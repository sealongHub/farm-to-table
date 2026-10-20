package farm_to_table.farm_to_table.DTO.Product;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRejectDto {

    private Long id;

    @NotBlank(message = "userId is required")
    private String userId;

    @NotBlank(message = "categoryId is required")
    private String categoryId;

    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "qty is required")
    @PositiveOrZero(message = "qty must not be negative")
    private Integer qty;

    @PositiveOrZero(message = "unitPrice must not be negative")
    private Double unitPrice;

    @PositiveOrZero(message = "farmerPrice must not be negative")
    private Double farmerPrice;

    private Date expectedHavestDate;
    private Date expiredDate;
    private String location;
    private String reason;
    private Integer availableQty;
    private String soldQty;
    private Boolean status;
}