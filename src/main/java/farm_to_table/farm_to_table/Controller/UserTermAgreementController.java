package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.UserTermAgreement.*;
import farm_to_table.farm_to_table.Service.UserTermAgreementService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/user-term-agreements")
@RequiredArgsConstructor
public class UserTermAgreementController {

    private final UserTermAgreementService service;

    @PostMapping
    public ResponseEntity<UserTermAgreementResponseDto> create(
            @Valid @RequestBody UserTermAgreementRequestDto dto,
            HttpServletRequest request) {
        String ip = clientIp(request);
        String userAgent = request.getHeader("User-Agent");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto, ip, userAgent));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserTermAgreementResponseDto> updateStatus(
            @PathVariable Long id, @Valid @RequestBody UserTermAgreementStatusDto dto) {
        return ResponseEntity.ok(service.updateStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTermAgreementResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // /api/user-term-agreements/check?userId=1&type=TERMS
    @GetMapping("/check")
    public ResponseEntity<AgreementCheckDto> check(@RequestParam String userId,
                                                   @RequestParam String type) {
        return ResponseEntity.ok(service.check(userId, type));
    }

    // /api/user-term-agreements?userId=1&termId=2&status=AGREED&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<UserTermAgreementResponseDto>> search(
            @RequestParam(defaultValue = "") String userId,
            @RequestParam(defaultValue = "") String termId,
            @RequestParam(defaultValue = "") String status,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(userId, termId, status, pageable));
    }

    private String clientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();   // IP ដំបូង = client ពិត
        }
        return request.getRemoteAddr();
    }
}
