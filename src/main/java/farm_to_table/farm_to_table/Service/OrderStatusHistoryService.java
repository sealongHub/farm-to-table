package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.OrderStatusHistory.*;
import java.util.List;

public interface OrderStatusHistoryService {
    OrderStatusHistoryResponseDto create(OrderStatusHistoryRequestDto request);
    OrderStatusHistoryResponseDto update(Long id, OrderStatusHistoryRequestDto request);
    void delete(Long id);
    OrderStatusHistoryResponseDto getById(Long id);
    List<OrderStatusHistoryResponseDto> getAll(String orderId, int page, int size);
}
