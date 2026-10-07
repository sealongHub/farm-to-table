package farm_to_table.farm_to_table.DTO.Login;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

// LoginRequest
@Data
public class LoginRequest {
    @NotBlank private String username;
    @NotBlank private String password;
}