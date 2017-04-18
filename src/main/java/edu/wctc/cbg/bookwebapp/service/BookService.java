package edu.wctc.cbg.bookwebapp.service;

import edu.wctc.cbg.bookwebapp.entity.Author;
import edu.wctc.cbg.bookwebapp.entity.Book;
import edu.wctc.cbg.bookwebapp.repository.AuthorRepository;
import edu.wctc.cbg.bookwebapp.repository.BookRepository;
import java.util.ArrayList;
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
public class BookService {
    
    private transient final Logger LOG = LoggerFactory.getLogger(BookService.class);
    
    @Inject
    private BookRepository bookRepo;
    
    @Inject
    private AuthorRepository authorRepo;

    public BookService() {
    }
    
    public List<Book> findAll() {
        return bookRepo.findAll();
    }
    
    public Book findById(String id) {
        return bookRepo.findOne(new Integer(id));
    }
    
    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param book 
     */
    @Transactional
    public void remove(Book book) {
        LOG.debug("Deleting book: " + book.getTitle());
        bookRepo.delete(book);
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param book 
     */
    @Transactional
    public Book edit(Book book) {
        return bookRepo.saveAndFlush(book);
    }
    /**
     * Custom method created for removing by id - Chris G
     * @param bookId
     */
    @Transactional
    public void removeById(String bookId){
        LOG.debug("Deleting author with ID: " + bookId);
        bookRepo.delete(new Integer(bookId));
    }
    /**
     * Custom method created for adding an author entity - Chris G
     * @param title
     * @param isbn
     * @param authorId
     * @return 
     */
    @Transactional
    public Book addBook(String title, String isbn, String authorId){
        Author author = authorRepo.findOne(new Integer(authorId));
        
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
   
        return bookRepo.saveAndFlush(book);
    }
    /**
     * Custom method created for editing an author entity - Chris G
     * @param bookId
     * @param title
     * @param isbn
     * @param authorId
     * @return 
     */
    @Transactional
    public Book editBook(String bookId, String title, String isbn, String authorId){
        Author author = authorRepo.findOne(new Integer(authorId));
        
        Book book = bookRepo.findOne(new Integer(bookId));
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        
        return bookRepo.saveAndFlush(book);
    }
    /**
     * Custom method created for adding or editing an author entity 
     * (Checks bookId if is null or empty) - Chris G
     * @param bookId
     * @param title
     * @param isbn
     * @param authorId
     * @return 
     */
    @Transactional
    public Book saveOrEdit(String bookId, String title, String isbn, String authorId){     
        if(bookId == null || bookId.isEmpty() || bookId.equals("0")){
            return this.addBook(title, isbn, authorId);
        }else{
            return this.editBook(bookId, title, isbn, authorId);
        }
    }
    @Transactional
    public List<Book> getBooksByAuthorIdAlphabetically(String authorId){
        return bookRepo.findAllByAuthorIdAlphabetically(new Integer(authorId));
    }
}
