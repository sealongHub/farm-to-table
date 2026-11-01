package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.FeeDelivery.FeeDeliveryRequestDto;
import farm_to_table.farm_to_table.DTO.FeeDelivery.FeeDeliveryResponseDto;
import farm_to_table.farm_to_table.Service.FeeDeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-fees")
@RequiredArgsConstructor
public class FeeDeliveryController {
    private final FeeDeliveryService service;

    @PostMapping
    public ResponseEntity<FeeDeliveryResponseDto> create(@Valid @RequestBody FeeDeliveryRequestDto r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));
    }
    @GetMapping("/{id}")
    public ResponseEntity<FeeDeliveryResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping
    public ResponseEntity<List<FeeDeliveryResponseDto>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(keyword, page, size));
    }
    @PutMapping("/{id}")
    public ResponseEntity<FeeDeliveryResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody FeeDeliveryRequestDto r) {
        return ResponseEntity.ok(service.update(id, r));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
