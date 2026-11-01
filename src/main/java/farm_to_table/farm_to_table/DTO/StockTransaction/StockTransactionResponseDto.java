package farm_to_table.farm_to_table.DTO.StockTransaction;

import farm_to_table.farm_to_table.model.StockTransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StockTransactionResponseDto {
    private Long id;
    private Long warehouseId;
    private Long productId;
    private Long handoverId;
    private StockTransactionType transactionType;
    private Integer qty;
    private Integer qtyBefore;
    private Integer qtyAfter;
    private Integer reservedQty;
    private String refNo;
    private Long statusId;
    private String statusCode;
    private Long approvedById;
    private LocalDateTime postedAt;
}