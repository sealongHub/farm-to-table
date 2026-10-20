package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Category.CategoryRequest;
import farm_to_table.farm_to_table.DTO.Category.CategoryResponse;
import farm_to_table.farm_to_table.Repository.CategoryRepository;
import farm_to_table.farm_to_table.model.CategoryEntity;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest r) {
        Long id = categoryRepository.insertCategory(r.getName(), r.getDescription(), r.getStatus());
        return getById(id);
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest r) {
        getById(id);
        categoryRepository.updateCategory(id, r.getName(), r.getDescription(), r.getStatus());
        return getById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getById(id);
        categoryRepository.deleteCategory(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        return categoryRepository.getCategoryById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return categoryRepository.getAllCategories().stream().map(this::toResponse).toList();
    }

    private CategoryResponse toResponse(CategoryEntity e) {
        return CategoryResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .status(e.getStatus())
                .build();
    }
}
