package farm_to_table.farm_to_table.DTO.FarmerPayment;

import lombok.*;

import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FarmerPaymentResponseDto {
    private Long id;
    private String accountId;
    private String accountName;
    private String bankName;
    private String accountNumber;
    private String userId;
    private String userFullName;
    private String settlementId;
    private Double amount;
    private String ccy;
    private String status;
    private String transactionRef;
    private Date paidAt;
}
