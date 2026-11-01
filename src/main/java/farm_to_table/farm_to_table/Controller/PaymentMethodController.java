package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.PaymentMethod.*;
import farm_to_table.farm_to_table.Service.PaymentMethodService;
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
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService service;

    @PostMapping
    public ResponseEntity<PaymentMethodResponseDto> create(@Valid @RequestBody PaymentMethodRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodResponseDto> update(@PathVariable Long id,
                                                           @Valid @RequestBody PaymentMethodUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // បញ្ជីពេញ សម្រាប់ dropdown
    @GetMapping("/all")
    public ResponseEntity<List<PaymentMethodResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping
    public ResponseEntity<Page<PaymentMethodResponseDto>> search(
            @RequestParam(defaultValue = "") String keyword,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(keyword, pageable));
    }
}
