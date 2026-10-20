package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Product.ProductRequest;
import farm_to_table.farm_to_table.DTO.Product.ProductResponse;
import farm_to_table.farm_to_table.Repository.ProductRepository;
import farm_to_table.farm_to_table.model.ProductEntity;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest r) {
        Long id = productRepository.insertProduct(
                r.getUserId(), r.getCategoryId(), r.getName(), r.getQty(),
                r.getUnitPrice(), r.getFarmerPrice(),
                r.getExpectedHavestDate(), r.getExpiredDate(), r.getLocation());
        return getById(id);
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest r) {
        getById(id); // ពិនិត្យថាមានទិន្នន័យ
        productRepository.updateProduct(
                id, r.getUserId(), r.getCategoryId(), r.getName(), r.getQty(),
                r.getUnitPrice(), r.getFarmerPrice(),
                r.getExpectedHavestDate(), r.getExpiredDate(), r.getLocation());
        return getById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getById(id);
        productRepository.deleteProduct(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return productRepository.getProductById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAll() {
        return productRepository.getAllProducts().stream().map(this::toResponse).toList();
    }

    private ProductResponse toResponse(ProductEntity e) {
        return ProductResponse.builder()
                .id(e.getId())
                .userId(e.getUserId())
                .categoryId(e.getCategoryId())
                .name(e.getName())
                .qty(e.getQty())
                .unitPrice(e.getUnitPrice())
                .farmerPrice(e.getFarmerPrice())
                .expectedHavestDate(e.getExpectedHavestDate())
                .expiredDate(e.getExpiredDate())
                .location(e.getLocation())
                .build();
    }
}
