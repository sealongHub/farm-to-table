package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.StocktransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionRepository
        extends JpaRepository<StocktransactionsEntity, Long>,
        JpaSpecificationExecutor<StocktransactionsEntity> {
}