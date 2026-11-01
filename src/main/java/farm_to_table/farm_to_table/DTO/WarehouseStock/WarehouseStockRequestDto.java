package farm_to_table.farm_to_table.DTO.WarehouseStock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseStockRequestDto {

    @NotBlank(message = "warehouseId is required")
    private String warehouseId;

    @NotBlank(message = "productId is required")
    private String productId;

    @NotNull(message = "qty is required")
    @PositiveOrZero(message = "qty must be 0 or greater")
    private Integer qty;
}