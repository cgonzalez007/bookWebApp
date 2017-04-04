package edu.wctc.cbg.bookwebapp.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cgonz
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "edu.wctc.cbg_bookWebApp_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }
    
    public void deleteBook(String id){    
        Book book = this.find(new Integer(id));
        this.remove(book);     
    }
    
    public void addNew(String title, String isbn, String authorId) {
        Author author = getEntityManager().find(Author.class, new Integer(authorId));
                
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        this.create(book);
        
    }
    
    public void update(String bookId, String title, String isbn, String authorId){
        Author author = getEntityManager().find(Author.class, new Integer(authorId));
        
        Book book = find(bookId);
        
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        
        this.edit(book);
    }
    
    public void addOrUpdate(String bookId, String title, String isbn, String authorId){   
        if(bookId == null || bookId.isEmpty() || bookId.equals("0")){
            addNew(title, isbn, authorId);
        }else{
            addOrUpdate(bookId, title, isbn, authorId);
        }
    }
    public Book find(String id){
        return getEntityManager().find(Book.class, new Integer(id));
    }
}
