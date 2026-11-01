package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.DeliveryMethod.DeliveryMethodRequestDto;
import farm_to_table.farm_to_table.DTO.DeliveryMethod.DeliveryMethodResponseDto;
import farm_to_table.farm_to_table.Repository.DeliveryMethodProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryMethodServiceImpl implements DeliveryMethodService {
    private final DeliveryMethodProcedureRepository repo;

    @Override public DeliveryMethodResponseDto create(DeliveryMethodRequestDto r) {
        return getById(repo.insert(r));
    }
    @Override public DeliveryMethodResponseDto update(Long id, DeliveryMethodRequestDto r) {
        getById(id); repo.update(id, r); return getById(id);
    }
    @Override public void delete(Long id) { getById(id); repo.delete(id); }
    @Override public DeliveryMethodResponseDto getById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery method with id " + id + " not found"));
    }
    @Override public List<DeliveryMethodResponseDto> getAll(String keyword, int page, int size) {
        return repo.findAll(keyword, size, page * size);
    }
}
