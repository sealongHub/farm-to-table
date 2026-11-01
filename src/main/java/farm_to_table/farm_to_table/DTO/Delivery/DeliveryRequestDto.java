package farm_to_table.farm_to_table.DTO.Delivery;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryRequestDto {
    @NotBlank(message = "orderId is required")
    private String orderId;
    private String assignTo;
    @NotBlank(message = "deliveryAddress is required")
    private String deliveryAddress;
    private String deliveryFeeId;
    private LocalDateTime scheduleAt;
    private LocalDateTime deliveryAt;
    @NotBlank(message = "statusId is required")
    private String statusId;
    private String remarks;
}
