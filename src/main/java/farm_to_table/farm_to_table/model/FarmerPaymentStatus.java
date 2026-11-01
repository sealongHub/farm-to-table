package farm_to_table.farm_to_table.model;

import java.util.EnumSet;
import java.util.Set;

public enum FarmerPaymentStatus {
    PENDING, PAID, FAILED;

    public Set<FarmerPaymentStatus> allowedNext() {
        return switch (this) {
            case PENDING -> EnumSet.of(PAID, FAILED);
            case FAILED  -> EnumSet.of(PENDING);
            case PAID    -> EnumSet.noneOf(FarmerPaymentStatus.class);
        };
    }

    public boolean canChangeTo(FarmerPaymentStatus next) {
        return allowedNext().contains(next);
    }
}