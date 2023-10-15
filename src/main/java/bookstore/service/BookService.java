package bookstore.service;

import bookstore.model.Book;
import org.hibernate.mapping.List;

public interface BookService {
    Book save(Book book);

    List findAll();
}
