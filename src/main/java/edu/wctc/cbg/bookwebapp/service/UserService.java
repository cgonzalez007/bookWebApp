package edu.wctc.cbg.bookwebapp.service;

import edu.wctc.cbg.bookwebapp.entity.Users;
import edu.wctc.cbg.bookwebapp.repository.UserRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cgonz
 */
@Service
@Transactional(readOnly = true)
public class UserService {
    
    private transient final Logger LOG = LoggerFactory.getLogger(UserService.class);
    
    @Inject
    private UserRepository userRepo;
    
    public UserService(){}
    
    public List<Users> findAll(){
        return userRepo.findAll();
    }
    
    public Users findById(String id) {
        return userRepo.findOne(new Integer(id));
    }
     
    @Transactional
    public void remove(Users user) {
        LOG.debug("Deleting author: " + user.getUsername());
        userRepo.delete(user);
    } 
    
    @Transactional
    public Users edit(Users user) {
        return userRepo.saveAndFlush(user);
    }
}
