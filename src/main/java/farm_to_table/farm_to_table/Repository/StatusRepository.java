package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.StatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

    Optional<StatusEntity> findByCodeAndType(String code, String type);

    boolean existsByCodeAndType(String code, String type);

    List<StatusEntity> findByTypeOrderByIdAsc(String type);

    // ដាក់ "" ដើម្បីមិនត្រង (filter)
    @Query("""
            SELECT s FROM StatusEntity s
            WHERE (:type = '' OR s.type = :type)
              AND (:keyword = ''
                   OR LOWER(s.code) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<StatusEntity> search(@Param("keyword") String keyword,
                              @Param("type") String type,
                              Pageable pageable);
}