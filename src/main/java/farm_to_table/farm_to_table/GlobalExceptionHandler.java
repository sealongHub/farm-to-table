package farm_to_table.farm_to_table;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentials(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Email ឬ Password មិនត្រឹមត្រូវ");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> notFound(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    // ===== UserDetail =====

    // រកមិនឃើញ User Detail → 404
    @ExceptionHandler(UserDetailNotFoundException.class)
    public ResponseEntity<String> userDetailNotFound(UserDetailNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    // JSON ខុសទម្រង់ ឬ date ខុស (ត្រូវជា "yyyy-MM-dd") → 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> invalidBody(HttpMessageNotReadableException e) {
        log.warn("Invalid request body: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("ទិន្នន័យដែលផ្ញើមិនត្រឹមត្រូវ (ពិនិត្យ JSON និង dateOfBirth ជា yyyy-MM-dd)");
    }

    // ទិន្នន័យស្ទួន ឬខ្វះ field ដែលចាំបាច់ក្នុង DB → 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dataIntegrity(DataIntegrityViolationException e) {
        log.error("Data integrity violation", e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("ទិន្នន័យស្ទួន ឬមិនគ្រប់គ្រាន់ (ឧ. cardId ស្ទួន)");
    }

    // ចុងក្រោយ: error ផ្សេងៗ → 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> other(Exception e) {
        log.error("Unexpected error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}