package farm_to_table.farm_to_table.DTO.Register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String companyName;
    private String phoneNumber;
    private String roleId;
}