package farm_to_table.farm_to_table.DTO.UserAccount;
import jakarta.validation.constraints.NotBlank;

public record UserAccountRequest(
        @NotBlank String accountName,
        @NotBlank String accountType,
        @NotBlank String accountNumber,
        @NotBlank String bankName) {}