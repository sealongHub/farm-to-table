package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.FarmerPaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface FarmerPaymentRepository extends JpaRepository<FarmerPaymentEntity, Long> {

    boolean existsByTransactionRef(String transactionRef);

    boolean existsBySettlementIdAndStatusIn(String settlementId, Collection<String> statuses);

    boolean existsBySettlementIdAndStatusInAndIdNot(String settlementId,
                                                    Collection<String> statuses, Long id);

    Optional<FarmerPaymentEntity> findByTransactionRef(String transactionRef);

    @Query("""
            SELECT p FROM FarmerPaymentEntity p
            WHERE (:accountId = '' OR p.accountId = :accountId)
              AND (:settlementId = '' OR p.settlementId = :settlementId)
              AND (:status = '' OR p.status = :status)
              AND (:keyword = '' OR LOWER(p.transactionRef) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<FarmerPaymentEntity> search(@Param("accountId") String accountId,
                                     @Param("settlementId") String settlementId,
                                     @Param("status") String status,
                                     @Param("keyword") String keyword,
                                     Pageable pageable);
}