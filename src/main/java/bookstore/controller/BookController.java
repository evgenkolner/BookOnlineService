package bookstore.controller;

import java.util.List;
import bookstore.model.Book;
import bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<Book> getAll() {
        return bookService.findAll();
    }

    @GetMapping
    public BookDto getBookById(Long id) {

    }

    @PostMapping
    public BookDto createBook(CreateBookRequestDto bookDto) {
        return bookService.save(book);
    }

}
