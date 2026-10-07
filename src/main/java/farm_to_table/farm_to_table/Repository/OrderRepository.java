package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
