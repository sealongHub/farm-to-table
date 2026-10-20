package farm_to_table.farm_to_table.Service;

import farm_to_table.farm_to_table.DTO.Category.CategoryRequest;
import farm_to_table.farm_to_table.DTO.Category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    CategoryResponse update(Long id, CategoryRequest request);
    void delete(Long id);
    CategoryResponse getById(Long id);
    List<CategoryResponse> getAll();
}