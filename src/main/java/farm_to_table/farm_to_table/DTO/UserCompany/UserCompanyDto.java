package farm_to_table.farm_to_table.DTO.UserCompany;

import jakarta.validation.constraints.NotBlank;

public record UserCompanyDto(
        @NotBlank String companyName,
        String address,
        Integer operationYears,
        String map,
        String partner,
        String phoneNumber,
        String type) {}