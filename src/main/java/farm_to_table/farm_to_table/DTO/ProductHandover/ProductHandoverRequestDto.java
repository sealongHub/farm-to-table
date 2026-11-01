package farm_to_table.farm_to_table.DTO.ProductHandover;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductHandoverRequestDto {

    @NotBlank(message = "fromType is required")
    private String fromType;

    @NotBlank(message = "fromId is required")
    private String fromId;

    @NotBlank(message = "toType is required")
    private String toType;

    @NotBlank(message = "toId is required")
    private String toId;

    @NotNull(message = "qty is required")
    @Positive(message = "qty must be greater than 0")
    private Integer qty;

    @Size(max = 255, message = "note must be at most 255 characters")
    private String note;
}
