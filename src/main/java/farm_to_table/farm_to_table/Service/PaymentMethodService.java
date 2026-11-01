package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.PaymentMethod.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentMethodService {

    PaymentMethodResponseDto create(PaymentMethodRequestDto dto);

    PaymentMethodResponseDto update(Long id, PaymentMethodUpdateDto dto);

    void delete(Long id);

    PaymentMethodResponseDto getById(Long id);

    List<PaymentMethodResponseDto> getAll();   // សម្រាប់ dropdown

    Page<PaymentMethodResponseDto> search(String keyword, Pageable pageable);
}
