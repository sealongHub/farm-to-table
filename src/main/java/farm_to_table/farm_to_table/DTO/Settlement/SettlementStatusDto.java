package farm_to_table.farm_to_table.DTO.Settlement;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SettlementStatusDto {

    @NotBlank(message = "status is required")
    private String status;
}
