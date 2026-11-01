package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.FarmerPayment.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FarmerPaymentService {

    FarmerPaymentResponseDto create(FarmerPaymentRequestDto dto);

    FarmerPaymentResponseDto update(Long id, FarmerPaymentUpdateDto dto);

    FarmerPaymentResponseDto updateStatus(Long id, FarmerPaymentStatusDto dto);

    void delete(Long id);

    FarmerPaymentResponseDto getById(Long id);

    FarmerPaymentResponseDto getByRef(String transactionRef);

    Page<FarmerPaymentResponseDto> search(String accountId, String settlementId,
                                          String status, String keyword, Pageable pageable);
}