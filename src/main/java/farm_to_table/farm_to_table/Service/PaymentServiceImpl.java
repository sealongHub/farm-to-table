package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.Config.SqlErrorMapper;
import farm_to_table.farm_to_table.DTO.payment.PaymentRequestDto;
import farm_to_table.farm_to_table.DTO.payment.PaymentResponseDto;
import farm_to_table.farm_to_table.DTO.payment.PaymentStatusDto;
import farm_to_table.farm_to_table.DTO.payment.PaymentUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    // args: id, ref, orderId, customerId, status, keyword, limit, offset
    private static final String SELECT =
            "SELECT * FROM fn_get_payments(CAST(? AS BIGINT), CAST(? AS VARCHAR), "
                    + "CAST(? AS VARCHAR), CAST(? AS VARCHAR), CAST(? AS VARCHAR), CAST(? AS VARCHAR), ?, ?)";

    private final JdbcTemplate jdbc;
    private final SqlErrorMapper errors;

    @Override
    public PaymentResponseDto create(PaymentRequestDto dto) {
        Long id = errors.run(() -> jdbc.queryForObject(
                "CALL sp_insert_payment(?, ?, CAST(NULL AS BIGINT))",
                Long.class, dto.getOrderId().trim(), dto.getPaymentMethodId().trim()));
        return getById(id);
    }

    @Override
    public PaymentResponseDto update(Long id, PaymentUpdateDto dto) {
        errors.runVoid(() -> jdbc.update("CALL sp_update_payment(?, ?)",
                id, dto.getPaymentMethodId().trim()));
        return getById(id);
    }

    @Override
    public PaymentResponseDto updateStatus(Long id, PaymentStatusDto dto) {
        errors.runVoid(() -> jdbc.update("CALL sp_update_payment_status(?, ?)",
                id, dto.getStatus()));
        return getById(id);
    }

    @Override
    public void delete(Long id) {
        errors.runVoid(() -> jdbc.update("CALL sp_delete_payment(?)", id));
    }

    @Override
    public PaymentResponseDto getById(Long id) {
        List<PaymentResponseDto> rows = errors.run(() ->
                jdbc.query(SELECT, (rs, i) -> map(rs), id, null, null, null, null, null, 1, 0));
        if (rows.isEmpty()) {
            throw new EntityNotFoundException("Payment with id " + id + " not found");
        }
        return rows.get(0);
    }

    @Override
    public PaymentResponseDto getByRef(String ref) {
        List<PaymentResponseDto> rows = errors.run(() ->
                jdbc.query(SELECT, (rs, i) -> map(rs), null, ref, null, null, null, null, 1, 0));
        if (rows.isEmpty()) {
            throw new EntityNotFoundException("Payment " + ref + " not found");
        }
        return rows.get(0);
    }

    @Override
    public Page<PaymentResponseDto> search(String orderId, String customerId,
                                           String status, String keyword, Pageable pageable) {
        long[] total = {0};
        List<PaymentResponseDto> rows = errors.run(() -> jdbc.query(SELECT, (rs, i) -> {
                    total[0] = rs.getLong("total");
                    return map(rs);
                }, null, null, trim(orderId), trim(customerId), trim(status), trim(keyword),
                pageable.getPageSize(), pageable.getOffset()));
        return new PageImpl<>(rows, pageable, total[0]);
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private PaymentResponseDto map(ResultSet rs) throws SQLException {
        return new PaymentResponseDto(
                rs.getLong("id"), rs.getString("order_id"), rs.getString("customer_id"),
                rs.getString("ref"), rs.getDouble("amount"),
                rs.getString("payment_method_id"), rs.getString("payment_method"),
                rs.getString("payment_status_id"), rs.getString("payment_status"),
                rs.getTimestamp("paid_at"));
    }
}
