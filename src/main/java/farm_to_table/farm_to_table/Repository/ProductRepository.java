package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = """
            CALL sp_insert_product(
                CAST(:userId AS varchar), CAST(:categoryId AS varchar), CAST(:name AS varchar),
                CAST(:qty AS integer), CAST(:unitPrice AS double precision),
                CAST(:farmerPrice AS double precision),
                CAST(:expectedDate AS date), CAST(:expiredDate AS date),
                CAST(:location AS varchar), CAST(NULL AS bigint))
            """, nativeQuery = true)
    Long insertProduct(@Param("userId") String userId,
                       @Param("categoryId") String categoryId,
                       @Param("name") String name,
                       @Param("qty") Integer qty,
                       @Param("unitPrice") Double unitPrice,
                       @Param("farmerPrice") Double farmerPrice,
                       @Param("expectedDate") Date expectedDate,
                       @Param("expiredDate") Date expiredDate,
                       @Param("location") String location);

    @Modifying(clearAutomatically = true)
    @Query(value = """
            CALL sp_update_product(
                CAST(:id AS bigint),
                CAST(:userId AS varchar), CAST(:categoryId AS varchar), CAST(:name AS varchar),
                CAST(:qty AS integer), CAST(:unitPrice AS double precision),
                CAST(:farmerPrice AS double precision),
                CAST(:expectedDate AS date), CAST(:expiredDate AS date),
                CAST(:location AS varchar))
            """, nativeQuery = true)
    void updateProduct(@Param("id") Long id,
                       @Param("userId") String userId,
                       @Param("categoryId") String categoryId,
                       @Param("name") String name,
                       @Param("qty") Integer qty,
                       @Param("unitPrice") Double unitPrice,
                       @Param("farmerPrice") Double farmerPrice,
                       @Param("expectedDate") Date expectedDate,
                       @Param("expiredDate") Date expiredDate,
                       @Param("location") String location);

    @Modifying(clearAutomatically = true)
    @Query(value = "CALL sp_delete_product(CAST(:id AS bigint))", nativeQuery = true)
    void deleteProduct(@Param("id") Long id);

    @Query(value = "SELECT * FROM fn_get_product_by_id(:id)", nativeQuery = true)
    Optional<ProductEntity> getProductById(@Param("id") Long id);

    @Query(value = "SELECT * FROM fn_get_all_products()", nativeQuery = true)
    List<ProductEntity> getAllProducts();
}
