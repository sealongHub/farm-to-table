package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.Settlement.*;
import farm_to_table.farm_to_table.Service.SettlementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService service;

    @PostMapping
    public ResponseEntity<SettlementResponseDto> create(@Valid @RequestBody SettlementRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SettlementResponseDto> update(@PathVariable Long id,
                                                        @Valid @RequestBody SettlementUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SettlementResponseDto> updateStatus(@PathVariable Long id,
                                                              @Valid @RequestBody SettlementStatusDto dto) {
        return ResponseEntity.ok(service.updateStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SettlementResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // /api/settlements?ccy=USD&status=PENDING&from=2026-10-01&to=2026-10-31&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<SettlementResponseDto>> search(
            @RequestParam(defaultValue = "") String ccy,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(ccy, status, from, to, pageable));
    }
}
