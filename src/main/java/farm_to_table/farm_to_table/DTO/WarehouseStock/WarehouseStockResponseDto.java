package farm_to_table.farm_to_table.DTO.WarehouseStock;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WarehouseStockResponseDto {
    private Long id;
    private String warehouseId;
    private String productId;
    private Integer qty;
    private Integer reservedQty;
    private Integer availableQty;   // qty - reservedQty
}