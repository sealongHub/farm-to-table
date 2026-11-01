package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Settlement.SettlementRequestDto;
import farm_to_table.farm_to_table.DTO.Settlement.SettlementResponseDto;
import farm_to_table.farm_to_table.DTO.Settlement.SettlementStatusDto;
import farm_to_table.farm_to_table.DTO.Settlement.SettlementUpdateDto;
import farm_to_table.farm_to_table.Repository.SettlementRepository;
import farm_to_table.farm_to_table.model.SettlementEntity;
import farm_to_table.farm_to_table.model.SettlementStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class SettlementServiceImpl implements SettlementService {

    private static final ZoneId ZONE = ZoneId.systemDefault();

    private final SettlementRepository repository;

    @Override
    public SettlementResponseDto create(SettlementRequestDto dto) {
        String ccy = normalizeCcy(dto.getCcy());
        Date date = toDate(dto.getSettlementDate());

        if (repository.existsBySettlementDateAndCcy(date, ccy)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Settlement for " + dto.getSettlementDate() + " " + ccy + " already exists");
        }

        SettlementEntity entity = new SettlementEntity();
        entity.setSettlementDate(date);
        entity.setCcy(ccy);
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setStatus(SettlementStatus.PENDING.name());
        return toDto(repository.save(entity));
    }

    @Override
    public SettlementResponseDto update(Long id, SettlementUpdateDto dto) {
        SettlementEntity entity = findOrThrow(id);

        if (currentStatus(entity) != SettlementStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only PENDING settlements can be edited (current: " + entity.getStatus() + ")");
        }

        String ccy = normalizeCcy(dto.getCcy());
        Date date = toDate(dto.getSettlementDate());

        if (repository.existsBySettlementDateAndCcyAndIdNot(date, ccy, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Settlement for " + dto.getSettlementDate() + " " + ccy + " already exists");
        }

        entity.setSettlementDate(date);
        entity.setCcy(ccy);
        entity.setTotalAmount(dto.getTotalAmount());
        return toDto(repository.save(entity));
    }

    @Override
    public SettlementResponseDto updateStatus(Long id, SettlementStatusDto dto) {
        SettlementEntity entity = findOrThrow(id);

        SettlementStatus next;
        try {
            next = SettlementStatus.valueOf(dto.getStatus().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed: PENDING, PROCESSING, COMPLETED, FAILED");
        }

        SettlementStatus current = currentStatus(entity);
        if (!current.canChangeTo(next)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot change settlement status from " + current + " to " + next
                            + ". Allowed: " + current.allowedNext());
        }

        entity.setStatus(next.name());
        if (next == SettlementStatus.COMPLETED) {
            entity.setProcessAt(new Date());
        }
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        SettlementEntity entity = findOrThrow(id);
        SettlementStatus current = currentStatus(entity);

        if (current != SettlementStatus.PENDING && current != SettlementStatus.FAILED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only PENDING or FAILED settlements can be deleted (current: " + current + ")");
        }
        repository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public SettlementResponseDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SettlementResponseDto> search(String ccy, String status,
                                              LocalDate from, LocalDate to, Pageable pageable) {
        String c = ccy == null ? "" : ccy.trim().toUpperCase();
        String s = status == null ? "" : status.trim().toUpperCase();
        Date fromDate = toDate(from != null ? from : LocalDate.of(1900, 1, 1));
        Date toDate = toDate(to != null ? to : LocalDate.of(2999, 12, 31));
        return repository.search(c, s, fromDate, toDate, pageable).map(this::toDto);
    }

    // ---------- helpers ----------

    private SettlementEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Settlement with id " + id + " not found"));
    }

    private SettlementStatus currentStatus(SettlementEntity entity) {
        return SettlementStatus.valueOf(entity.getStatus());
    }

    private String normalizeCcy(String ccy) {
        return ccy.trim().toUpperCase();
    }

    private Date toDate(LocalDate d) {
        return Date.from(d.atStartOfDay(ZONE).toInstant());
    }

    private LocalDate toLocalDate(Date d) {
        return d == null ? null : new Date(d.getTime()).toInstant().atZone(ZONE).toLocalDate();
    }

    private SettlementResponseDto toDto(SettlementEntity e) {
        return new SettlementResponseDto(
                e.getId(), toLocalDate(e.getSettlementDate()), e.getCcy(),
                e.getTotalAmount(), e.getStatus(), e.getProcessAt());
    }
}