package farm_to_table.farm_to_table.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "user_account")
@Getter @Setter
public class UserAccountEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String accountName;
    private String accountType;
    private String accountNumber;
    private String bankName;
    private Boolean isVerified;
    private Boolean isDefault;
}
