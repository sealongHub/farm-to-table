package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.DeliveryMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethodEntity, Long> {
}
