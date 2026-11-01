package farm_to_table.farm_to_table.DTO.ProductHandover;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductHandoverResponseDto {
    private Long id;
    private String fromType;
    private String fromId;
    private String toType;
    private String toId;
    private Integer qty;
    private String statusId;
    private String status;      // code ឧ. PENDING
    private String receiveBy;
    private String note;
}