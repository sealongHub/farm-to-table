package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.model.StocktransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockTransactionRepository extends JpaRepository<StocktransactionsEntity, Long> {
}
