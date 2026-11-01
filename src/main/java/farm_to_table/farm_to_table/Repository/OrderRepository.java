package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByOrderNumber(String orderNumber);

    boolean existsByStatusId(String statusId);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    @Query("""
            SELECT o FROM OrderEntity o
            WHERE (:customerId = '' OR o.customerId = :customerId)
              AND (:status = '' OR o.orderStatus = :status)
              AND (:keyword = '' OR LOWER(o.orderNumber) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<OrderEntity> search(@Param("customerId") String customerId,
                             @Param("status") String status,
                             @Param("keyword") String keyword,
                             Pageable pageable);
}
