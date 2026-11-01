package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Terms.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TermsConditionService {

    TermsConditionResponseDto create(TermsConditionRequestDto dto);

    TermsConditionResponseDto update(Long id, TermsConditionUpdateDto dto);

    TermsConditionResponseDto updateStatus(Long id, TermsConditionStatusDto dto);

    void delete(Long id);

    TermsConditionResponseDto getById(Long id);

    // កំណែ ACTIVE បច្ចុប្បន្នរបស់ type (សម្រាប់បង្ហាញដល់អតិថិជន)
    TermsConditionResponseDto getActive(String type);

    Page<TermsConditionResponseDto> search(String type, String status,
                                           String keyword, Pageable pageable);
}
