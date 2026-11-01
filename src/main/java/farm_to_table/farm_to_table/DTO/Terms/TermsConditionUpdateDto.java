package farm_to_table.farm_to_table.DTO.Terms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TermsConditionUpdateDto {

    @NotBlank(message = "version is required")
    @Size(max = 20, message = "version must be at most 20 characters")
    @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "version may contain only letters, numbers, . _ and -")
    private String version;

    @NotBlank(message = "content is required")
    private String content;
}
