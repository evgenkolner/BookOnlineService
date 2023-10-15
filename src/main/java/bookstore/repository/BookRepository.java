package bookstore.repository;

import bookstore.model.Book;
import org.hibernate.mapping.List;

public interface BookRepository {
    Book save(Book book);

    List findAll();
}
