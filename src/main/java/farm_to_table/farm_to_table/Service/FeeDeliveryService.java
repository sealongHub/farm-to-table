package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.FeeDelivery.FeeDeliveryRequestDto;
import farm_to_table.farm_to_table.DTO.FeeDelivery.FeeDeliveryResponseDto;
import farm_to_table.farm_to_table.Repository.FeeDeliveryProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface FeeDeliveryService {
    FeeDeliveryResponseDto create(FeeDeliveryRequestDto r);
    FeeDeliveryResponseDto update(Long id, FeeDeliveryRequestDto r);
    void delete(Long id);
    FeeDeliveryResponseDto getById(Long id);
    List<FeeDeliveryResponseDto> getAll(String keyword, int page, int size);
}

