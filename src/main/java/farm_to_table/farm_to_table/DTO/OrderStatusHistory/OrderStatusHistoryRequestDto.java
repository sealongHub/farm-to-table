package farm_to_table.farm_to_table.DTO.OrderStatusHistory;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusHistoryRequestDto {
    @NotBlank(message = "orderId is required")
    private String orderId;

    private String orderStatusId;   // ស្ថានភាពចាស់

    @NotBlank(message = "newStatusId is required")
    private String newStatusId;     // ស្ថានភាពថ្មី

    private String remark;

    private LocalDateTime changeAt; // មិនផ្ញើ = server ប្រើ NOW()
}
