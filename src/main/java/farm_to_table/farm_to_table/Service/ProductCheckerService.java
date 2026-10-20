package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Product.ProductCheckerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCheckerService {

    ProductCheckerDto create(ProductCheckerDto dto);

    ProductCheckerDto update(Long id, ProductCheckerDto dto);

    void delete(Long id);

    void deleteByProductId(String productId);

    ProductCheckerDto getById(Long id);

    List<ProductCheckerDto> getByProductId(String productId);

    Page<ProductCheckerDto> getAll(Pageable pageable);
}