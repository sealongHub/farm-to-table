package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.ProductRejectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRejectRepository extends JpaRepository<ProductRejectEntity, Long> {

    List<ProductRejectEntity> findByUserId(String userId);

    List<ProductRejectEntity> findByCategoryId(String categoryId);

    List<ProductRejectEntity> findByStatus(Boolean status);

    // Pass null to ignore a filter
    @Query("""
            SELECT r FROM ProductRejectEntity r
            WHERE (:userId IS NULL OR r.userId = :userId)
              AND (:categoryId IS NULL OR r.categoryId = :categoryId)
              AND (:status IS NULL OR r.status = :status)
              AND (:keyword IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<ProductRejectEntity> search(@Param("userId") String userId,
                                     @Param("categoryId") String categoryId,
                                     @Param("status") Boolean status,
                                     @Param("keyword") String keyword,
                                     Pageable pageable);
}
