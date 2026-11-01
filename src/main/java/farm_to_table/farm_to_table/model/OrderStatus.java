package farm_to_table.farm_to_table.model;

import java.util.EnumSet;
import java.util.Set;

public enum OrderStatus {
    PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED;

    public Set<OrderStatus> allowedNext() {
        return switch (this) {
            case PENDING   -> EnumSet.of(CONFIRMED, CANCELLED);
            case CONFIRMED -> EnumSet.of(SHIPPING, CANCELLED);
            case SHIPPING  -> EnumSet.of(DELIVERED);
            case DELIVERED, CANCELLED -> EnumSet.noneOf(OrderStatus.class);
        };
    }

    public boolean canChangeTo(OrderStatus next) {
        return allowedNext().contains(next);
    }
}
