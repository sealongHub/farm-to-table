package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.ProductImage.ProductImageRequestDto;
import farm_to_table.farm_to_table.DTO.ProductImage.ProductImageResponseDto;
import farm_to_table.farm_to_table.DTO.ProductImage.ProductImageUpdateDto;
import farm_to_table.farm_to_table.Repository.ProductImageRepository;
import farm_to_table.farm_to_table.Repository.ProductRepository;
import farm_to_table.farm_to_table.model.ProductEntity;
import farm_to_table.farm_to_table.model.ProductImageEntity;
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
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository repository;
    private final ProductRepository productRepository;

    @Override
    public ProductImageResponseDto create(Long productId, ProductImageRequestDto dto) {
        ProductEntity product = findProductOrThrow(productId);
        String path = dto.getPath().trim();

        if (repository.existsByProduct_IdAndPath(productId, path)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Image '" + path + "' already exists for product " + productId);
        }

        ProductImageEntity entity = new ProductImageEntity();
        entity.setProduct(product);
        entity.setPath(path);
        return toDto(repository.save(entity));
    }

    @Override
    public ProductImageResponseDto update(Long productId, Long id, ProductImageUpdateDto dto) {
        ProductImageEntity entity = findOrThrow(productId, id);
        String path = dto.getPath().trim();

        if (repository.existsByProduct_IdAndPathAndIdNot(productId, path, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Image '" + path + "' already exists for product " + productId);
        }

        entity.setPath(path);
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long productId, Long id) {
        repository.delete(findOrThrow(productId, id));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductImageResponseDto getById(Long productId, Long id) {
        return toDto(findOrThrow(productId, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductImageResponseDto> search(Long productId, String path, Pageable pageable) {
        findProductOrThrow(productId);
        String f = path == null ? "" : path.trim();
        return repository.findByProduct_IdAndPathContainingIgnoreCase(productId, f, pageable)
                .map(this::toDto);
    }

    // ---------- helpers ----------

    private ProductEntity findProductOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product with id " + productId + " not found"));
    }

    private ProductImageEntity findOrThrow(Long productId, Long id) {
        return repository.findByIdAndProduct_Id(id, productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Image with id " + id + " not found for product " + productId));
    }

    private ProductImageResponseDto toDto(ProductImageEntity e) {
        return new ProductImageResponseDto(e.getId(), e.getProduct().getId(), e.getPath());
    }
}