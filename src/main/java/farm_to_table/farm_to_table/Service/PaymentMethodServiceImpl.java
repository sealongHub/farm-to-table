package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.Config.SqlErrorMapper;
import farm_to_table.farm_to_table.DTO.PaymentMethod.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private static final String SELECT =
            "SELECT * FROM fn_get_payment_methods(CAST(? AS BIGINT), CAST(? AS VARCHAR), ?, ?)";

    private final JdbcTemplate jdbc;
    private final SqlErrorMapper errors;

    @Override
    public PaymentMethodResponseDto create(PaymentMethodRequestDto dto) {
        Long id = errors.run(() -> jdbc.queryForObject(
                "CALL sp_insert_payment_method(?, ?, ?, CAST(NULL AS BIGINT))",
                Long.class, dto.getCode(), dto.getTitle(), dto.getDescription()));
        return getById(id);
    }

    @Override
    public PaymentMethodResponseDto update(Long id, PaymentMethodUpdateDto dto) {
        errors.runVoid(() -> jdbc.update("CALL sp_update_payment_method(?, ?, ?)",
                id, dto.getTitle(), dto.getDescription()));
        return getById(id);
    }

    @Override
    public void delete(Long id) {
        errors.runVoid(() -> jdbc.update("CALL sp_delete_payment_method(?)", id));
    }

    @Override
    public PaymentMethodResponseDto getById(Long id) {
        List<PaymentMethodResponseDto> rows = errors.run(() ->
                jdbc.query(SELECT, (rs, i) -> map(rs), id, null, 1, 0));
        if (rows.isEmpty()) {
            throw new EntityNotFoundException("Payment method with id " + id + " not found");
        }
        return rows.get(0);
    }

    @Override
    public List<PaymentMethodResponseDto> getAll() {
        return errors.run(() -> jdbc.query(SELECT, (rs, i) -> map(rs), null, null, 1000, 0));
    }

    @Override
    public Page<PaymentMethodResponseDto> search(String keyword, Pageable pageable) {
        String k = keyword == null ? "" : keyword.trim();
        long[] total = {0};
        List<PaymentMethodResponseDto> rows = errors.run(() -> jdbc.query(SELECT, (rs, i) -> {
            total[0] = rs.getLong("total");
            return map(rs);
        }, null, k, pageable.getPageSize(), pageable.getOffset()));
        return new PageImpl<>(rows, pageable, total[0]);
    }

    private PaymentMethodResponseDto map(java.sql.ResultSet rs) throws java.sql.SQLException {
        return new PaymentMethodResponseDto(
                rs.getLong("id"), rs.getString("code"),
                rs.getString("title"), rs.getString("description"));
    }
}