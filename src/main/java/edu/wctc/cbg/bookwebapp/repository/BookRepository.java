package edu.wctc.cbg.bookwebapp.repository;

import edu.wctc.cbg.bookwebapp.entity.Book;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jlombardo
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
    @Query("SELECT b.title FROM Book b")
    public Object[] findAllWithNameOnly();
    
//    @Query("SELECT b FROM Book b WHERE b.author.authorId = :authorId")
//    public List<Book> findAllByAuthorId(@Param("authorId") String authorId);
}
