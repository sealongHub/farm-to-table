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

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryProcedureRepository repo;
    private final FeeDeliveryProcedureRepository feeRepo;

    private void validateFee(String feeId) {
        if (feeId == null || feeId.isBlank()) return;
        try {
            Long id = Long.parseLong(feeId);
            if (feeRepo.findById(id).isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Delivery fee with id " + feeId + " not found");
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "deliveryFeeId must be a number");
        }
    }

    @Override public DeliveryResponseDto create(DeliveryRequestDto r) {
        validateFee(r.getDeliveryFeeId());
        return getById(repo.insert(r));
    }
    @Override public DeliveryResponseDto update(Long id, DeliveryRequestDto r) {
        getById(id);
        validateFee(r.getDeliveryFeeId());
        repo.update(id, r);
        return getById(id);
    }
    @Override public void delete(Long id) { getById(id); repo.delete(id); }
    @Override public DeliveryResponseDto getById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Delivery with id " + id + " not found"));
    }
    @Override public List<DeliveryResponseDto> getAll(String orderId, String statusId, int page, int size) {
        return repo.findAll(orderId, statusId, size, page * size);
    }
}
