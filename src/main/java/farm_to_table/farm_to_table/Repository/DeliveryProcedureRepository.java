package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.DTO.Delivery.DeliveryRequestDto;
import farm_to_table.farm_to_table.DTO.Delivery.DeliveryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DeliveryProcedureRepository {
    private final JdbcTemplate jdbc;
    private final BeanPropertyRowMapper<DeliveryResponseDto> mapper =
            new BeanPropertyRowMapper<>(DeliveryResponseDto.class);

    private Timestamp ts(LocalDateTime t) { return t == null ? null : Timestamp.valueOf(t); }

    public Long insert(DeliveryRequestDto d) {
        return jdbc.queryForObject(
                "CALL sp_insert_delivery(?, ?, ?, ?, CAST(? AS TIMESTAMP), CAST(? AS TIMESTAMP), ?, ?, CAST(NULL AS BIGINT))",
                Long.class, d.getOrderId(), d.getAssignTo(), d.getDeliveryAddress(),
                d.getDeliveryFeeId(), ts(d.getScheduleAt()), ts(d.getDeliveryAt()),
                d.getStatusId(), d.getRemarks());
    }
    public void update(Long id, DeliveryRequestDto d) {
        jdbc.update(
                "CALL sp_update_delivery(?, ?, ?, ?, ?, CAST(? AS TIMESTAMP), CAST(? AS TIMESTAMP), ?, ?)",
                id, d.getOrderId(), d.getAssignTo(), d.getDeliveryAddress(),
                d.getDeliveryFeeId(), ts(d.getScheduleAt()), ts(d.getDeliveryAt()),
                d.getStatusId(), d.getRemarks());
    }
    public void delete(Long id) {
        jdbc.update("CALL sp_delete_delivery(?)", id);
    }
    public Optional<DeliveryResponseDto> findById(Long id) {
        return jdbc.query("SELECT * FROM fn_get_delivery_by_id(?)", mapper, id)
                .stream().findFirst();
    }
    public List<DeliveryResponseDto> findAll(String orderId, String statusId, int limit, int offset) {
        return jdbc.query("SELECT * FROM fn_list_delivery(?, ?, ?, ?)",
                mapper, orderId, statusId, limit, offset);
    }
}
