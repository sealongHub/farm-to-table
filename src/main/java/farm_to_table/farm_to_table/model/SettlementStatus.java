package farm_to_table.farm_to_table.model;

import java.util.EnumSet;
import java.util.Set;

public enum SettlementStatus {
    PENDING, PROCESSING, COMPLETED, FAILED;

    public Set<SettlementStatus> allowedNext() {
        return switch (this) {
            case PENDING    -> EnumSet.of(PROCESSING);
            case PROCESSING -> EnumSet.of(COMPLETED, FAILED);
            case FAILED     -> EnumSet.of(PENDING);
            case COMPLETED  -> EnumSet.noneOf(SettlementStatus.class);
        };
    }

    public boolean canChangeTo(SettlementStatus next) {
        return allowedNext().contains(next);
    }
}