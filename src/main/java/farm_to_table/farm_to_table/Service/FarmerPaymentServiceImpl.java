package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.FarmerPayment.FarmerPaymentRequestDto;
import farm_to_table.farm_to_table.DTO.FarmerPayment.FarmerPaymentResponseDto;
import farm_to_table.farm_to_table.DTO.FarmerPayment.FarmerPaymentStatusDto;
import farm_to_table.farm_to_table.DTO.FarmerPayment.FarmerPaymentUpdateDto;
import farm_to_table.farm_to_table.Repository.FarmerPaymentRepository;
import farm_to_table.farm_to_table.Repository.SettlementRepository;
import farm_to_table.farm_to_table.Repository.UserAccountRepository;
import farm_to_table.farm_to_table.Repository.UserRepository;
import farm_to_table.farm_to_table.model.FarmerPaymentEntity;
import farm_to_table.farm_to_table.model.FarmerPaymentStatus;
import farm_to_table.farm_to_table.model.SettlementEntity;
import farm_to_table.farm_to_table.model.SettlementStatus;
import farm_to_table.farm_to_table.model.UserAccountEntity;
import farm_to_table.farm_to_table.model.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional
public class FarmerPaymentServiceImpl implements FarmerPaymentService {

    // payment សកម្ម = កំពុងរង់ចាំ ឬបង់រួច
    private static final List<String> ACTIVE =
            List.of(FarmerPaymentStatus.PENDING.name(), FarmerPaymentStatus.PAID.name());

    private final FarmerPaymentRepository repository;
    private final UserAccountRepository accountRepository;
    private final UserRepository userRepository;
    private final SettlementRepository settlementRepository;

