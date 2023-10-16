package bookstore;

import bookstore.model.Book;
import bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookOnlineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookOnlineServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setTitle("Blood lands");
                book.setAuthor("Tymothy Snyder");
                book.setIsbn("53464364644");
                book.setPrice(BigDecimal.valueOf(1000));
                book.setDescription(
                        "Historical book");
                bookService.save(book);
                System.out.println(bookService.findAll());
            }
        };
    }
}
