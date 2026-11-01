package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.FarmerPayment.*;
import farm_to_table.farm_to_table.Service.FarmerPaymentService;
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
@RequestMapping("/api/farmer-payments")
@RequiredArgsConstructor
public class FarmerPaymentController {

    private final FarmerPaymentService service;

    @PostMapping
    public ResponseEntity<FarmerPaymentResponseDto> create(
            @Valid @RequestBody FarmerPaymentRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarmerPaymentResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody FarmerPaymentUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FarmerPaymentResponseDto> updateStatus(
            @PathVariable Long id, @Valid @RequestBody FarmerPaymentStatusDto dto) {
        return ResponseEntity.ok(service.updateStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmerPaymentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/ref/{transactionRef}")
    public ResponseEntity<FarmerPaymentResponseDto> getByRef(@PathVariable String transactionRef) {
        return ResponseEntity.ok(service.getByRef(transactionRef));
    }

    // /api/farmer-payments?accountId=1&settlementId=1&status=PAID&keyword=FPAY&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<FarmerPaymentResponseDto>> search(
            @RequestParam(defaultValue = "") String accountId,
            @RequestParam(defaultValue = "") String settlementId,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String keyword,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(accountId, settlementId, status, keyword, pageable));
    }
}