    @Override
    public FarmerPaymentResponseDto create(FarmerPaymentRequestDto dto) {
        Long accountId = parseId(dto.getAccountId(), "accountId");
        Long settlementId = parseId(dto.getSettlementId(), "settlementId");

        UserAccountEntity account = findAccountOrThrow(accountId);
        requirePayableAccount(account);

        SettlementEntity settlement = settlementRepository.findById(settlementId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Settlement with id " + settlementId + " not found"));

        if (!SettlementStatus.COMPLETED.name().equals(settlement.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Settlement must be COMPLETED (current: " + settlement.getStatus() + ")");
        }
        if (repository.existsBySettlementIdAndStatusIn(String.valueOf(settlementId), ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Settlement " + settlementId + " already has an active payment");
        }

        FarmerPaymentEntity entity = new FarmerPaymentEntity();
        entity.setAccountId(String.valueOf(accountId));
        entity.setSettlementId(String.valueOf(settlementId));
        entity.setAmount(settlement.getTotalAmount());
        entity.setCcy(settlement.getCcy());
        entity.setStatus(FarmerPaymentStatus.PENDING.name());
        entity.setTransactionRef(generateRef());
        return toDto(repository.save(entity));
    }

    @Override
    public FarmerPaymentResponseDto update(Long id, FarmerPaymentUpdateDto dto) {
        FarmerPaymentEntity entity = findOrThrow(id);

        if (currentStatus(entity) != FarmerPaymentStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only PENDING payments can be edited (current: " + entity.getStatus() + ")");
        }

        Long accountId = parseId(dto.getAccountId(), "accountId");
        requirePayableAccount(findAccountOrThrow(accountId));

        entity.setAccountId(String.valueOf(accountId));
        return toDto(repository.save(entity));
    }

    @Override
    public FarmerPaymentResponseDto updateStatus(Long id, FarmerPaymentStatusDto dto) {
        FarmerPaymentEntity entity = findOrThrow(id);

        FarmerPaymentStatus next;
        try {
            next = FarmerPaymentStatus.valueOf(dto.getStatus().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed: PENDING, PAID, FAILED");
        }

        FarmerPaymentStatus current = currentStatus(entity);
        if (!current.canChangeTo(next)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot change payment status from " + current + " to " + next
                            + ". Allowed: " + current.allowedNext());
        }

        // FAILED => PENDING: ត្រូវប្រាកដថាគ្មាន payment សកម្មផ្សេងលើ settlement នេះ
        if (next == FarmerPaymentStatus.PENDING
                && repository.existsBySettlementIdAndStatusInAndIdNot(
                entity.getSettlementId(), ACTIVE, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Settlement " + entity.getSettlementId() + " already has an active payment");
        }

        if (next == FarmerPaymentStatus.PAID) {
            // គណនីត្រូវនៅតែ verified ពេលបង់ពិត
            requirePayableAccount(findAccountOrThrow(parseId(entity.getAccountId(), "accountId")));
            entity.setPaidAt(new Date());
        }

        entity.setStatus(next.name());
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        FarmerPaymentEntity entity = findOrThrow(id);
        FarmerPaymentStatus current = currentStatus(entity);

        if (current != FarmerPaymentStatus.PENDING && current != FarmerPaymentStatus.FAILED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only PENDING or FAILED payments can be deleted (current: " + current + ")");
        }
        repository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public FarmerPaymentResponseDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public FarmerPaymentResponseDto getByRef(String transactionRef) {
        return repository.findByTransactionRef(transactionRef.trim())
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Payment " + transactionRef + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FarmerPaymentResponseDto> search(String accountId, String settlementId,
                                                 String status, String keyword, Pageable pageable) {
        String a = accountId == null ? "" : accountId.trim();
        String sid = settlementId == null ? "" : settlementId.trim();
        String s = status == null ? "" : status.trim().toUpperCase();
        String k = keyword == null ? "" : keyword.trim();
        return repository.search(a, sid, s, k, pageable).map(this::toDto);
    }

    // ---------- helpers ----------

    private FarmerPaymentEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Farmer payment with id " + id + " not found"));
    }

    private UserAccountEntity findAccountOrThrow(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Account with id " + accountId + " not found"));
    }

    // គណនីត្រូវ verified ហើយ user ម្ចាស់ត្រូវមាន
    private void requirePayableAccount(UserAccountEntity account) {
        if (!Boolean.TRUE.equals(account.getIsVerified())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Account " + account.getId() + " is not verified");
        }
        if (account.getUserId() == null || !userRepository.existsById(account.getUserId())) {
            throw new EntityNotFoundException(
                    "User with id " + account.getUserId() + " not found");
        }
    }

    private FarmerPaymentStatus currentStatus(FarmerPaymentEntity entity) {
        return FarmerPaymentStatus.valueOf(entity.getStatus());
    }

    private Long parseId(String value, String field) {
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, field + " must be a number");
        }
    }

    // ឧទាហរណ៍: FPAY-20261101-483921
    private String generateRef() {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String ref;
        do {
            ref = "FPAY-" + date + "-" + ThreadLocalRandom.current().nextInt(100000, 1000000);
        } while (repository.existsByTransactionRef(ref));
        return ref;
    }

    private FarmerPaymentResponseDto toDto(FarmerPaymentEntity e) {
        UserAccountEntity account = null;
        UserEntity user = null;
        try {
            account = accountRepository.findById(Long.parseLong(e.getAccountId().trim())).orElse(null);
            if (account != null && account.getUserId() != null) {
                user = userRepository.findById(account.getUserId()).orElse(null);
            }
        } catch (NumberFormatException ignored) {
        }

        return new FarmerPaymentResponseDto(
                e.getId(),
                e.getAccountId(),
                account == null ? null : account.getAccountName(),
                account == null ? null : account.getBankName(),
                account == null ? null : account.getAccountNumber(),
                account == null || account.getUserId() == null ? null : String.valueOf(account.getUserId()),
                user == null ? null : user.getFullName(),
                e.getSettlementId(), e.getAmount(), e.getCcy(),
                e.getStatus(), e.getTransactionRef(), e.getPaidAt());
    }
}
