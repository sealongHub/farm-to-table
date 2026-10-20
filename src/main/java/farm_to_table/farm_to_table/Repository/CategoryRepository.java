package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query(value = """
            CALL sp_insert_category(CAST(:name AS varchar), CAST(:description AS varchar),
                                    CAST(:status AS varchar), CAST(NULL AS bigint))
            """, nativeQuery = true)
    Long insertCategory(@Param("name") String name,
                        @Param("description") String description,
                        @Param("status") String status);

    @Modifying(clearAutomatically = true)
    @Query(value = """
            CALL sp_update_category(CAST(:id AS bigint), CAST(:name AS varchar),
                                    CAST(:description AS varchar), CAST(:status AS varchar))
            """, nativeQuery = true)
    void updateCategory(@Param("id") Long id,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("status") String status);

    @Modifying(clearAutomatically = true)
    @Query(value = "CALL sp_delete_category(CAST(:id AS bigint))", nativeQuery = true)
    void deleteCategory(@Param("id") Long id);

    @Query(value = "SELECT * FROM fn_get_category_by_id(:id)", nativeQuery = true)
    Optional<CategoryEntity> getCategoryById(@Param("id") Long id);

    @Query(value = "SELECT * FROM fn_get_all_categories()", nativeQuery = true)
    List<CategoryEntity> getAllCategories();
}
