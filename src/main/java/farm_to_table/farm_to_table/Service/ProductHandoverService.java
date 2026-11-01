package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.ProductHandover.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductHandoverService {

    ProductHandoverResponseDto create(ProductHandoverRequestDto dto);

    ProductHandoverResponseDto update(Long id, ProductHandoverRequestDto dto);

    ProductHandoverResponseDto updateStatus(Long id, ProductHandoverStatusDto dto);

    void delete(Long id);

    ProductHandoverResponseDto getById(Long id);

    Page<ProductHandoverResponseDto> search(String fromType, String fromId,
                                            String toType, String toId,
                                            String status, Pageable pageable);
}