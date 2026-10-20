package farm_to_table.farm_to_table.DTO.Customer;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    private Long id;
    private String fullNameKh;
    private String fullNameEn;
    private String email;
    private String status;
    private String phoneNumber;
    private String image;
}