package farm_to_table.farm_to_table.DTO.FeeDelivery;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeeDeliveryResponseDto {
    private Long id;
    private String code;
    private Double amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
