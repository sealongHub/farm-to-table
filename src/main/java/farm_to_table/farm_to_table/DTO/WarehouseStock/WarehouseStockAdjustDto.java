package farm_to_table.farm_to_table.DTO.WarehouseStock;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseStockAdjustDto {

    @NotNull(message = "delta is required")
    private Integer delta;      // + បន្ថែមស្តុក, - ដកស្តុក
}