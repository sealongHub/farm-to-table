package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.WarehouseStock.*;
import farm_to_table.farm_to_table.Service.WarehouseStockService;
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
@RequestMapping("/api/warehouse-stocks")
@RequiredArgsConstructor
public class WarehouseStockController {

    private final WarehouseStockService service;

    @PostMapping
    public ResponseEntity<WarehouseStockResponseDto> create(
            @Valid @RequestBody WarehouseStockRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseStockResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody WarehouseStockUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/adjust")
    public ResponseEntity<WarehouseStockResponseDto> adjust(
            @PathVariable Long id, @Valid @RequestBody WarehouseStockAdjustDto dto) {
        return ResponseEntity.ok(service.adjust(id, dto));
    }

    @PatchMapping("/{id}/reserve")
    public ResponseEntity<WarehouseStockResponseDto> reserve(
            @PathVariable Long id, @Valid @RequestBody WarehouseStockReserveDto dto) {
        return ResponseEntity.ok(service.reserve(id, dto));
    }

    @PatchMapping("/{id}/release")
    public ResponseEntity<WarehouseStockResponseDto> release(
            @PathVariable Long id, @Valid @RequestBody WarehouseStockReserveDto dto) {
        return ResponseEntity.ok(service.release(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseStockResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // /api/warehouse-stocks?warehouseId=1&productId=1&lowStock=10&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<WarehouseStockResponseDto>> search(
            @RequestParam(defaultValue = "") String warehouseId,
            @RequestParam(defaultValue = "") String productId,
            @RequestParam(required = false) Integer lowStock,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(warehouseId, productId, lowStock, pageable));
    }
}
