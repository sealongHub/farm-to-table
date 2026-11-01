package farm_to_table.farm_to_table.DTO.WareHouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WareHouseRequestDto {

    @NotBlank(message = "name is required")
    @Size(max = 100, message = "name must be at most 100 characters")
    private String name;

    @Size(max = 255, message = "description must be at most 255 characters")
    private String description;

    @NotBlank(message = "address is required")
    @Size(max = 255, message = "address must be at most 255 characters")
    private String address;

    @NotNull(message = "capacity is required")
    @Positive(message = "capacity must be greater than 0")
    private Double capacity;

    @PositiveOrZero(message = "capacityUnit must be 0 or greater")
    private Double capacityUnit;
}
