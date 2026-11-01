package farm_to_table.farm_to_table.DTO.WareHouse;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WareHouseResponseDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private Double capacity;
    private Double capacityUnit;
}