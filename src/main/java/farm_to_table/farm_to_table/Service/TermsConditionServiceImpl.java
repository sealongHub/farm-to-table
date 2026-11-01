package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Terms.TermsConditionRequestDto;
import farm_to_table.farm_to_table.DTO.Terms.TermsConditionResponseDto;
import farm_to_table.farm_to_table.DTO.Terms.TermsConditionStatusDto;
import farm_to_table.farm_to_table.DTO.Terms.TermsConditionUpdateDto;
import farm_to_table.farm_to_table.Repository.TermsConditionRepository;
import farm_to_table.farm_to_table.model.TermsConditionEntity;
import farm_to_table.farm_to_table.model.TermsStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class TermsConditionServiceImpl implements TermsConditionService {

    private final TermsConditionRepository repository;

    @Override
    public TermsConditionResponseDto create(TermsConditionRequestDto dto) {
        String type = normalizeType(dto.getType());
        String version = dto.getVersion().trim();

        if (repository.existsByTypeAndVersion(type, version)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Version " + version + " already exists for type " + type);
        }

        TermsConditionEntity entity = new TermsConditionEntity();
        entity.setType(type);
        entity.setVersion(version);
        entity.setContent(dto.getContent());
        entity.setStatus(TermsStatus.DRAFT.name());
        return toDto(repository.save(entity));
    }

    @Override
    public TermsConditionResponseDto update(Long id, TermsConditionUpdateDto dto) {
        TermsConditionEntity entity = findOrThrow(id);

        if (currentStatus(entity) != TermsStatus.DRAFT) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only DRAFT terms can be edited (current: " + entity.getStatus() + ")");
        }

        String version = dto.getVersion().trim();
        if (repository.existsByTypeAndVersionAndIdNot(entity.getType(), version, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Version " + version + " already exists for type " + entity.getType());
        }

        entity.setVersion(version);
        entity.setContent(dto.getContent());
        return toDto(repository.save(entity));
    }

    @Override
    public TermsConditionResponseDto updateStatus(Long id, TermsConditionStatusDto dto) {
        TermsConditionEntity entity = findOrThrow(id);

        TermsStatus next;
        try {
            next = TermsStatus.valueOf(dto.getStatus().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed: DRAFT, ACTIVE, ARCHIVED");
        }

        TermsStatus current = currentStatus(entity);
        if (!current.canChangeTo(next)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot change status from " + current + " to " + next
                            + ". Allowed: " + current.allowedNext());
        }

        // type មួយមាន ACTIVE បានតែមួយ => archive កំណែ ACTIVE ចាស់
        if (next == TermsStatus.ACTIVE) {
            repository.findByTypeAndStatus(entity.getType(), TermsStatus.ACTIVE.name())
                    .forEach(old -> {
                        old.setStatus(TermsStatus.ARCHIVED.name());
                        repository.save(old);
                    });
        }

        entity.setStatus(next.name());
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        TermsConditionEntity entity = findOrThrow(id);

        if (currentStatus(entity) != TermsStatus.DRAFT) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only DRAFT terms can be deleted (current: " + entity.getStatus() + ")");
        }
        repository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public TermsConditionResponseDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public TermsConditionResponseDto getActive(String type) {
        String t = normalizeType(type);
        return repository.findByTypeAndStatus(t, TermsStatus.ACTIVE.name())
                .stream().findFirst()
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No ACTIVE terms found for type " + t));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TermsConditionResponseDto> search(String type, String status,
                                                  String keyword, Pageable pageable) {
        String t = type == null ? "" : type.trim().toUpperCase();
        String s = status == null ? "" : status.trim().toUpperCase();
        String k = keyword == null ? "" : keyword.trim();
        return repository.search(t, s, k, pageable).map(this::toDto);
    }

    // ---------- helpers ----------

    private TermsConditionEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Terms with id " + id + " not found"));
    }

    private TermsStatus currentStatus(TermsConditionEntity entity) {
        return TermsStatus.valueOf(entity.getStatus());
    }

    private String normalizeType(String type) {
        return type.trim().toUpperCase();
    }

    private TermsConditionResponseDto toDto(TermsConditionEntity e) {
        return new TermsConditionResponseDto(
                e.getId(), e.getType(), e.getVersion(), e.getContent(), e.getStatus());
    }
}
