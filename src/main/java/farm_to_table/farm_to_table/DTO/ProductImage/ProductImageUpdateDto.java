package farm_to_table.farm_to_table.DTO.ProductImage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductImageUpdateDto {

    @NotBlank(message = "path is required")
    @Size(max = 500, message = "path must be at most 500 characters")
    private String path;
}