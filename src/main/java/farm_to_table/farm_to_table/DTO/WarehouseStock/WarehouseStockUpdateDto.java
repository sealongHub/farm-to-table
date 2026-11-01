package farm_to_table.farm_to_table.DTO.WarehouseStock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseStockUpdateDto {

    @NotNull(message = "qty is required")
    @PositiveOrZero(message = "qty must be 0 or greater")
    private Integer qty;
}
