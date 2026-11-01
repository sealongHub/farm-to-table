package farm_to_table.farm_to_table.model;

import java.util.EnumSet;
import java.util.Set;

public enum AgreementStatus {
    AGREED, REVOKED;

    public Set<AgreementStatus> allowedNext() {
        return switch (this) {
            case AGREED  -> EnumSet.of(REVOKED);
            case REVOKED -> EnumSet.noneOf(AgreementStatus.class);
        };
    }

    public boolean canChangeTo(AgreementStatus next) {
        return allowedNext().contains(next);
    }
}