package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.payment.PaymentRequestDto;
import farm_to_table.farm_to_table.DTO.payment.PaymentResponseDto;
import farm_to_table.farm_to_table.DTO.payment.PaymentStatusDto;
import farm_to_table.farm_to_table.DTO.payment.PaymentUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentResponseDto create(PaymentRequestDto dto);

    PaymentResponseDto update(Long id, PaymentUpdateDto dto);

    PaymentResponseDto updateStatus(Long id, PaymentStatusDto dto);

    void delete(Long id);

    PaymentResponseDto getById(Long id);

    PaymentResponseDto getByRef(String ref);

    Page<PaymentResponseDto> search(String orderId, String customerId,
                                    String status, String keyword, Pageable pageable);
}