package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Customer.CustomerRequestDto;
import farm_to_table.farm_to_table.DTO.Customer.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerResponseDto create(CustomerRequestDto dto);

    CustomerResponseDto update(Long id, CustomerRequestDto dto);

    void delete(Long id);

    CustomerResponseDto getById(Long id);

    Page<CustomerResponseDto> search(String keyword, String status, Pageable pageable);
}