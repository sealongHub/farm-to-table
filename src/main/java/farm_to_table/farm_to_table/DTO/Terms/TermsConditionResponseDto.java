package farm_to_table.farm_to_table.DTO.Terms;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TermsConditionResponseDto {
    private Long id;
    private String type;
    private String version;
    private String content;
    private String status;
}
