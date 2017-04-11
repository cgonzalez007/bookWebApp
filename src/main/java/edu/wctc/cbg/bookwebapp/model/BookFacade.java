package edu.wctc.cbg.bookwebapp.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
        System.out.println(author.toString() + "\n" + book.toString());
    }
    
    public void update(String bookId, String title, String isbn, String authorId){
        Author author = getEntityManager().find(Author.class, new Integer(authorId));
        
        Book book = this.find(bookId);
        
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        
        this.edit(book);
        System.out.println(author.toString() + "\n" + book.toString());
    }
    
    public void addOrUpdate(String bookId, String title, String isbn, String authorId){   
        if(bookId == null || bookId.isEmpty() || bookId.equals("0")){
            addNew(title, isbn, authorId);
        }else{
            update(bookId, title, isbn, authorId);
        }
    }
    
    public Book find(String id){
        return getEntityManager().find(Book.class, new Integer(id));
    }
    
    public List<Book> findBooksByAuthorId(String authorId){
        String jpql = "SELECT b FROM Book b WHERE b.author.authorId = :authorId";
        TypedQuery<Book> q = getEntityManager().createQuery(jpql, Book.class);
        q.setParameter("authorId", new Integer(authorId));
        
        return q.getResultList();
    }
}
