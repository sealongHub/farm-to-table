package farm_to_table.farm_to_table.DTO.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentMethodUpdateDto {

    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title must be at most 100 characters")
    private String title;

    @Size(max = 255, message = "description must be at most 255 characters")
    private String description;
}
