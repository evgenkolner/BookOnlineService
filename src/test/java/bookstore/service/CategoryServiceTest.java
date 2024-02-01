package bookstore.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bookstore.dto.category.CategoryDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import bookstore.mapper.CategoryMapper;
import bookstore.model.Category;
import bookstore.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Find all categories")
    void findAll() {
        Long id = 1L;
        Category category = defaultCategory(id);

        CategoryDto categoryDto = new CategoryDto(category.getId(),
                category.getName(), category.getDescription());

        Pageable pageable = PageRequest.of(0, 5);
        List<Category> categories = List.of(category);

        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        List<CategoryDto> categoryDtos = categoryService.findAll(pageable);

        assertThat(categoryDtos.get(0)).isEqualTo(categoryDto);
    }

    @Test
    @DisplayName("Find category by id")
    void getCategoryById() {
        Long id = 1L;
        Category category = defaultCategory(id);

        CategoryDto categoryDto = new CategoryDto(category.getId(),
                category.getName(), category.getDescription());

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.getCategoryById(id);

        assertThat(actual).isEqualTo(categoryDto);
    }

    @Test
    @DisplayName("Create new category")
    void save() {
        Long id = 1L;
        Category category = defaultCategory(id);

        CategoryDto categoryDto = new CategoryDto(category.getId(),
                category.getName(), category.getDescription());

        CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto(
                "Category", "Description");

        when(categoryMapper.toCategory(createCategoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.save(createCategoryRequestDto);

        assertThat(actual).isEqualTo(categoryDto);
    }

    @Test
    @DisplayName("Delete category by id")
    void deleteById() {
        Long id = 1L;
        categoryService.deleteById(id);
        verify(categoryRepository, times(1)).deleteById(id);
    }

    Category defaultCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        category.setName("Category");
        category.setDescription("Description");
        return category;
    }
}
