package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.UserTermAgreement.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserTermAgreementService {

    UserTermAgreementResponseDto create(UserTermAgreementRequestDto dto,
                                        String ipAddress, String userAgent);

    UserTermAgreementResponseDto updateStatus(Long id, UserTermAgreementStatusDto dto);

    void delete(Long id);

    UserTermAgreementResponseDto getById(Long id);

    // user នេះបានយល់ព្រមកំណែ ACTIVE របស់ type នេះឬនៅ
    AgreementCheckDto check(String userId, String type);

    Page<UserTermAgreementResponseDto> search(String userId, String termId,
                                              String status, Pageable pageable);
}
