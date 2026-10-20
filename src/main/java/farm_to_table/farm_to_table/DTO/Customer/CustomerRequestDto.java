package farm_to_table.farm_to_table.DTO.Customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {

    @NotBlank(message = "fullNameKh is required")
    private String fullNameKh;

    @NotBlank(message = "fullNameEn is required")
    private String fullNameEn;

    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    private String email;

    // Required on create, optional on update (only changes it if provided)
    @Size(min = 6, max = 100, message = "password must be 6-100 characters")
    private String password;

    private String status;

    @Pattern(regexp = "^[0-9+\\-\\s]{8,15}$", message = "phoneNumber is not valid")
    private String phoneNumber;

    private String image;
}