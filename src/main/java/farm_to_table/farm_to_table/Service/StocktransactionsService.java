package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.StockTransaction.StockTransactionRequestDto;
import farm_to_table.farm_to_table.DTO.StockTransaction.StockTransactionResponseDto;
import farm_to_table.farm_to_table.model.StockTransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface StocktransactionsService {

    StockTransactionResponseDto create(StockTransactionRequestDto dto);

    StockTransactionResponseDto getById(Long id);

    Page<StockTransactionResponseDto> search(Long warehouseId, Long productId,
                                             StockTransactionType type, Long statusId,
                                             LocalDate from, LocalDate to, Pageable pageable);
}
