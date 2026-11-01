package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Status.StatusRequestDto;
import farm_to_table.farm_to_table.DTO.Status.StatusResponseDto;
import farm_to_table.farm_to_table.DTO.Status.StatusUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusService {

    StatusResponseDto create(StatusRequestDto dto);

    StatusResponseDto update(Long id, StatusUpdateDto dto);

    void delete(Long id);

    StatusResponseDto getById(Long id);

    // សម្រាប់ដាក់ក្នុង dropdown
    List<StatusResponseDto> getByType(String type);

    Page<StatusResponseDto> search(String keyword, String type, Pageable pageable);
}
