package farm_to_table.farm_to_table.DTO.CreateUser;

import farm_to_table.farm_to_table.DTO.UserAccount.UserAccountRequest;
import farm_to_table.farm_to_table.DTO.UserDetail.UserDetailRequest;
import farm_to_table.farm_to_table.DTO.UserCompany.UserCompanyDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank @Size(min = 4, max = 50)
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Only letters, digits, . _ - allowed")
        String username,
        @NotBlank String fullName,
        @Email String email,
        String image,
        @NotNull Long rolesId,
        @NotNull @Valid UserDetailRequest detail,   // ចាំបាច់
        @Valid UserCompanyDto company,          // optional
        @NotNull @Valid UserAccountRequest account) {} // ចាំបាច់