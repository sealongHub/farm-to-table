package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Customer.CustomerRequestDto;
import farm_to_table.farm_to_table.DTO.Customer.CustomerResponseDto;
import farm_to_table.farm_to_table.Repository.CustomerRepository;
import farm_to_table.farm_to_table.model.CustomerEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerResponseDto create(CustomerRequestDto dto) {
        String email = normalizeEmail(dto.getEmail());

        if (repository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password is required");
        }

        CustomerEntity entity = new CustomerEntity();
        applyDto(entity, dto, email);
        entity.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        return toDto(repository.save(entity));
    }

    @Override
    public CustomerResponseDto update(Long id, CustomerRequestDto dto) {
        CustomerEntity entity = findOrThrow(id);
        String email = normalizeEmail(dto.getEmail());

        if (repository.existsByEmailAndIdNot(email, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        applyDto(entity, dto, email);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            entity.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponseDto> search(String keyword, String status, Pageable pageable) {
        String k = keyword == null ? "" : keyword.trim();
        String s = status == null ? "" : status.trim();
        return repository.search(k, s, pageable).map(this::toDto);
    }

    // ---------- helpers ----------

    private CustomerEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Customer with id " + id + " not found"));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private void applyDto(CustomerEntity e, CustomerRequestDto d, String email) {
        e.setFullNameKh(d.getFullNameKh());
        e.setFullNameEn(d.getFullNameEn());
        e.setEmail(email);
        e.setPhoneNumber(d.getPhoneNumber());
        e.setImage(d.getImage());

        if (d.getStatus() != null && !d.getStatus().isBlank()) {
            e.setStatus(d.getStatus());
        } else if (e.getStatus() == null) {
            e.setStatus("ACTIVE");
        }
    }

    private CustomerResponseDto toDto(CustomerEntity e) {
        return new CustomerResponseDto(
                e.getId(), e.getFullNameKh(), e.getFullNameEn(), e.getEmail(),
                e.getStatus(), e.getPhoneNumber(), e.getImage());
    }
}