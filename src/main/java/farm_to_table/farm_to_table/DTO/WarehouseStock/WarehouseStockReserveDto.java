package farm_to_table.farm_to_table.DTO.WarehouseStock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseStockReserveDto {

    @NotNull(message = "qty is required")
    @Positive(message = "qty must be greater than 0")
    private Integer qty;
}