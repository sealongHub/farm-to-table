package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.WareHouse.WareHouseRequestDto;
import farm_to_table.farm_to_table.DTO.WareHouse.WareHouseResponseDto;
import farm_to_table.farm_to_table.Repository.WareHouseRepository;
import farm_to_table.farm_to_table.model.WareHouseEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WareHouseServiceImpl implements WareHouseService {

    private final WareHouseRepository repository;

    @Override
    public WareHouseResponseDto create(WareHouseRequestDto dto) {
        String name = dto.getName().trim();

        if (repository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Warehouse " + name + " already exists");
        }

        WareHouseEntity entity = new WareHouseEntity();
        applyDto(entity, dto, name);
        return toDto(repository.save(entity));
    }

    @Override
    public WareHouseResponseDto update(Long id, WareHouseRequestDto dto) {
        WareHouseEntity entity = findOrThrow(id);
        String name = dto.getName().trim();

        if (repository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Warehouse " + name + " already exists");
        }

        applyDto(entity, dto, name);
        return toDto(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public WareHouseResponseDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WareHouseResponseDto> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WareHouseResponseDto> search(String keyword, Pageable pageable) {
        String k = keyword == null ? "" : keyword.trim();
        return repository.search(k, pageable).map(this::toDto);
    }

    // ---------- helpers ----------

    private WareHouseEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Warehouse with id " + id + " not found"));
    }

    private void applyDto(WareHouseEntity e, WareHouseRequestDto d, String name) {
        e.setName(name);
        e.setDescription(d.getDescription());
        e.setAddress(d.getAddress().trim());
        e.setCapacity(d.getCapacity());
        e.setCapacityUnit(d.getCapacityUnit());
    }

    private WareHouseResponseDto toDto(WareHouseEntity e) {
        return new WareHouseResponseDto(
                e.getId(), e.getName(), e.getDescription(), e.getAddress(),
                e.getCapacity(), e.getCapacityUnit());
    }
}
