package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.ProductCheckerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCheckerRepository extends JpaRepository<ProductCheckerEntity, Long> {

    List<ProductCheckerEntity> findByProductIdOrderByIdDesc(String productId);

    List<ProductCheckerEntity> findByCheckBy(String checkBy);

    long deleteByProductId(String productId);
}
