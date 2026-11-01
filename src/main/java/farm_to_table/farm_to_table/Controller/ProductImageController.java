package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.ProductImage.*;
import farm_to_table.farm_to_table.Service.ProductImageService;
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
@RequestMapping("/api/products/{productId}/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService service;

    @PostMapping
    public ResponseEntity<ProductImageResponseDto> create(@PathVariable Long productId,
                                                          @Valid @RequestBody ProductImageRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(productId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductImageResponseDto> update(@PathVariable Long productId,
                                                          @PathVariable Long id,
                                                          @Valid @RequestBody ProductImageUpdateDto dto) {
        return ResponseEntity.ok(service.update(productId, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long productId, @PathVariable Long id) {
        service.delete(productId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductImageResponseDto> getById(@PathVariable Long productId,
                                                           @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(productId, id));
    }

    // /api/products/1/images?path=banana&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<ProductImageResponseDto>> search(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "") String path,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.search(productId, path, pageable));
    }
}