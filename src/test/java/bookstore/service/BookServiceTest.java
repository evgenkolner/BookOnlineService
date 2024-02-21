package bookstore.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bookstore.dto.BookDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.mapper.BookMapper;
import bookstore.mapper.impl.BookMapperImpl;
import bookstore.model.Book;
import bookstore.model.Category;
import bookstore.repository.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Spy
    private BookMapper bookMapper = new BookMapperImpl();
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Save new book")
    void save_validBookRequestDto_returnNewBook() {
        Long id = 1L;
        Book book = defaultBook(id);

        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        bookDto.setCategoryIds(List.of());

        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto(
                "Book","Author","11111", BigDecimal.valueOf(99.99),
                "description","picture", List.of()
        );

        when(bookMapper.toBook(bookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.save(bookRequestDto);

        assertThat(actual).isEqualTo(bookDto);
    }

    @Test
    @DisplayName("Find all books")
    void findAll_BooksFromDb_ReturnAllBooks() {
        long id = 1L;
        Book book = defaultBook(id);

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        bookDto.setCategoryIds(List.of());

        Pageable pageable = PageRequest.of(0, 5);
        List<Book> books = List.of(book);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        List<BookDto> bookDtos = bookService.findAll(pageable);

        assertThat(bookDtos.get(0)).isEqualTo(bookDto);
    }

    @Test
    @DisplayName("Find book by id")
    void getBookById_ValidId_ReturnBookById() {
        Long id = 1L;
        Book book = defaultBook(id);

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        bookDto.setCategoryIds(List.of());

        when(bookRepository.getBookById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.getBookById(id);

        assertThat(actual).isEqualTo(bookDto);
    }

    @Test
    @DisplayName("Delete book by id")
    void deleteById_ValidId_Deleted() {
        Long id = 1L;
        bookService.deleteById(id);
        verify(bookRepository, times(1)).deleteById(id);
    }

    private Book defaultBook(Long id) {
        Book book = new Book();
        book.setId(id);
        book.setTitle("Book");
        book.setAuthor("Author");
        book.setIsbn("11111");
        book.setPrice(BigDecimal.valueOf(99.99));
        book.setDescription("description");
        book.setCoverImage("picture");
        book.setCategories(Set.of(new Category()));
        return book;
    }
}
