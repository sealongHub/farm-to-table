package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Product.ProductRejectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRejectService {

    ProductRejectDto create(ProductRejectDto dto);

    ProductRejectDto update(Long id, ProductRejectDto dto);

    void delete(Long id);

    ProductRejectDto getById(Long id);

    Page<ProductRejectDto> search(String userId,
                                  String categoryId,
                                  Boolean status,
                                  String keyword,
                                  Pageable pageable);
}
