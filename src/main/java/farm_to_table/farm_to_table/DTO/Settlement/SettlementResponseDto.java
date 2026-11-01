package farm_to_table.farm_to_table.DTO.Settlement;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SettlementResponseDto {
    private Long id;
    private LocalDate settlementDate;
    private String ccy;
    private Double totalAmount;
    private String status;
    private Date processAt;
}