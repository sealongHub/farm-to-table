package farm_to_table.farm_to_table.DTO.Delivery;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryResponseDto {
    private Long id;
    private String orderId;
    private String assignTo;
    private String deliveryAddress;
    private String deliveryFeeId;
    private LocalDateTime scheduleAt;
    private LocalDateTime deliveryAt;
    private String statusId;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}