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
public class ProductHandoversEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fromType;
    private String fromId;
    private String toType;
    private String toId;
    private Integer qty;
    private String statusId;
    private String receiveBy;
    private String note;
}
