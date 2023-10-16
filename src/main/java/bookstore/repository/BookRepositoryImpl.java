package bookstore.repository;

import bookstore.exception.DataProcessingException;
import bookstore.model.Book;
import java.util.List;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert book into db " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "select b from Book b", Book.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find any books from DB", e);
        }
    }
}
