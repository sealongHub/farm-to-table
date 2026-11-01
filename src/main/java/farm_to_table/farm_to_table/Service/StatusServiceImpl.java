package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Status.StatusRequestDto;
import farm_to_table.farm_to_table.DTO.Status.StatusResponseDto;
import farm_to_table.farm_to_table.DTO.Status.StatusUpdateDto;
import farm_to_table.farm_to_table.Repository.OrderRepository;
import farm_to_table.farm_to_table.Repository.StatusRepository;
import farm_to_table.farm_to_table.model.StatusEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusServiceImpl implements StatusService {

    private final StatusRepository repository;
    private final OrderRepository orderRepository;

    @Override
    public StatusResponseDto create(StatusRequestDto dto) {
        String code = normalize(dto.getCode());
        String type = normalize(dto.getType());

        if (repository.existsByCodeAndType(code, type)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Status " + code + " already exists for type " + type);
        }

        StatusEntity entity = new StatusEntity();
        entity.setCode(code);
        entity.setType(type);
        entity.setDescription(dto.getDescription());
        return toDto(repository.save(entity));
    }

    @Override
    public StatusResponseDto update(Long id, StatusUpdateDto dto) {
        StatusEntity entity = findOrThrow(id);
        entity.setDescription(dto.getDescription());
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        StatusEntity entity = findOrThrow(id);

        if (orderRepository.existsByStatusId(String.valueOf(id))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Status " + entity.getCode() + " is used by orders and cannot be deleted");
        }
        repository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public StatusResponseDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusResponseDto> getByType(String type) {
        return repository.findByTypeOrderByIdAsc(normalize(type))
                .stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusResponseDto> search(String keyword, String type, Pageable pageable) {
        String k = keyword == null ? "" : keyword.trim();
        String t = type == null ? "" : type.trim().toUpperCase();
        return repository.search(k, t, pageable).map(this::toDto);
    }

    // ---------- helpers ----------

    private StatusEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Status with id " + id + " not found"));
    }

    private String normalize(String value) {
        return value.trim().toUpperCase();
    }

    private StatusResponseDto toDto(StatusEntity e) {
        return new StatusResponseDto(e.getId(), e.getCode(), e.getDescription(), e.getType());
    }
}
