package farm_to_table.farm_to_table.DTO.OrderStatusHistory;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusHistoryResponseDto {
    private Long id;
    private String orderId;
    private String orderStatusId;
    private String newStatusId;
    private String remark;
    private LocalDateTime changeAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
