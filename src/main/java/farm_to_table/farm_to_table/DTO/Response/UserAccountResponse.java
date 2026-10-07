package farm_to_table.farm_to_table.DTO.Response;

public record UserAccountResponse(
        Long id, String accountName, String accountType, String accountNumber,
        String bankName, Boolean isVerified, Boolean isDefault) {}