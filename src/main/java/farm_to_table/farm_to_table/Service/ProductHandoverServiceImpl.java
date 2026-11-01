package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.ProductHandover.ProductHandoverRequestDto;
import farm_to_table.farm_to_table.DTO.ProductHandover.ProductHandoverResponseDto;
import farm_to_table.farm_to_table.DTO.ProductHandover.ProductHandoverStatusDto;
import farm_to_table.farm_to_table.Repository.ProductHandoverRepository;
import farm_to_table.farm_to_table.Repository.StatusRepository;
import farm_to_table.farm_to_table.Repository.UserRepository;
import farm_to_table.farm_to_table.Repository.WareHouseRepository;
import farm_to_table.farm_to_table.model.HandoverParty;
import farm_to_table.farm_to_table.model.HandoverStatus;
import farm_to_table.farm_to_table.model.ProductHandoversEntity;
import farm_to_table.farm_to_table.model.StatusEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductHandoverServiceImpl implements ProductHandoverService {

    private static final String HANDOVER_TYPE = "HANDOVER";

    private final ProductHandoverRepository repository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final WareHouseRepository warehouseRepository;

    @Override
    public ProductHandoverResponseDto create(ProductHandoverRequestDto dto) {
        ProductHandoversEntity entity = new ProductHandoversEntity();
        applyDto(entity, dto);
        applyStatus(entity, resolveStatus(HandoverStatus.PENDING));
        return toDto(repository.save(entity));
    }

    @Override
    public ProductHandoverResponseDto update(Long id, ProductHandoverRequestDto dto) {
        ProductHandoversEntity entity = findOrThrow(id);

        if (currentStatus(entity) != HandoverStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only PENDING handovers can be edited (current: " + currentStatus(entity) + ")");
        }

        applyDto(entity, dto);
        return toDto(repository.save(entity));
    }

    @Override
    public ProductHandoverResponseDto updateStatus(Long id, ProductHandoverStatusDto dto) {
        ProductHandoversEntity entity = findOrThrow(id);

        HandoverStatus next;
        try {
            next = HandoverStatus.valueOf(dto.getStatus().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed: PENDING, ACCEPTED, REJECTED, CANCELLED");
        }

        HandoverStatus current = currentStatus(entity);
        if (!current.canChangeTo(next)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot change handover status from " + current + " to " + next
                            + ". Allowed: " + current.allowedNext());
        }

        if (next == HandoverStatus.ACCEPTED) {
            if (dto.getReceiveBy() == null || dto.getReceiveBy().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "receiveBy is required when status is ACCEPTED");
            }
            Long receiverId = parseId(dto.getReceiveBy(), "receiveBy");
            if (!userRepository.existsById(receiverId)) {
                throw new EntityNotFoundException("User with id " + receiverId + " not found");
            }
            entity.setReceiveBy(String.valueOf(receiverId));
        }

        applyStatus(entity, resolveStatus(next));
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        ProductHandoversEntity entity = findOrThrow(id);
        HandoverStatus current = currentStatus(entity);

        if (current != HandoverStatus.PENDING && current != HandoverStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only PENDING or CANCELLED handovers can be deleted (current: " + current + ")");
        }
        repository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductHandoverResponseDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductHandoverResponseDto> search(String fromType, String fromId,
                                                   String toType, String toId,
                                                   String status, Pageable pageable) {
        String ft = fromType == null ? "" : fromType.trim().toUpperCase();
        String fi = fromId == null ? "" : fromId.trim();
        String tt = toType == null ? "" : toType.trim().toUpperCase();
        String ti = toId == null ? "" : toId.trim();
        String s = status == null ? "" : status.trim().toUpperCase();

        String statusId = "";
        if (!s.isEmpty()) {
            var found = statusRepository.findByCodeAndType(s, HANDOVER_TYPE);
            if (found.isEmpty()) {
                return Page.empty(pageable);
            }
            statusId = String.valueOf(found.get().getId());
        }
        return repository.search(ft, fi, tt, ti, statusId, pageable).map(this::toDto);
    }

    // ---------- helpers ----------

    private ProductHandoversEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Handover with id " + id + " not found"));
    }

    private void applyDto(ProductHandoversEntity e, ProductHandoverRequestDto d) {
        HandoverParty fromType = parseParty(d.getFromType(), "fromType");
        HandoverParty toType = parseParty(d.getToType(), "toType");
        Long fromId = parseId(d.getFromId(), "fromId");
        Long toId = parseId(d.getToId(), "toId");

        if (fromType == toType && fromId.equals(toId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot hand over to the same party");
        }

        requireExists(fromType, fromId, "fromId");
        requireExists(toType, toId, "toId");

        e.setFromType(fromType.name());
        e.setFromId(String.valueOf(fromId));
        e.setToType(toType.name());
        e.setToId(String.valueOf(toId));
        e.setQty(d.getQty());
        e.setNote(d.getNote());
    }

    private HandoverParty parseParty(String value, String field) {
        try {
            return HandoverParty.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    field + " must be one of: " + Arrays.toString(HandoverParty.values()));
        }
    }

    // ត្រូវបន្ថែម case ពេលមាន type ថ្មីក្នុង HandoverParty
    private void requireExists(HandoverParty type, Long id, String field) {
        boolean exists = switch (type) {
            case FARMER, CHECKER -> userRepository.existsById(id);
            case WAREHOUSE -> warehouseRepository.existsById(id);
        };
        if (!exists) {
            throw new EntityNotFoundException(type + " with id " + id + " not found (" + field + ")");
        }
    }

    private Long parseId(String value, String field) {
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, field + " must be a number");
        }
    }

    private HandoverStatus currentStatus(ProductHandoversEntity entity) {
        StatusEntity s = statusRepository.findById(Long.parseLong(entity.getStatusId()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Status id " + entity.getStatusId() + " is missing in the status table"));
        return HandoverStatus.valueOf(s.getCode());
    }

    private StatusEntity resolveStatus(HandoverStatus status) {
        return statusRepository.findByCodeAndType(status.name(), HANDOVER_TYPE)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Status " + status + " (type " + HANDOVER_TYPE
                                + ") is missing in the status table"));
    }

    private void applyStatus(ProductHandoversEntity entity, StatusEntity status) {
        entity.setStatusId(String.valueOf(status.getId()));
    }

    private ProductHandoverResponseDto toDto(ProductHandoversEntity e) {
        String statusCode = null;
        try {
            statusCode = statusRepository.findById(Long.parseLong(e.getStatusId()))
                    .map(StatusEntity::getCode).orElse(null);
        } catch (NumberFormatException ignored) {
        }
        return new ProductHandoverResponseDto(
                e.getId(), e.getFromType(), e.getFromId(), e.getToType(), e.getToId(),
                e.getQty(), e.getStatusId(), statusCode, e.getReceiveBy(), e.getNote());
    }
}