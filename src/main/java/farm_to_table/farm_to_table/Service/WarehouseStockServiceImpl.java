package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.WarehouseStock.WarehouseStockAdjustDto;
import farm_to_table.farm_to_table.DTO.WarehouseStock.WarehouseStockRequestDto;
import farm_to_table.farm_to_table.DTO.WarehouseStock.WarehouseStockReserveDto;
import farm_to_table.farm_to_table.DTO.WarehouseStock.WarehouseStockResponseDto;
import farm_to_table.farm_to_table.DTO.WarehouseStock.WarehouseStockUpdateDto;
import farm_to_table.farm_to_table.Repository.ProductRepository;
import farm_to_table.farm_to_table.Repository.WareHouseRepository;
import farm_to_table.farm_to_table.Repository.WarehouseStockRepository;
import farm_to_table.farm_to_table.model.WarehouseStockEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseStockServiceImpl implements WarehouseStockService {

    private final WarehouseStockRepository repository;
    private final WareHouseRepository warehouseRepository;
    private final ProductRepository productRepository;   // តារាង tbl_product

    @Override
    public WarehouseStockResponseDto create(WarehouseStockRequestDto dto) {
        Long warehouseId = parseId(dto.getWarehouseId(), "warehouseId");
        Long productId = parseId(dto.getProductId(), "productId");

        if (!warehouseRepository.existsById(warehouseId)) {
            throw new EntityNotFoundException("Warehouse with id " + warehouseId + " not found");
        }
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product with id " + productId + " not found");
        }
        if (repository.existsByWarehouseIdAndProductId(
                String.valueOf(warehouseId), String.valueOf(productId))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Stock for product " + productId + " in warehouse " + warehouseId
                            + " already exists");
        }

        WarehouseStockEntity entity = new WarehouseStockEntity();
        entity.setWarehouseId(String.valueOf(warehouseId));
        entity.setProductId(String.valueOf(productId));
        entity.setQty(dto.getQty());
        entity.setReservedQty(0);
        return toDto(repository.save(entity));
    }

    @Override
    public WarehouseStockResponseDto update(Long id, WarehouseStockUpdateDto dto) {
        WarehouseStockEntity entity = findForUpdateOrThrow(id);
        requireNotBelowReserved(dto.getQty(), entity);

        entity.setQty(dto.getQty());
        return toDto(repository.save(entity));
    }

    @Override
    public WarehouseStockResponseDto adjust(Long id, WarehouseStockAdjustDto dto) {
        WarehouseStockEntity entity = findForUpdateOrThrow(id);

        long newQty = (long) nz(entity.getQty()) + dto.getDelta();
        if (newQty < 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Stock cannot be negative (current qty: " + nz(entity.getQty()) + ")");
        }
        if (newQty > Integer.MAX_VALUE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "qty is too large");
        }
        requireNotBelowReserved((int) newQty, entity);

        entity.setQty((int) newQty);
        return toDto(repository.save(entity));
    }

    @Override
    public WarehouseStockResponseDto reserve(Long id, WarehouseStockReserveDto dto) {
        WarehouseStockEntity entity = findForUpdateOrThrow(id);

        int available = nz(entity.getQty()) - nz(entity.getReservedQty());
        if (dto.getQty() > available) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Not enough available stock (available: " + available
                            + ", requested: " + dto.getQty() + ")");
        }

        entity.setReservedQty(nz(entity.getReservedQty()) + dto.getQty());
        return toDto(repository.save(entity));
    }

    @Override
    public WarehouseStockResponseDto release(Long id, WarehouseStockReserveDto dto) {
        WarehouseStockEntity entity = findForUpdateOrThrow(id);

        int reserved = nz(entity.getReservedQty());
        if (dto.getQty() > reserved) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot release more than reserved (reserved: " + reserved
                            + ", requested: " + dto.getQty() + ")");
        }

        entity.setReservedQty(reserved - dto.getQty());
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        WarehouseStockEntity entity = findForUpdateOrThrow(id);

        if (nz(entity.getQty()) != 0 || nz(entity.getReservedQty()) != 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only stock with qty 0 and reservedQty 0 can be deleted (qty: "
                            + nz(entity.getQty()) + ", reservedQty: "
                            + nz(entity.getReservedQty()) + ")");
        }
        repository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseStockResponseDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WarehouseStockResponseDto> search(String warehouseId, String productId,
                                                  Integer lowStock, Pageable pageable) {
        String w = warehouseId == null ? "" : warehouseId.trim();
        String p = productId == null ? "" : productId.trim();
        int max = lowStock == null ? Integer.MAX_VALUE : lowStock;
        return repository.search(w, p, max, pageable).map(this::toDto);
    }

    // ---------- helpers ----------

    private WarehouseStockEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse stock with id " + id + " not found"));
    }

    // ចាក់សោជួរ ដើម្បីកុំឱ្យ request ២ កែស្តុកជាន់គ្នា
    private WarehouseStockEntity findForUpdateOrThrow(Long id) {
        return repository.findByIdForUpdate(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse stock with id " + id + " not found"));
    }

    private void requireNotBelowReserved(int newQty, WarehouseStockEntity entity) {
        int reserved = nz(entity.getReservedQty());
        if (newQty < reserved) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "qty cannot be less than reservedQty (reservedQty: " + reserved + ")");
        }
    }

    private Long parseId(String value, String field) {
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, field + " must be a number");
        }
    }

    private int nz(Integer v) {
        return v == null ? 0 : v;
    }

    private WarehouseStockResponseDto toDto(WarehouseStockEntity e) {
        int qty = nz(e.getQty());
        int reserved = nz(e.getReservedQty());
        return new WarehouseStockResponseDto(
                e.getId(), e.getWarehouseId(), e.getProductId(),
                qty, reserved, qty - reserved);
    }
}
