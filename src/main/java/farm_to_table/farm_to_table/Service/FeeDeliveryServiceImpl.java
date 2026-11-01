package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.FeeDelivery.FeeDeliveryRequestDto;
import farm_to_table.farm_to_table.DTO.FeeDelivery.FeeDeliveryResponseDto;
import farm_to_table.farm_to_table.Repository.FeeDeliveryProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeDeliveryServiceImpl implements FeeDeliveryService {
    private final FeeDeliveryProcedureRepository repo;

    @Override public FeeDeliveryResponseDto create(FeeDeliveryRequestDto r) {
        return getById(repo.insert(r));
    }
    @Override public FeeDeliveryResponseDto update(Long id, FeeDeliveryRequestDto r) {
        getById(id); repo.update(id, r); return getById(id);
    }
    @Override public void delete(Long id) { getById(id); repo.delete(id); }
    @Override public FeeDeliveryResponseDto getById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery fee with id " + id + " not found"));
    }
    @Override public List<FeeDeliveryResponseDto> getAll(String keyword, int page, int size) {
        return repo.findAll(keyword, size, page * size);
    }
}
