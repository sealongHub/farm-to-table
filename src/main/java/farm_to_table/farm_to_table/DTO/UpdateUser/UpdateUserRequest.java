package farm_to_table.farm_to_table.DTO.UpdateUser;

import farm_to_table.farm_to_table.DTO.UserAccount.UserAccountRequest;
import farm_to_table.farm_to_table.DTO.UserCompany.UserCompanyDto;
import farm_to_table.farm_to_table.DTO.UserDetail.UserDetailRequest;
import jakarta.validation.Valid;

public record UpdateUserRequest(
        String fullName,
        String image,
        String status,
        Long rolesId,
        @Valid UserDetailRequest detail,
        @Valid UserCompanyDto company,
        @Valid UserAccountRequest account) {}