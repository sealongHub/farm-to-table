package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.DeliveryMethod.DeliveryMethodRequestDto;
import farm_to_table.farm_to_table.DTO.DeliveryMethod.DeliveryMethodResponseDto;
import farm_to_table.farm_to_table.Repository.DeliveryMethodProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface DeliveryMethodService {
    DeliveryMethodResponseDto create(DeliveryMethodRequestDto r);
    DeliveryMethodResponseDto update(Long id, DeliveryMethodRequestDto r);
    void delete(Long id);
    DeliveryMethodResponseDto getById(Long id);
    List<DeliveryMethodResponseDto> getAll(String keyword, int page, int size);
}

