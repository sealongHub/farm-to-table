package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {
}
