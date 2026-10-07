package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
