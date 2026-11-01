package farm_to_table.farm_to_table.DTO.ProductImage;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductImageResponseDto {
    private Long id;
    private Long productId;
    private String path;
}