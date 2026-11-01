package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.OrderDetail.*;
import java.util.List;

public interface OrderDetailService {
    OrderDetailResponseDto create(OrderDetailRequestDto request);
    OrderDetailResponseDto update(Long id, OrderDetailRequestDto request);
    void delete(Long id);
    OrderDetailResponseDto getById(Long id);
    List<OrderDetailResponseDto> getAll(String orderId, int page, int size);
}
