package farm_to_table.farm_to_table.DTO.DeliveryMethod;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryMethodResponseDto {
    private Long id;
    private String code;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
