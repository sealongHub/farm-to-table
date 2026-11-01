package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.DTO.OrderStatusHistory.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderStatusHistoryProcedureRepository {

    private final JdbcTemplate jdbcTemplate;

    private final BeanPropertyRowMapper<OrderStatusHistoryResponseDto> mapper =
            new BeanPropertyRowMapper<>(OrderStatusHistoryResponseDto.class);

    private Timestamp toTimestamp(OrderStatusHistoryRequestDto d) {
        return d.getChangeAt() == null ? null : Timestamp.valueOf(d.getChangeAt());
    }

    public Long insert(OrderStatusHistoryRequestDto d) {
        return jdbcTemplate.queryForObject(
                "CALL sp_insert_order_status_history(?, ?, ?, ?, ?, CAST(NULL AS BIGINT))",
                Long.class,
                d.getOrderId(), d.getOrderStatusId(), d.getNewStatusId(),
                d.getRemark(), toTimestamp(d));
    }

    public void update(Long id, OrderStatusHistoryRequestDto d) {
        jdbcTemplate.update(
                "CALL sp_update_order_status_history(?, ?, ?, ?, ?, ?)",
                id, d.getOrderId(), d.getOrderStatusId(), d.getNewStatusId(),
                d.getRemark(), toTimestamp(d));
    }

    public void delete(Long id) {
        jdbcTemplate.update("CALL sp_delete_order_status_history(?)", id);
    }

    public Optional<OrderStatusHistoryResponseDto> findById(Long id) {
        List<OrderStatusHistoryResponseDto> list = jdbcTemplate.query(
                "SELECT * FROM fn_get_order_status_history_by_id(?)", mapper, id);
        return list.stream().findFirst();
    }

    public List<OrderStatusHistoryResponseDto> findAll(String orderId, int limit, int offset) {
        return jdbcTemplate.query(
                "SELECT * FROM fn_list_order_status_history(?, ?, ?)",
                mapper, orderId, limit, offset);
    }
}
