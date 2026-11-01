package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.PaymentMethodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {

    boolean existsByCode(String code);

    @Query("""
            SELECT m FROM PaymentMethodEntity m
            WHERE (:keyword = ''
                   OR LOWER(m.code) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<PaymentMethodEntity> search(@Param("keyword") String keyword, Pageable pageable);
}
