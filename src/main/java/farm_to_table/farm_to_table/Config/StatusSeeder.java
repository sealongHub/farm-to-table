package farm_to_table.farm_to_table.Config;

import farm_to_table.farm_to_table.Repository.StatusRepository;
import farm_to_table.farm_to_table.model.StatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StatusSeeder implements CommandLineRunner {

    private static final String ORDER = "ORDER";

    private final StatusRepository repository;

    @Override
    @Transactional
    public void run(String... args) {
        seed("PENDING",   "Waiting for confirmation");
        seed("CONFIRMED", "Order confirmed");
        seed("SHIPPING",  "On the way to the customer");
        seed("DELIVERED", "Delivered to the customer");
        seed("CANCELLED", "Order cancelled");
    }

    private void seed(String code, String description) {
        if (!repository.existsByCodeAndType(code, ORDER)) {
            StatusEntity s = new StatusEntity();
            s.setCode(code);
            s.setDescription(description);
            s.setType(ORDER);
            repository.save(s);
        }
    }
}
