package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.DTO.FeeDelivery.FeeDeliveryRequestDto;
import farm_to_table.farm_to_table.DTO.FeeDelivery.FeeDeliveryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FeeDeliveryProcedureRepository {
    private final JdbcTemplate jdbc;
    private final BeanPropertyRowMapper<FeeDeliveryResponseDto> mapper =
            new BeanPropertyRowMapper<>(FeeDeliveryResponseDto.class);

    public Long insert(FeeDeliveryRequestDto d) {
        return jdbc.queryForObject(
                "CALL sp_insert_fee_delivery(?, ?, ?, CAST(NULL AS BIGINT))",
                Long.class, d.getCode(), d.getAmount(), d.getDescription());
    }
    public void update(Long id, FeeDeliveryRequestDto d) {
        jdbc.update("CALL sp_update_fee_delivery(?, ?, ?, ?)",
                id, d.getCode(), d.getAmount(), d.getDescription());
    }
    public void delete(Long id) {
        jdbc.update("CALL sp_delete_fee_delivery(?)", id);
    }
    public Optional<FeeDeliveryResponseDto> findById(Long id) {
        return jdbc.query("SELECT * FROM fn_get_fee_delivery_by_id(?)", mapper, id)
                .stream().findFirst();
    }
    public List<FeeDeliveryResponseDto> findAll(String keyword, int limit, int offset) {
        return jdbc.query("SELECT * FROM fn_list_fee_delivery(?, ?, ?)",
                mapper, keyword, limit, offset);
    }
}
