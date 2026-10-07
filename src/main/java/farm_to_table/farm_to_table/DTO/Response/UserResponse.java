package farm_to_table.farm_to_table.DTO.Response;

import farm_to_table.farm_to_table.DTO.UserCompany.UserCompanyDto;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
        Long id, String username, String fullName, String email, String status, String image,
        RoleResponse role,
        UserDetailResponse detail,
        UserCompanyDto company,
        List<UserAccountResponse> accounts,
        LocalDateTime createdAt,
        String tempPassword) {}