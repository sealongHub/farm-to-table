package farm_to_table.farm_to_table.DTO.UserTermAgreement;

import lombok.*;

import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserTermAgreementResponseDto {
    private Long id;
    private String userId;
    private String termId;
    private String termType;      // ឧ. TERMS
    private String termVersion;   // ឧ. 2.0
    private Date agreeAt;
    private String ipAddress;
    private String userAgent;
    private String status;
}