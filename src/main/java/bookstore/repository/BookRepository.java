package bookstore.repository;

import bookstore.model.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
    @EntityGraph(attributePaths = "categories")
    Optional<Object> getBookById(Long id);

    @EntityGraph(attributePaths = "categories")
    List<Book> findAllWithCategories(Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    List<Book> findAllByCategoriesId(Long id, Pageable pageable);
}
