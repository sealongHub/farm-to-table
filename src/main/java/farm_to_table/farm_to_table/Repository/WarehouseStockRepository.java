package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.WarehouseStockEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseStockRepository extends JpaRepository<WarehouseStockEntity, Long> {

    boolean existsByWarehouseIdAndProductId(String warehouseId, String productId);

    boolean existsByWarehouseId(String warehouseId);

    boolean existsByProductId(String productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM WarehouseStockEntity s WHERE s.id = :id")
    Optional<WarehouseStockEntity> findByIdForUpdate(@Param("id") Long id);

    // ថ្មី: lock ជួរស្តុកតាម warehouse + product (ប្រើក្នុង Stock Transaction)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM WarehouseStockEntity s WHERE s.warehouseId = :warehouseId AND s.productId = :productId")
    Optional<WarehouseStockEntity> findForUpdate(@Param("warehouseId") String warehouseId,
                                                 @Param("productId") String productId);

    @Query("""
            SELECT s FROM WarehouseStockEntity s
            WHERE (:warehouseId = '' OR s.warehouseId = :warehouseId)
              AND (:productId = '' OR s.productId = :productId)
              AND s.qty <= :maxQty
            """)
    Page<WarehouseStockEntity> search(@Param("warehouseId") String warehouseId,
                                      @Param("productId") String productId,
                                      @Param("maxQty") int maxQty,
                                      Pageable pageable);
}