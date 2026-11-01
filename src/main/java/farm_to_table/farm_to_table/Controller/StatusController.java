package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.Status.StatusRequestDto;
import farm_to_table.farm_to_table.DTO.Status.StatusResponseDto;
import farm_to_table.farm_to_table.DTO.Status.StatusUpdateDto;
import farm_to_table.farm_to_table.Service.StatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService service;

    @PostMapping
    public ResponseEntity<StatusResponseDto> create(@Valid @RequestBody StatusRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusResponseDto> update(@PathVariable Long id,
                                                    @Valid @RequestBody StatusUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // /api/statuses/type/ORDER  (បញ្ជីពេញសម្រាប់ dropdown)
    @GetMapping("/type/{type}")
    public ResponseEntity<List<StatusResponseDto>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(service.getByType(type));
    }

    // /api/statuses?type=ORDER&keyword=pend&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<StatusResponseDto>> search(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String type,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(keyword, type, pageable));
    }
}