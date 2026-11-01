package farm_to_table.farm_to_table.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "status_entity",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_status_code_type", columnNames = {"code", "type"}))
public class StatusEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    private String description;

    @Column(nullable = false)
    private String type;
}