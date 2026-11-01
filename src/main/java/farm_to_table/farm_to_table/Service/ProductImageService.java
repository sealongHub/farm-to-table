package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.ProductImage.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductImageService {

    ProductImageResponseDto create(Long productId, ProductImageRequestDto dto);

    ProductImageResponseDto update(Long productId, Long id, ProductImageUpdateDto dto);

    void delete(Long productId, Long id);

    ProductImageResponseDto getById(Long productId, Long id);

    Page<ProductImageResponseDto> search(Long productId, String path, Pageable pageable);
}