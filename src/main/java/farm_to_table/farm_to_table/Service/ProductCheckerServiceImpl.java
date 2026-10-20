package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Product.ProductCheckerDto;
import farm_to_table.farm_to_table.Repository.ProductCheckerRepository;
import farm_to_table.farm_to_table.model.ProductCheckerEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCheckerServiceImpl implements ProductCheckerService {

    private final ProductCheckerRepository repository;

    @Override
    public ProductCheckerDto create(ProductCheckerDto dto) {
        ProductCheckerEntity entity = new ProductCheckerEntity();
        applyDto(entity, dto);
        return toDto(repository.save(entity));
    }

    @Override
    public ProductCheckerDto update(Long id, ProductCheckerDto dto) {
        ProductCheckerEntity entity = findOrThrow(id);
        applyDto(entity, dto);
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    @Override
    public void deleteByProductId(String productId) {
        repository.deleteByProductId(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductCheckerDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductCheckerDto> getByProductId(String productId) {
        return repository.findByProductIdOrderByIdDesc(productId)
                .stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductCheckerDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }

    private ProductCheckerEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product checker with id " + id + " not found"));
    }

    private void applyDto(ProductCheckerEntity e, ProductCheckerDto d) {
        e.setProductId(d.getProductId());
        e.setCheckBy(d.getCheckBy());
        e.setCheckedAt(d.getCheckedAt());
    }

    private ProductCheckerDto toDto(ProductCheckerEntity e) {
        return new ProductCheckerDto(
                e.getId(), e.getProductId(), e.getCheckBy(), e.getCheckedAt());
    }
}