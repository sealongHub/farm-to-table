package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.WarehouseStock.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WarehouseStockService {

    WarehouseStockResponseDto create(WarehouseStockRequestDto dto);

    WarehouseStockResponseDto update(Long id, WarehouseStockUpdateDto dto);

    WarehouseStockResponseDto adjust(Long id, WarehouseStockAdjustDto dto);

    WarehouseStockResponseDto reserve(Long id, WarehouseStockReserveDto dto);

    WarehouseStockResponseDto release(Long id, WarehouseStockReserveDto dto);

    void delete(Long id);

    WarehouseStockResponseDto getById(Long id);

    // lowStock: បង្ហាញតែជួរដែល qty <= lowStock (null = មិនត្រង)
    Page<WarehouseStockResponseDto> search(String warehouseId, String productId,
                                           Integer lowStock, Pageable pageable);
}
