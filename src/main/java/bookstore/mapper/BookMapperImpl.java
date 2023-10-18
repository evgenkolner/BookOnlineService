package bookstore.mapper;

import bookstore.dto.BookDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        return dto;
    }

    @Override
    public Book toModel(CreateBookRequestDto requestDto) {
        Book book = new Book();
        book.setId(requestDto.getId());
        return book;
    }
}
