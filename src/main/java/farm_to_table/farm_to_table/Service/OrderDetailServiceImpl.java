package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.OrderDetail.*;
import farm_to_table.farm_to_table.Repository.OrderDetailProcedureRepository;
import farm_to_table.farm_to_table.Service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailProcedureRepository repository;

    @Override
    public OrderDetailResponseDto create(OrderDetailRequestDto request) {
        Long newId = repository.insert(request);
        return getById(newId);
    }

    @Override
    public OrderDetailResponseDto update(Long id, OrderDetailRequestDto request) {
        getById(id); // ពិនិត្យថាមានមែន បើគ្មាន → 404
        repository.update(id, request);
        return getById(id);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repository.delete(id);
    }

    @Override
    public OrderDetailResponseDto getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Order detail with id " + id + " not found"));
    }

    @Override
    public List<OrderDetailResponseDto> getAll(String orderId, int page, int size) {
        return repository.findAll(orderId, size, page * size);
    }
}