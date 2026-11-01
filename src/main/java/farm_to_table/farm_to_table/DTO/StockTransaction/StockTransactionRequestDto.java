package farm_to_table.farm_to_table.DTO.StockTransaction;

import farm_to_table.farm_to_table.model.StockTransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StockTransactionRequestDto {

    @NotNull(message = "warehouseId is required")
    private Long warehouseId;

    @NotNull(message = "productId is required")
    private Long productId;

    private Long handoverId;                 // ស្រេចចិត្ត

    @NotNull(message = "transactionType is required")
    private StockTransactionType transactionType;

    @NotNull(message = "qty is required")
    @Positive(message = "qty must be greater than 0")
    private Integer qty;

    @Size(max = 100, message = "refNo must be at most 100 characters")
    private String refNo;

    @NotNull(message = "statusId is required")
    private Long statusId;

    private Long approvedById;               // ស្រេចចិត្ត
}