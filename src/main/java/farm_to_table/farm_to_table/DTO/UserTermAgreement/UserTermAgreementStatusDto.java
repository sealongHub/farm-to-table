package farm_to_table.farm_to_table.DTO.UserTermAgreement;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserTermAgreementStatusDto {

    @NotBlank(message = "status is required")
    private String status;
}
