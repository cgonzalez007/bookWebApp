package edu.wctc.cbg.bookwebapp.repository;

import edu.wctc.cbg.bookwebapp.entity.Authorities;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author cgonz
 */
public interface AuthorityRepository extends JpaRepository<Authorities, Integer>, Serializable{
    
}
