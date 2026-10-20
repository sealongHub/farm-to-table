package farm_to_table.farm_to_table.DTO.Product;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCheckerDto {

    private Long id;

    @NotBlank(message = "productId is required")
    private String productId;

    @NotBlank(message = "checkBy is required")
    private String checkBy;

    private String checkedAt;
}