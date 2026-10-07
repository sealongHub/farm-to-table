package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
}
