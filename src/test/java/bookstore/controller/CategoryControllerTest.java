package bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bookstore.dto.category.CategoryDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {

    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "classpath:database/categories/remove-category-from-categories-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Create new category")
    void createCategory_ValidRequest_Created() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                "name", "description");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto expected = new CategoryDto(1L, requestDto.name(),
                requestDto.description());

        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.id());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user")
    @Test
    @Sql(scripts = {"classpath:database/categories/remove-category-from-categories-table.sql",
            "classpath:database/categories/add-three-categories-to-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-category-from-categories-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all categories")
    void getAll_CategoriesFromDb_ReturnAllCategories() throws Exception {
        List<CategoryDto> expected = new ArrayList<>();
        expected.add(new CategoryDto(1L, "category1", "description1"));
        expected.add(new CategoryDto(2L, "category2", "description2"));
        expected.add(new CategoryDto(3L, "category3", "description3"));

        MvcResult result = mockMvc.perform(
                        get("/api/categories")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        CategoryDto[] actual = objectMapper.readValue(result.getResponse()
                .getContentAsByteArray(), CategoryDto[].class);
        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());

    }

    @WithMockUser(username = "user")
    @Test
    @Sql(scripts = {"classpath:database/categories/remove-category-from-categories-table.sql",
            "classpath:database/categories/add-category-to-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Get category by id")
    void getCategoryById_ValidId_ReturnedCategory() throws Exception {
        Long id = 1L;

        CategoryDto expected = new CategoryDto(id, "category1",
                "description1");

        MvcResult result = mockMvc.perform(
                        get("/api/categories/" + id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        Assertions.assertEquals(expected, actual);
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete category with valid id")
    @Sql(scripts = "classpath:database/categories/add-category-to-categories-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteCategory_ValidId_IsOk() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(
                        delete("/api/categories/" + id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
