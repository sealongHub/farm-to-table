package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.Product.ProductRejectDto;
import farm_to_table.farm_to_table.Service.ProductRejectService;
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
@RequestMapping("/api/product-rejects")
@RequiredArgsConstructor
public class ProductRejectController {

    private final ProductRejectService service;

    @PostMapping
    public ResponseEntity<ProductRejectDto> create(@Valid @RequestBody ProductRejectDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRejectDto> update(@PathVariable Long id,
                                                   @Valid @RequestBody ProductRejectDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRejectDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // All filters are optional: /api/product-rejects?userId=U1&status=false&keyword=tom&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<ProductRejectDto>> search(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.search(userId, categoryId, status, keyword, pageable));
    }
}
