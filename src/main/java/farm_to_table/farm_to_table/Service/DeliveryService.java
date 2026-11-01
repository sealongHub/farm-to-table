package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Delivery.DeliveryRequestDto;
import farm_to_table.farm_to_table.DTO.Delivery.DeliveryResponseDto;
import farm_to_table.farm_to_table.Repository.DeliveryProcedureRepository;
import farm_to_table.farm_to_table.Repository.FeeDeliveryProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface DeliveryService {
    DeliveryResponseDto create(DeliveryRequestDto r);
    DeliveryResponseDto update(Long id, DeliveryRequestDto r);
    void delete(Long id);
    DeliveryResponseDto getById(Long id);
    List<DeliveryResponseDto> getAll(String orderId, String statusId, int page, int size);
}

