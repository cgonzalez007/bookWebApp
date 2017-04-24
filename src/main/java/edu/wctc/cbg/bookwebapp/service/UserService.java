package edu.wctc.cbg.bookwebapp.service;

import edu.wctc.cbg.bookwebapp.entity.Users;
import edu.wctc.cbg.bookwebapp.repository.UserRepository;
import java.util.Date;
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
    
    @Transactional
    public Users addUser(String username, String password, Boolean enabled, 
             Integer version){
        
        Date lastUpdate = new Date();
        
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(enabled);
        user.setVersion(version);
        user.setLastUpdate(lastUpdate);
   
        return userRepo.saveAndFlush(user);
    }
    /**
     * Custom method created for editing an Authorities entity - Chris G
     * @param userId
     * @param username
     * @param password
     * @param enabled
     * @param version
     * @return 
     */
    @Transactional
    public Users editUser(String userId, String username, String password, 
            Boolean enabled, Integer version){
        
        Users user = userRepo.findOne(new Integer(userId));
        Date lastUpdate = new Date();
        
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(enabled);
        user.setVersion(version);
        user.setLastUpdate(lastUpdate);
        
        return userRepo.saveAndFlush(user);
    }
    /**
     * Custom method created for adding or editing an Authorities entity
     * (checks if authorId is null or empty)- Chris G
     * @param userId
     * @param username
     * @param password
     * @param enabled
     * @param version
     * @return 
     */
    @Transactional
    public Users saveOrEdit(String userId, String username, String password, 
            Boolean enabled, Integer version){     
        if(userId == null || userId.isEmpty() || userId.equals("0")){
            return this.addUser(username, password, enabled, version);
        }else{
            return this.editUser(userId, username, password, enabled, version);
        }
    }
}
