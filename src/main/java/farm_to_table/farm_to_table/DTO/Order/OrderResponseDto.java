package farm_to_table.farm_to_table.DTO.Order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;
    private String customerId;
    private String orderNumber;
    private String orderStatus;
    private Double totalAmount;
    private String deliveryAddress;
    private String statusId;
}
