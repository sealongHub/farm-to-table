package farm_to_table.farm_to_table.model;

import java.util.EnumSet;
import java.util.Set;

public enum HandoverStatus {
    PENDING, ACCEPTED, REJECTED, CANCELLED;

    public Set<HandoverStatus> allowedNext() {
        return switch (this) {
            case PENDING -> EnumSet.of(ACCEPTED, REJECTED, CANCELLED);
            case ACCEPTED, REJECTED, CANCELLED -> EnumSet.noneOf(HandoverStatus.class);
        };
    }

    public boolean canChangeTo(HandoverStatus next) {
        return allowedNext().contains(next);
    }
}