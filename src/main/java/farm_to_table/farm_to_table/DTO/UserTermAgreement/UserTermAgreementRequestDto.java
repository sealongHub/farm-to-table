package farm_to_table.farm_to_table.DTO.UserTermAgreement;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserTermAgreementRequestDto {

    @NotBlank(message = "userId is required")
    private String userId;

    @NotBlank(message = "termId is required")
    private String termId;
}