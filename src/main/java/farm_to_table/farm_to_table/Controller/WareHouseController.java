package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.WareHouse.WareHouseRequestDto;
import farm_to_table.farm_to_table.DTO.WareHouse.WareHouseResponseDto;
import farm_to_table.farm_to_table.Service.WareHouseService;
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
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WareHouseController {

    private final WareHouseService service;

    @PostMapping
    public ResponseEntity<WareHouseResponseDto> create(@Valid @RequestBody WareHouseRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WareHouseResponseDto> update(@PathVariable Long id,
                                                       @Valid @RequestBody WareHouseRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WareHouseResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // បញ្ជីពេញ សម្រាប់ dropdown
    @GetMapping("/all")
    public ResponseEntity<List<WareHouseResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // /api/warehouses?keyword=phnom&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<WareHouseResponseDto>> search(
            @RequestParam(defaultValue = "") String keyword,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(keyword, pageable));
    }
}
