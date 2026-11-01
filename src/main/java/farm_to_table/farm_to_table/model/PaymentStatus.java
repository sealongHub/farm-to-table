package farm_to_table.farm_to_table.model;

import java.util.EnumSet;
import java.util.Set;

public enum PaymentStatus {
    PENDING, PAID, FAILED, REFUNDED;

    public Set<PaymentStatus> allowedNext() {
        return switch (this) {
            case PENDING  -> EnumSet.of(PAID, FAILED);
            case FAILED   -> EnumSet.of(PENDING);
            case PAID     -> EnumSet.of(REFUNDED);
            case REFUNDED -> EnumSet.noneOf(PaymentStatus.class);
        };
    }

    public boolean canChangeTo(PaymentStatus next) {
        return allowedNext().contains(next);
    }
}