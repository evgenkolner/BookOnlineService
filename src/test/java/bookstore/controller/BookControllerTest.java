package bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bookstore.dto.BookDto;
import bookstore.dto.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
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
class BookControllerTest {

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
    @Sql(scripts = "classpath:database/categories/add-category-to-categories-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/books/remove-all-books-from-books-table.sql",
            "classpath:database/categories/remove-category-from-categories-table.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create new book")
    void createBook_ValidBookRequestDto_ReturnedNewBook() throws Exception {

        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "Book","Author","11111", BigDecimal.valueOf(99.99),
                "description","picture", List.of(1L)
        );

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                post("/api/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        BookDto expected = new BookDto()
                .setTitle(requestDto.title())
                .setAuthor(requestDto.author())
                .setIsbn(requestDto.isbn())
                .setPrice(requestDto.price())
                .setDescription(requestDto.description())
                .setCoverImage(requestDto.coverImage())
                .setCategoryIds(requestDto.categoryIds());

        BookDto actual = objectMapper.readValue(result.getResponse()
                        .getContentAsString(), BookDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user")
    @Test
    @Sql(scripts = "classpath:database/books/add-three-books-to-books-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/remove-all-books-from-books-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all books")
    void getAll_BooksFromDb_ReturnAllBooks() throws Exception {
        List<BookDto> expected = new ArrayList<>();
        expected.add(new BookDto().setId(1L).setTitle("Title1").setAuthor("Author1")
                .setIsbn("isbn1").setPrice(BigDecimal.valueOf(1)).setDescription("Description1")
                .setCoverImage("image1").setCategoryIds(List.of()));
        expected.add(new BookDto().setId(2L).setTitle("Title2").setAuthor("Author2")
                .setIsbn("isbn2").setPrice(BigDecimal.valueOf(2)).setDescription("Description2")
                .setCoverImage("image2").setCategoryIds(List.of()));
        expected.add(new BookDto().setId(3L).setTitle("Title3").setAuthor("Author3")
                .setIsbn("isbn3").setPrice(BigDecimal.valueOf(3)).setDescription("Description3")
                .setCoverImage("image3").setCategoryIds(List.of()));

        MvcResult result = mockMvc.perform(
                get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(result.getResponse()
                        .getContentAsByteArray(), BookDto[].class);
        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());

    }

    @WithMockUser(username = "user")
    @Test
    @Sql(scripts = "classpath:database/books/add-one-book-to-books-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/remove-all-books-from-books-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get book by valid id")
    void getBookById_ValidId_ReturnedBook() throws Exception {
        Long id = 1L;

        BookDto expected = new BookDto()
                .setId(id).setTitle("Title1").setAuthor("Author1")
                .setIsbn("isbn1").setPrice(BigDecimal.valueOf(1)).setDescription("Description1")
                .setCoverImage("image1").setCategoryIds(List.of());

        MvcResult result = mockMvc.perform(
                        get("/api/books/" + id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        Assertions.assertEquals(expected, actual);
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete book with valid id")
    @Sql(scripts = "classpath:database/books/add-one-book-to-books-table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void delete_ValidId_IsOk() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(
                        delete("/api/books/" + id)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
