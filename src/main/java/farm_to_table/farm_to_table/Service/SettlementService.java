package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Settlement.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface SettlementService {

    SettlementResponseDto create(SettlementRequestDto dto);

    SettlementResponseDto update(Long id, SettlementUpdateDto dto);

    SettlementResponseDto updateStatus(Long id, SettlementStatusDto dto);

    void delete(Long id);

    SettlementResponseDto getById(Long id);

    Page<SettlementResponseDto> search(String ccy, String status,
                                       LocalDate from, LocalDate to, Pageable pageable);
}
