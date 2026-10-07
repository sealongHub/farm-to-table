package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.WarehouseStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseStockRepository extends JpaRepository<WarehouseStockEntity, Long> {
}
