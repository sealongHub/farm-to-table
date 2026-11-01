package farm_to_table.farm_to_table.DTO.UserTermAgreement;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AgreementCheckDto {
    private String userId;
    private String type;
    private String termId;
    private String version;
    private boolean agreed;
}