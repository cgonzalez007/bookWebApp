package edu.wctc.cbg.bookwebapp.repository;

import edu.wctc.cbg.bookwebapp.entity.Author;
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
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    
    @Query("SELECT a.authorName FROM Author a")
    public Object[] findAllWithNameOnly();
    
    @Query("SELECT a FROM Author a ORDER BY a.authorName ASC")
    public List<Author> findAllAlphabetically();
}
