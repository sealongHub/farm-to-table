package farm_to_table.farm_to_table.DTO.PaymentMethod;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentMethodResponseDto {
    private Long id;
    private String code;
    private String title;
    private String description;
}
