package edu.wctc.cbg.bookwebapp.service;

import edu.wctc.cbg.bookwebapp.entity.Author;
import edu.wctc.cbg.bookwebapp.repository.AuthorRepository;
import edu.wctc.cbg.bookwebapp.repository.BookRepository;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 * Don't confuse the Spring @Respository annotation with the repository
 * classes (AuthorRepository, BookRespository). The annotation here is used 
 * solely to tell Spring to translate any exception messages into more
 * user friendly text. Any class annotated that way will do that.
 * 
 * @author jlombardo
 */
@Service
@Transactional(readOnly = true)
public class AuthorService {

    private transient final Logger LOG = LoggerFactory.getLogger(AuthorService.class);

    @Inject
    private AuthorRepository authorRepo;

    @Inject
    private BookRepository bookRepo;
    
    @Inject
    private DeleteNotificationEmailSender deleteNotificationEmailSender;

    public AuthorService() {}

    public List<Author> findAll() {
        return authorRepo.findAll();
    }
    
    public List<Author> findAllAlphabetically() {
        return authorRepo.findAllAlphabetically();
    }
    
    public List<Author> findAllEagerly() {
        List<Author> authors = authorRepo.findAll();
        for(Author a : authors) {
            a.getBookSet().size();
        }
        
        return authors;
    }
    
    /**
     * This custom method is necessary because we are using Hibernate which
     * does not load lazy relationships (in this case Books).
     * @param id
     * @return 
     */
    public Author findByIdAndFetchBooksEagerly(String id) {
        Integer authorId = new Integer(id);
        Author author = authorRepo.findOne(authorId);

        // Eagerly fetches books within a transaction
        author.getBookSet().size();
        
        return author;
    }

    public Author findById(String id) {
        return authorRepo.findOne(new Integer(id));
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param author 
     */
    @Transactional
    public void remove(Author author) {
        LOG.debug("Deleting author: " + author.getAuthorName());
        
        authorRepo.delete(author);
        
        deleteNotificationEmailSender.sendEmail("Author");
    }
    
    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param author 
     * @return  
     */
    @Transactional
    public Author edit(Author author) {
        return authorRepo.saveAndFlush(author);
    }
    /**
     * Custom method created for removing by id - Chris G
     * @param authorId
     */
    @Transactional
    public void removeById(String authorId){
        LOG.debug("Deleting author with ID: " + authorId);
        
        authorRepo.delete(new Integer(authorId));
        
        deleteNotificationEmailSender.sendEmail("Author");
    }
    /**
     * Custom method created for adding an author entity - Chris G
     * @param authorName
     * @return 
     */
    @Transactional
    public Author addAuthor(String authorName){
        Date dateAdded = new Date();
        
        Author author = new Author();
        author.setAuthorName(authorName);
        author.setDateAdded(dateAdded);
   
        return authorRepo.saveAndFlush(author);
    }
    /**
     * Custom method created for editing an author entity - Chris G
     * @param authorId
     * @param authorName
     * @return 
     */
    @Transactional
    public Author editAuthor(String authorId, String authorName){
        Author author = authorRepo.findOne(new Integer(authorId));
        author.setAuthorName(authorName);
        
        return authorRepo.saveAndFlush(author);
    }
    /**
     * Custom method created for adding or editing an author entity
     * (checks if authorId is null or empty)- Chris G
     * @param authorId
     * @param authorName
     * @return 
     */
    @Transactional
    public Author saveOrEdit(String authorId, String authorName){     
        if(authorId == null || authorId.isEmpty() || authorId.equals("0")){
            return this.addAuthor(authorName);
        }else{
            return this.editAuthor(authorId, authorName);
        }
    }
}
