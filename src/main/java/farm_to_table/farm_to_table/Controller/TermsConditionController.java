package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.Terms.*;
import farm_to_table.farm_to_table.Service.TermsConditionService;
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
@RequestMapping("/api/terms-conditions")
@RequiredArgsConstructor
public class TermsConditionController {

    private final TermsConditionService service;

    @PostMapping
    public ResponseEntity<TermsConditionResponseDto> create(
            @Valid @RequestBody TermsConditionRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TermsConditionResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody TermsConditionUpdateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TermsConditionResponseDto> updateStatus(
            @PathVariable Long id, @Valid @RequestBody TermsConditionStatusDto dto) {
        return ResponseEntity.ok(service.updateStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermsConditionResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // /api/terms-conditions/active/TERMS
    @GetMapping("/active/{type}")
    public ResponseEntity<TermsConditionResponseDto> getActive(@PathVariable String type) {
        return ResponseEntity.ok(service.getActive(type));
    }

    // /api/terms-conditions?type=TERMS&status=ACTIVE&keyword=1.0&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<TermsConditionResponseDto>> search(
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String keyword,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(type, status, keyword, pageable));
    }
}
