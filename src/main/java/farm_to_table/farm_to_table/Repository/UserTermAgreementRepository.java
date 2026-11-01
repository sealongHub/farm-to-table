package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.UserTermAgreementsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTermAgreementRepository extends JpaRepository<UserTermAgreementsEntity, Long> {

    boolean existsByUserIdAndTermIdAndStatus(String userId, String termId, String status);

    @Query("""
            SELECT a FROM UserTermAgreementsEntity a
            WHERE (:userId = '' OR a.userId = :userId)
              AND (:termId = '' OR a.termId = :termId)
              AND (:status = '' OR a.status = :status)
            """)
    Page<UserTermAgreementsEntity> search(@Param("userId") String userId,
                                          @Param("termId") String termId,
                                          @Param("status") String status,
                                          Pageable pageable);
}
