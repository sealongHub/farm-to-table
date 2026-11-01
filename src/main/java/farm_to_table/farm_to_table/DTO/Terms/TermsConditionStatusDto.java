package farm_to_table.farm_to_table.DTO.Terms;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TermsConditionStatusDto {

    @NotBlank(message = "status is required")
    private String status;
}
