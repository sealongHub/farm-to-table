package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.OrderDetail.*;
import farm_to_table.farm_to_table.Service.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService service;

    @PostMapping
    public ResponseEntity<OrderDetailResponseDto> create(@Valid @RequestBody OrderDetailRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailResponseDto>> getAll(
            @RequestParam(required = false) String orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(orderId, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody OrderDetailRequestDto request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}