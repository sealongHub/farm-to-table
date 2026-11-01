package farm_to_table.farm_to_table.DTO.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentRequestDto {

    @NotBlank(message = "orderId is required")
    private String orderId;

    @NotBlank(message = "paymentMethodId is required")
    private String paymentMethodId;
}
