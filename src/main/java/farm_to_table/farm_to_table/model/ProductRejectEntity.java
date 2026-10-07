package farm_to_table.farm_to_table.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class ProductRejectEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String categoryId;
    private String name;
    private Integer qty;
    private Double unitPrice;
    private Double farmerPrice;
    private Date expectedHavest;
    private Date expireDate;
    private String location;
    private TextArea reason;
    private Integer availableQty;
    private String soldQty;
    private Boolean Status;

}
