package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.Product.ProductCheckerDto;
import farm_to_table.farm_to_table.Service.ProductCheckerService;
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
@RequestMapping("/api/product-checkers")
@RequiredArgsConstructor
public class ProductCheckerController {

    private final ProductCheckerService service;

    @PostMapping
    public ResponseEntity<ProductCheckerDto> create(@Valid @RequestBody ProductCheckerDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCheckerDto> update(@PathVariable Long id,
                                                    @Valid @RequestBody ProductCheckerDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteByProduct(@PathVariable String productId) {
        service.deleteByProductId(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCheckerDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductCheckerDto>> getByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(service.getByProductId(productId));
    }

    @GetMapping
    public ResponseEntity<Page<ProductCheckerDto>> getAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }
}