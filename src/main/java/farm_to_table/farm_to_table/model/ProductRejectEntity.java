package farm_to_table.farm_to_table.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class ProductRejectEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String categoryId;
    private String name;
    private Integer qty;
    private Double unitPrice;
    private Double farmerPrice;
    private Date expectedHavestDate;
    private Date expiredDate;
    private String location;

    @Column(columnDefinition = "TEXT")
    private String reason;

    private Integer availableQty;
    private String soldQty;
    private Boolean status;   // was "Status"; getter is still getStatus()
}