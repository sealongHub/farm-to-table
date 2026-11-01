package farm_to_table.farm_to_table.DTO.FeeDelivery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class FeeDeliveryRequestDto {
    @NotBlank(message = "code is required")
    private String code;
    @NotNull(message = "amount is required")
    @PositiveOrZero(message = "amount must not be negative")
    private Double amount;
    private String description;
}
