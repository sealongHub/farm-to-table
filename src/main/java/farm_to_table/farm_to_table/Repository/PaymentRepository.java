package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
