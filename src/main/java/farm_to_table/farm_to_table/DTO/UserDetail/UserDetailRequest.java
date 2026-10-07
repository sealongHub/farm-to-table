package farm_to_table.farm_to_table.DTO.UserDetail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDetailRequest(
        String userId,
        @NotBlank String nationality,
        @NotBlank String cardId,
        @NotBlank String address,
        @NotNull LocalDate dateOfBirth,
        String placeOfBirth,
        String maritalStatus,
        String userDetailId) {}