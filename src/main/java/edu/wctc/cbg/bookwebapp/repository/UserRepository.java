package edu.wctc.cbg.bookwebapp.repository;

import edu.wctc.cbg.bookwebapp.entity.Users;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author cgonz
 */
public interface UserRepository extends JpaRepository<Users, Integer>, Serializable {
    
}
