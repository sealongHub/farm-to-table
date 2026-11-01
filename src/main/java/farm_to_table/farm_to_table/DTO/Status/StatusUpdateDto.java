package farm_to_table.farm_to_table.DTO.Status;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDto {

    @Size(max = 255, message = "description must be at most 255 characters")
    private String description;
}