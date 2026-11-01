package farm_to_table.farm_to_table.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "stocktransactions_entity")
public class StocktransactionsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WareHouseEntity warehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handover_id")
    private ProductHandoversEntity handover;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private UserEntity approvedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockTransactionType transactionType;

    private Integer reservedQty;
    private Integer qty;
    private Integer qtyBefore;
    private Integer qtyAfter;
    private String refNo;
    private LocalDateTime postedAt;
}