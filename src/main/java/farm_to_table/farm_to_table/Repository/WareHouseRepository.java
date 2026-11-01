package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.WareHouseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouseEntity, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    @Query("""
            SELECT w FROM WareHouseEntity w
            WHERE (:keyword = ''
                   OR LOWER(w.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(w.address) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<WareHouseEntity> search(@Param("keyword") String keyword, Pageable pageable);
}
