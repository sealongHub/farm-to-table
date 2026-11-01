package farm_to_table.farm_to_table.DTO.ProductHandover;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductHandoverStatusDto {

    @NotBlank(message = "status is required")
    private String status;

    private String receiveBy;   // ត្រូវផ្ញើពេល status = ACCEPTED
}
