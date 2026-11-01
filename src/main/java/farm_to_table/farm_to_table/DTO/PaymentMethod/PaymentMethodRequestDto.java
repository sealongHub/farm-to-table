package farm_to_table.farm_to_table.DTO.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentMethodRequestDto {

    @NotBlank(message = "code is required")
    @Size(max = 50, message = "code must be at most 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "code may contain only letters, numbers and _")
    private String code;

    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title must be at most 100 characters")
    private String title;

    @Size(max = 255, message = "description must be at most 255 characters")
    private String description;
}
