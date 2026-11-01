package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.TermsConditionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermsConditionRepository extends JpaRepository<TermsConditionEntity, Long> {

    boolean existsByTypeAndVersion(String type, String version);

    boolean existsByTypeAndVersionAndIdNot(String type, String version, Long id);

    List<TermsConditionEntity> findByTypeAndStatus(String type, String status);

    @Query("""
            SELECT t FROM TermsConditionEntity t
            WHERE (:type = '' OR t.type = :type)
              AND (:status = '' OR t.status = :status)
              AND (:keyword = '' OR LOWER(t.version) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<TermsConditionEntity> search(@Param("type") String type,
                                      @Param("status") String status,
                                      @Param("keyword") String keyword,
                                      Pageable pageable);
}
