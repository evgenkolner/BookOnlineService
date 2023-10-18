package bookstore.repository;

import bookstore.exception.DataProcessingException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.Book;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert book into db " + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "from Book", Book.class).getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Can't find any books from DB", e);
        }
    }

    @Override
    public Optional<Book> getBookByID(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Book.class, id));
        } catch (Exception e) {
            throw new EntityNotFoundException("Can`t find book by id " + id, e);
        }
    }
}
