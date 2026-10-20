package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    // Pass "" to ignore a filter
    @Query("""
            SELECT c FROM CustomerEntity c
            WHERE (:status = '' OR c.status = :status)
              AND (:keyword = ''
                   OR LOWER(c.fullNameEn) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR c.fullNameKh LIKE CONCAT('%', :keyword, '%')
                   OR c.phoneNumber LIKE CONCAT('%', :keyword, '%'))
            """)
    Page<CustomerEntity> search(@Param("keyword") String keyword,
                                @Param("status") String status,
                                Pageable pageable);
}