package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.OrderStatusHistory.*;
import farm_to_table.farm_to_table.Repository.OrderStatusHistoryProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusHistoryServiceImpl implements OrderStatusHistoryService {

    private final OrderStatusHistoryProcedureRepository repository;

    @Override
    public OrderStatusHistoryResponseDto create(OrderStatusHistoryRequestDto request) {
        Long newId = repository.insert(request);
        return getById(newId);
    }

    @Override
    public OrderStatusHistoryResponseDto update(Long id, OrderStatusHistoryRequestDto request) {
        getById(id);
        repository.update(id, request);
        return getById(id);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repository.delete(id);
    }

    @Override
    public OrderStatusHistoryResponseDto getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Order status history with id " + id + " not found"));
    }

    @Override
    public List<OrderStatusHistoryResponseDto> getAll(String orderId, int page, int size) {
        return repository.findAll(orderId, size, page * size);
    }
}