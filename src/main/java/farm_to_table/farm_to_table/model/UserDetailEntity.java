package farm_to_table.farm_to_table.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity @Table(name = "user_detail")
@Getter @Setter
public class UserDetailEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String nationality;
    private String cardId;
    private String address;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String maritalStatus;
}
