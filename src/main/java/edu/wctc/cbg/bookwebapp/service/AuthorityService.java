package edu.wctc.cbg.bookwebapp.service;

import edu.wctc.cbg.bookwebapp.entity.Authorities;
import edu.wctc.cbg.bookwebapp.repository.AuthorityRepository;
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
public class AuthorityService {
    private transient final Logger LOG = LoggerFactory.getLogger(AuthorityService.class);
    
    @Inject
    private AuthorityRepository authorityRepo;
    
    public AuthorityService(){}
    
    public List<Authorities> findAll(){
        return authorityRepo.findAll();
    }
    
    public Authorities findById(String id) {
        return authorityRepo.findOne(new Integer(id));
    }
     
    @Transactional
    public void remove(Authorities authority) {
        LOG.debug("Deleting author: " + authority.getUsername());
        authorityRepo.delete(authority);
    } 
    
    @Transactional
    public Authorities edit(Authorities authority) {
        return authorityRepo.saveAndFlush(authority);
    }
}
