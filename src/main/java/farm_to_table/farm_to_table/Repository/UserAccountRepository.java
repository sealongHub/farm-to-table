package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
    List<UserAccountEntity> findByUserId(Long userId);
}