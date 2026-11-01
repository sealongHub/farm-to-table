package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.WareHouse.WareHouseRequestDto;
import farm_to_table.farm_to_table.DTO.WareHouse.WareHouseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WareHouseService {

    WareHouseResponseDto create(WareHouseRequestDto dto);

    WareHouseResponseDto update(Long id, WareHouseRequestDto dto);

    void delete(Long id);

    WareHouseResponseDto getById(Long id);

    // បញ្ជីពេញ សម្រាប់ dropdown
    List<WareHouseResponseDto> getAll();

    Page<WareHouseResponseDto> search(String keyword, Pageable pageable);
}
