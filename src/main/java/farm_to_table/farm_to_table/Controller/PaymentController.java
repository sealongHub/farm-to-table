package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.payment.PaymentRequestDto;
import farm_to_table.farm_to_table.DTO.payment.PaymentResponseDto;
import farm_to_table.farm_to_table.DTO.payment.PaymentStatusDto;
import farm_to_table.farm_to_table.DTO.payment.PaymentUpdateDto;
import farm_to_table.farm_to_table.Service.PaymentService;
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
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> create(@Valid @RequestBody PaymentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> update(@PathVariable Long id,
                                                     @Valid @RequestBody PaymentUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponseDto> updateStatus(@PathVariable Long id,
                                                           @Valid @RequestBody PaymentStatusDto dto) {
        return ResponseEntity.ok(service.updateStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/ref/{ref}")
    public ResponseEntity<PaymentResponseDto> getByRef(@PathVariable String ref) {
        return ResponseEntity.ok(service.getByRef(ref));
    }

    // /api/payments?orderId=1&customerId=1&status=PAID&keyword=PAY&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<PaymentResponseDto>> search(
            @RequestParam(defaultValue = "") String orderId,
            @RequestParam(defaultValue = "") String customerId,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String keyword,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(orderId, customerId, status, keyword, pageable));
    }
}
