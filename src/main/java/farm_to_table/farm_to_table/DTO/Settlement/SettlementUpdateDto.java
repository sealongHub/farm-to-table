package farm_to_table.farm_to_table.DTO.Settlement;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SettlementUpdateDto {

    @NotNull(message = "settlementDate is required")
    private LocalDate settlementDate;

    @NotNull(message = "ccy is required")
    @Pattern(regexp = "^[A-Za-z]{3}$", message = "ccy must be 3 letters, e.g. USD or KHR")
    private String ccy;

    @NotNull(message = "totalAmount is required")
    @Positive(message = "totalAmount must be greater than 0")
    private Double totalAmount;
}
