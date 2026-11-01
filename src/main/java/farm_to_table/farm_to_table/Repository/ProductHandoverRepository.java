package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.ProductHandoversEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHandoverRepository extends JpaRepository<ProductHandoversEntity, Long> {

    boolean existsByStatusId(String statusId);

    @Query("""
            SELECT h FROM ProductHandoversEntity h
            WHERE (:fromType = '' OR h.fromType = :fromType)
              AND (:fromId = '' OR h.fromId = :fromId)
              AND (:toType = '' OR h.toType = :toType)
              AND (:toId = '' OR h.toId = :toId)
              AND (:statusId = '' OR h.statusId = :statusId)
            """)
    Page<ProductHandoversEntity> search(@Param("fromType") String fromType,
                                        @Param("fromId") String fromId,
                                        @Param("toType") String toType,
                                        @Param("toId") String toId,
                                        @Param("statusId") String statusId,
                                        Pageable pageable);
}