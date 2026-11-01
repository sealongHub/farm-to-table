package farm_to_table.farm_to_table.Repository;

import farm_to_table.farm_to_table.DTO.DeliveryMethod.DeliveryMethodRequestDto;
import farm_to_table.farm_to_table.DTO.DeliveryMethod.DeliveryMethodResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DeliveryMethodProcedureRepository {
    private final JdbcTemplate jdbc;
    private final BeanPropertyRowMapper<DeliveryMethodResponseDto> mapper =
            new BeanPropertyRowMapper<>(DeliveryMethodResponseDto.class);

    public Long insert(DeliveryMethodRequestDto d) {
        return jdbc.queryForObject(
                "CALL sp_insert_delivery_method(?, ?, ?, CAST(NULL AS BIGINT))",
                Long.class, d.getCode(), d.getTitle(), d.getDescription());
    }
    public void update(Long id, DeliveryMethodRequestDto d) {
        jdbc.update("CALL sp_update_delivery_method(?, ?, ?, ?)",
                id, d.getCode(), d.getTitle(), d.getDescription());
    }
    public void delete(Long id) {
        jdbc.update("CALL sp_delete_delivery_method(?)", id);
    }
    public Optional<DeliveryMethodResponseDto> findById(Long id) {
        return jdbc.query("SELECT * FROM fn_get_delivery_method_by_id(?)", mapper, id)
                .stream().findFirst();
    }
    public List<DeliveryMethodResponseDto> findAll(String keyword, int limit, int offset) {
        return jdbc.query("SELECT * FROM fn_list_delivery_method(?, ?, ?)",
                mapper, keyword, limit, offset);
    }
}
