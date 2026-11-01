package farm_to_table.farm_to_table.DTO.Order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDto {

    @NotBlank(message = "deliveryAddress is required")
    @Size(max = 255, message = "deliveryAddress must be at most 255 characters")
    private String deliveryAddress;

    @NotNull(message = "totalAmount is required")
    @Positive(message = "totalAmount must be greater than 0")
    private Double totalAmount;
}
