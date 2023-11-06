package bookstore.service;

import bookstore.dto.category.CategoryDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getCategoryById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);
}
