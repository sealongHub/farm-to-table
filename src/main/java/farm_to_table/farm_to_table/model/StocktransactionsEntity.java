package farm_to_table.farm_to_table.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StocktransactionsEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String warehousId;
    private String productId;
    private String handoverId;
    private Integer reservedQty;
    private String transactionTypeId;
    private Integer qty;
    private Integer qtyBefore;
    private Integer qtyAfter;
    private String refNo;
    private String statusId;
    private String approvedBy;
    private String postedAt;
}
