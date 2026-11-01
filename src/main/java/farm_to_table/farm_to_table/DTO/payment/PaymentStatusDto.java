package farm_to_table.farm_to_table.DTO.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentStatusDto {

    @NotBlank(message = "status is required")
    private String status;
}
