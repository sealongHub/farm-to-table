package farm_to_table.farm_to_table.DTO.Order;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDto {

    @NotBlank(message = "status is required")
    private String status;
}
