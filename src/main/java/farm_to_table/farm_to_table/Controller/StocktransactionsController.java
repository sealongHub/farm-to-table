package farm_to_table.farm_to_table.Controller;

import farm_to_table.farm_to_table.DTO.StockTransaction.StockTransactionRequestDto;
import farm_to_table.farm_to_table.DTO.StockTransaction.StockTransactionResponseDto;
import farm_to_table.farm_to_table.Service.StocktransactionsService;
import farm_to_table.farm_to_table.model.StockTransactionType;
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
@RequestMapping("/api/stock-transactions")
@RequiredArgsConstructor
public class StocktransactionsController {

    private final StocktransactionsService service;

    @PostMapping
    public ResponseEntity<StockTransactionResponseDto> create(
            @Valid @RequestBody StockTransactionRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockTransactionResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // /api/stock-transactions?warehouseId=1&productId=1&type=IN&from=2026-11-01&to=2026-11-30&page=0&size=10
    @GetMapping
    public ResponseEntity<Page<StockTransactionResponseDto>> search(
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) StockTransactionType type,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(service.search(warehouseId, productId, type, statusId, from, to, pageable));
    }
}
