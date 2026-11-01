package farm_to_table.farm_to_table.DTO.DeliveryMethod;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeliveryMethodRequestDto {
    @NotBlank(message = "code is required")
    private String code;
    @NotBlank(message = "title is required")
    private String title;
    private String description;
}