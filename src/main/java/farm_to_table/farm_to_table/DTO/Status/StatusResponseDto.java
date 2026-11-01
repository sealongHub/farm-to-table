package farm_to_table.farm_to_table.DTO.Status;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponseDto {

    private Long id;
    private String code;
    private String description;
    private String type;
}