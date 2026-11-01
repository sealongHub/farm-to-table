package farm_to_table.farm_to_table.DTO.FarmerPayment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FarmerPaymentRequestDto {

    @NotBlank(message = "accountId is required")
    private String accountId;

    @NotBlank(message = "settlementId is required")
    private String settlementId;
}
