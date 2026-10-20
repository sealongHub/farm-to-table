package farm_to_table.farm_to_table.Service;


import farm_to_table.farm_to_table.DTO.Product.ProductRejectDto;
import farm_to_table.farm_to_table.Repository.ProductRejectRepository;
import farm_to_table.farm_to_table.model.ProductRejectEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductRejectServiceImpl implements ProductRejectService {

    private final ProductRejectRepository repository;

    @Override
    public ProductRejectDto create(ProductRejectDto dto) {
        ProductRejectEntity entity = new ProductRejectEntity();
        applyDto(entity, dto);
        return toDto(repository.save(entity));
    }

    @Override
    public ProductRejectDto update(Long id, ProductRejectDto dto) {
        ProductRejectEntity entity = findOrThrow(id);
        applyDto(entity, dto);
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductRejectDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductRejectDto> search(String userId, String categoryId,
                                         Boolean status, String keyword,
                                         Pageable pageable) {
        return repository.search(userId, categoryId, status, keyword, pageable)
                .map(this::toDto);
    }

    private ProductRejectEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product reject with id " + id + " not found"));
    }

    private void applyDto(ProductRejectEntity e, ProductRejectDto d) {
        e.setUserId(d.getUserId());
        e.setCategoryId(d.getCategoryId());
        e.setName(d.getName());
        e.setQty(d.getQty());
        e.setUnitPrice(d.getUnitPrice());
        e.setFarmerPrice(d.getFarmerPrice());
        e.setExpectedHavestDate(d.getExpectedHavestDate());
        e.setExpiredDate(d.getExpiredDate());
        e.setLocation(d.getLocation());
        e.setReason(d.getReason());
        e.setAvailableQty(d.getAvailableQty());
        e.setSoldQty(d.getSoldQty());
        e.setStatus(d.getStatus());
    }

    private ProductRejectDto toDto(ProductRejectEntity e) {
        return new ProductRejectDto(
                e.getId(), e.getUserId(), e.getCategoryId(), e.getName(),
                e.getQty(), e.getUnitPrice(), e.getFarmerPrice(),
                e.getExpectedHavestDate(), e.getExpiredDate(), e.getLocation(),
                e.getReason(), e.getAvailableQty(), e.getSoldQty(), e.getStatus());
    }
}
