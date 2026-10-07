package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.UserCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompanyEntity, Long> {
    Optional<UserCompanyEntity> findByUserId(Long userId);
}