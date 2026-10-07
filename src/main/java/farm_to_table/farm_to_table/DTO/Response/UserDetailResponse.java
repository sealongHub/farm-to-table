package farm_to_table.farm_to_table.DTO.Response;

import java.time.LocalDate;

public record UserDetailResponse(
        Long id,
        Long userId,
        String nationality,
        String cardId,
        String address,
        LocalDate dateOfBirth,
        String placeOfBirth,
        String maritalStatus) {}