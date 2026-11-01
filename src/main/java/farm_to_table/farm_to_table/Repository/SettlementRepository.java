package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.SettlementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SettlementRepository extends JpaRepository<SettlementEntity, Long> {

    boolean existsBySettlementDateAndCcy(Date settlementDate, String ccy);

    boolean existsBySettlementDateAndCcyAndIdNot(Date settlementDate, String ccy, Long id);

    @Query("""
            SELECT s FROM SettlementEntity s
            WHERE (:ccy = '' OR s.ccy = :ccy)
              AND (:status = '' OR s.status = :status)
              AND s.settlementDate >= :fromDate
              AND s.settlementDate <= :toDate
            """)
    Page<SettlementEntity> search(@Param("ccy") String ccy,
                                  @Param("status") String status,
                                  @Param("fromDate") Date fromDate,
                                  @Param("toDate") Date toDate,
                                  Pageable pageable);
}
