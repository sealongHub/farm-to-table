package farm_to_table.farm_to_table.DTO.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentUpdateDto {

    @NotBlank(message = "paymentMethodId is required")
    private String paymentMethodId;
}
