package farm_to_table.farm_to_table.model;

import java.util.EnumSet;
import java.util.Set;

public enum TermsStatus {
    DRAFT, ACTIVE, ARCHIVED;

    public Set<TermsStatus> allowedNext() {
        return switch (this) {
            case DRAFT    -> EnumSet.of(ACTIVE);
            case ACTIVE   -> EnumSet.of(ARCHIVED);
            case ARCHIVED -> EnumSet.noneOf(TermsStatus.class);
        };
    }

    public boolean canChangeTo(TermsStatus next) {
        return allowedNext().contains(next);
    }
}