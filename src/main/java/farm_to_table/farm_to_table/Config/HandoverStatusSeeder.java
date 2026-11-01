package farm_to_table.farm_to_table.Config;

import farm_to_table.farm_to_table.Repository.StatusRepository;
import farm_to_table.farm_to_table.model.StatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HandoverStatusSeeder implements CommandLineRunner {

    private static final String HANDOVER = "HANDOVER";

    private final StatusRepository repository;

    @Override
    @Transactional
    public void run(String... args) {
        seed("PENDING",   "Waiting for the receiver");
        seed("ACCEPTED",  "Receiver accepted the handover");
        seed("REJECTED",  "Receiver rejected the handover");
        seed("CANCELLED", "Handover cancelled");
    }

    private void seed(String code, String description) {
        if (!repository.existsByCodeAndType(code, HANDOVER)) {
            StatusEntity s = new StatusEntity();
            s.setCode(code);
            s.setDescription(description);
            s.setType(HANDOVER);
            repository.save(s);
        }
    }
}