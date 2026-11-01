package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.StockTransaction.StockTransactionRequestDto;
import farm_to_table.farm_to_table.DTO.StockTransaction.StockTransactionResponseDto;
import farm_to_table.farm_to_table.Repository.*;
import farm_to_table.farm_to_table.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StocktransactionsServiceImpl implements StocktransactionsService {

    private final StockTransactionRepository repository;
    private final WarehouseStockRepository stockRepository;
    private final WareHouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final ProductHandoverRepository handoverRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    @Override
    public StockTransactionResponseDto create(StockTransactionRequestDto dto) {
        WareHouseEntity warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse with id " + dto.getWarehouseId() + " not found"));
        ProductEntity product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product with id " + dto.getProductId() + " not found"));
        StatusEntity status = statusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Status with id " + dto.getStatusId() + " not found"));

        ProductHandoversEntity handover = null;
        if (dto.getHandoverId() != null) {
            handover = handoverRepository.findById(dto.getHandoverId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Handover with id " + dto.getHandoverId() + " not found"));
        }
        UserEntity approver = null;
        if (dto.getApprovedById() != null) {
            approver = userRepository.findById(dto.getApprovedById())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "User with id " + dto.getApprovedById() + " not found"));
        }

        // WarehouseStockEntity ប្រើ String id ដូច្នេះបំប្លែងពី Long
        String wid = String.valueOf(dto.getWarehouseId());
        String pid = String.valueOf(dto.getProductId());
        StockTransactionType type = dto.getTransactionType();
        int amount = dto.getQty();

        WarehouseStockEntity stock = stockRepository.findForUpdate(wid, pid).orElse(null);
        if (stock == null) {
            if (type != StockTransactionType.IN) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "No stock for product " + pid + " in warehouse " + wid);
            }
            stock = new WarehouseStockEntity();
            stock.setWarehouseId(wid);
            stock.setProductId(pid);
            stock.setQty(0);
            stock.setReservedQty(0);
        }

        int onHand = nz(stock.getQty());
        int reserved = nz(stock.getReservedQty());
        int before = onHand;

        switch (type) {
            case IN -> onHand += amount;
            case OUT -> {
                requireAvailable(onHand - reserved, amount);
                onHand -= amount;
            }
            case RESERVE -> {
                requireAvailable(onHand - reserved, amount);
                reserved += amount;
            }
            case RELEASE -> {
                if (amount > reserved) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Cannot release " + amount + ", only " + reserved + " reserved");
                }
                reserved -= amount;
            }
        }

        stock.setQty(onHand);
        stock.setReservedQty(reserved);
        stockRepository.save(stock);

        StocktransactionsEntity entity = new StocktransactionsEntity();
        entity.setWarehouse(warehouse);
        entity.setProduct(product);
        entity.setHandover(handover);
        entity.setStatus(status);
        entity.setApprovedBy(approver);
        entity.setTransactionType(type);
        entity.setQty(amount);
        entity.setQtyBefore(before);
        entity.setQtyAfter(onHand);
        entity.setReservedQty(reserved);
        entity.setRefNo(dto.getRefNo() == null ? null : dto.getRefNo().trim());
        entity.setPostedAt(LocalDateTime.now());
        return toDto(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public StockTransactionResponseDto getById(Long id) {
        return toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Stock transaction with id " + id + " not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockTransactionResponseDto> search(Long warehouseId, Long productId,
                                                    StockTransactionType type, Long statusId,
                                                    LocalDate from, LocalDate to, Pageable pageable) {
        Specification<StocktransactionsEntity> spec = (root, q, cb) -> {
            List<Predicate> ps = new ArrayList<>();
            if (warehouseId != null) ps.add(cb.equal(root.get("warehouse").get("id"), warehouseId));
            if (productId != null)   ps.add(cb.equal(root.get("product").get("id"), productId));
            if (statusId != null)    ps.add(cb.equal(root.get("status").get("id"), statusId));
            if (type != null)        ps.add(cb.equal(root.get("transactionType"), type));
            if (from != null)        ps.add(cb.greaterThanOrEqualTo(root.get("postedAt"), from.atStartOfDay()));
            if (to != null)          ps.add(cb.lessThan(root.get("postedAt"), to.plusDays(1).atStartOfDay()));
            return cb.and(ps.toArray(new Predicate[0]));
        };
        return repository.findAll(spec, pageable).map(this::toDto);
    }

    // ---------- helpers ----------

    private int nz(Integer v) {
        return v == null ? 0 : v;
    }

    private void requireAvailable(int available, int amount) {
        if (amount > available) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Insufficient stock. Available: " + available + ", requested: " + amount);
        }
    }

    private StockTransactionResponseDto toDto(StocktransactionsEntity e) {
        return new StockTransactionResponseDto(
                e.getId(),
                e.getWarehouse().getId(),
                e.getProduct().getId(),
                e.getHandover() == null ? null : e.getHandover().getId(),
                e.getTransactionType(),
                e.getQty(), e.getQtyBefore(), e.getQtyAfter(), e.getReservedQty(),
                e.getRefNo(),
                e.getStatus().getId(), e.getStatus().getCode(),
                e.getApprovedBy() == null ? null : e.getApprovedBy().getId(),
                e.getPostedAt());
    }
}
