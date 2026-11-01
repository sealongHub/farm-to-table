package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.DTO.OrderDetail.OrderDetailRequestDto;
import farm_to_table.farm_to_table.DTO.OrderDetail.OrderDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDetailProcedureRepository {

    private final JdbcTemplate jdbcTemplate;

    private final BeanPropertyRowMapper<OrderDetailResponseDto> mapper =
            new BeanPropertyRowMapper<>(OrderDetailResponseDto.class);

    public Long insert(OrderDetailRequestDto d) {
        return jdbcTemplate.queryForObject(
                "CALL sp_insert_order_detail(?, ?, ?, ?, ?, ?, CAST(NULL AS BIGINT))",
                Long.class,
                d.getOrderId(), d.getProductId(), d.getQty(), d.getUnitPrice(),
                d.getOtherMethodId(), d.getPaymentMethodId());
    }

    public void update(Long id, OrderDetailRequestDto d) {
        jdbcTemplate.update(
                "CALL sp_update_order_detail(?, ?, ?, ?, ?, ?, ?)",
                id, d.getOrderId(), d.getProductId(), d.getQty(), d.getUnitPrice(),
                d.getOtherMethodId(), d.getPaymentMethodId());
    }

    public void delete(Long id) {
        jdbcTemplate.update("CALL sp_delete_order_detail(?)", id);
    }

    public Optional<OrderDetailResponseDto> findById(Long id) {
        List<OrderDetailResponseDto> list = jdbcTemplate.query(
                "SELECT * FROM fn_get_order_detail_by_id(?)", mapper, id);
        return list.stream().findFirst();
    }

    public List<OrderDetailResponseDto> findAll(String orderId, int limit, int offset) {
        return jdbcTemplate.query(
                "SELECT * FROM fn_list_order_detail(?, ?, ?)", mapper, orderId, limit, offset);
    }
}