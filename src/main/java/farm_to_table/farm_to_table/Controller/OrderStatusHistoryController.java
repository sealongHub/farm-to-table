package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.OrderStatusHistory.*;
import farm_to_table.farm_to_table.Service.OrderStatusHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-status-histories")
@RequiredArgsConstructor
public class OrderStatusHistoryController {

    private final OrderStatusHistoryService service;

    @PostMapping
    public ResponseEntity<OrderStatusHistoryResponseDto> create(
            @Valid @RequestBody OrderStatusHistoryRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStatusHistoryResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderStatusHistoryResponseDto>> getAll(
            @RequestParam(required = false) String orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(orderId, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderStatusHistoryResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusHistoryRequestDto request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
