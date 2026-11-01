package farm_to_table.farm_to_table.DTO.payment;

import lombok.*;

import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private String orderId;
    private String customerId;
    private String ref;
    private Double amount;
    private String paymentMethodId;
    private String paymentMethod;     // code ឧ. CASH
    private String paymentStatusId;
    private String paymentStatus;     // code ឧ. PENDING
    private Date paidAt;
}