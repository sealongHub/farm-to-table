package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
