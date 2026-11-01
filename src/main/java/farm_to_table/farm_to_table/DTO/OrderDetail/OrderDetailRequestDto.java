package farm_to_table.farm_to_table.DTO.OrderDetail;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderDetailRequestDto {
    @NotBlank(message = "orderId is required")
    private String orderId;

    @NotBlank(message = "productId is required")
    private String productId;

    @NotNull(message = "qty is required")
    @Min(value = 1, message = "qty must be at least 1")
    private Integer qty;

    @NotNull(message = "unitPrice is required")
    @PositiveOrZero(message = "unitPrice must not be negative")
    private Double unitPrice;

    private String otherMethodId;

    @NotBlank(message = "paymentMethodId is required")
    private String paymentMethodId;
}