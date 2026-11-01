package farm_to_table.farm_to_table.DTO.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusRequestDto {

    @NotBlank(message = "code is required")
    @Size(max = 50, message = "code must be at most 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "code may contain only letters, numbers and _")
    private String code;

    @Size(max = 255, message = "description must be at most 255 characters")
    private String description;

    @NotBlank(message = "type is required")
    @Size(max = 50, message = "type must be at most 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "type may contain only letters, numbers and _")
    private String type;
}