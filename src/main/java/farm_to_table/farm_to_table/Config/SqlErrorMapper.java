package farm_to_table.farm_to_table.Config;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.function.Supplier;

@Component
public class SqlErrorMapper {

    public <T> T run(Supplier<T> action) {
        try {
            return action.get();
        } catch (DataAccessException ex) {
            throw map(ex);
        }
    }

    public void runVoid(Runnable action) {
        run(() -> { action.run(); return null; });
    }

    private RuntimeException map(DataAccessException ex) {
        Throwable root = ex.getMostSpecificCause();
        if (root instanceof SQLException sql && sql.getSQLState() != null) {
            String message = clean(sql.getMessage());
            switch (sql.getSQLState()) {
                case "PT404": return new EntityNotFoundException(message);
                case "PT409": return new ResponseStatusException(HttpStatus.CONFLICT, message);
                case "PT400": return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
                default: break;
            }
        }
        return ex;   // កំហុសផ្សេង => 500
    }

    // "ERROR: text\n  Where: ..." => "text"
    private String clean(String raw) {
        if (raw == null) return "Database error";
        String msg = raw.startsWith("ERROR: ") ? raw.substring(7) : raw;
        int nl = msg.indexOf('\n');
        return nl > 0 ? msg.substring(0, nl) : msg;
    }
}
