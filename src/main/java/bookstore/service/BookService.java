package bookstore.service;

import bookstore.dto.BookDto;
import bookstore.dto.BookWithoutCategoryDto;
import bookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    void deleteById(Long id);

    BookDto update(CreateBookRequestDto bookRequestDto, Long id);

    List<BookWithoutCategoryDto> getBooksByCategoryId(Long id, Pageable pageable);
}
