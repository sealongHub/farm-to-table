package farm_to_table.farm_to_table.DTO.FarmerPayment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FarmerPaymentStatusDto {

    @NotBlank(message = "status is required")
    private String status;
}