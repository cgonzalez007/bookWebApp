package edu.wctc.cbg.bookwebapp.model;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author cgonz
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {

    @PersistenceContext(unitName = "edu.wctc.cbg_bookWebApp_war_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
    }
    
    public void deleteAuthorById(String id){    
        Author author = this.find(new Integer(id));
        this.remove(author);     
    }
    
    public void addNew(String name) {
        Author a = new Author();
        a.setAuthorName(name);
        Date date = new Date();
        a.setDateAdded(date);
        this.create(a);
        
    }
    
    public void update(String id, String name){
        Author a = this.find(new Integer(id));
        a.setAuthorName(name);
        this.edit(a);
    }
    
    public void addOrUpdate(String id, String name){   
        if(id == null || id.isEmpty() || id.equals("0")){
            addNew(name);
        }else{
            update(id, name);
        }
    }
    
    public Author find(String id){
        return getEntityManager().find(Author.class, new Integer(id));
    }
    
    public List<Author> findAllAlphabetized(){
        String jpql = "SELECT a FROM Author a ORDER BY a.authorName ASC";
        TypedQuery<Author> q = getEntityManager().createQuery(jpql, Author.class);
        return q.getResultList();
    }
}
