package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.ProductHandover.*;
import farm_to_table.farm_to_table.Service.ProductHandoverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-handovers")
@RequiredArgsConstructor
public class ProductHandoverController {

    private final ProductHandoverService service;

    @PostMapping
    public ResponseEntity<ProductHandoverResponseDto> create(
            @Valid @RequestBody ProductHandoverRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductHandoverResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody ProductHandoverRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductHandoverResponseDto> updateStatus(
            @PathVariable Long id, @Valid @RequestBody ProductHandoverStatusDto dto) {
        return ResponseEntity.ok(service.updateStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductHandoverResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // /api/product-handovers?fromType=FARMER&toType=WAREHOUSE&status=PENDING&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<ProductHandoverResponseDto>> search(
            @RequestParam(defaultValue = "") String fromType,
            @RequestParam(defaultValue = "") String fromId,
            @RequestParam(defaultValue = "") String toType,
            @RequestParam(defaultValue = "") String toId,
            @RequestParam(defaultValue = "") String status,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(fromType, fromId, toType, toId, status, pageable));
    }
}