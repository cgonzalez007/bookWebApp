package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Chris Gonzalez 2017
 */
public class AuthorService {
    private static final int MIN_MAX_RECORDS_PARAMETER = 1;

    private IAuthorDao authorDao;
    /**
     * 
     * @param authorDao 
     */
    public AuthorService(IAuthorDao authorDao) {
        setAuthorDao(authorDao);
    }
    /**
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final List<Author> retrieveAuthors()throws ClassNotFoundException, 
            SQLException {
        return authorDao.getAuthorList();
    }
    /**
     * 
     * @param authorTableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final Author retrieveAuthor(String authorId) throws ClassNotFoundException, 
            SQLException,IllegalArgumentException{
        if (authorId == null || authorId.isEmpty()) {
            throw new InvalidInputException();
        }
        return authorDao.retrieveAuthor(authorId);
    }
    /**
     * 
     * @param authorTableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final int deleteAuthorById(String authorId) throws 
            ClassNotFoundException, SQLException,
            IllegalArgumentException{
        if (authorId == null || authorId.isEmpty()) {
            throw new InvalidInputException();
        }
        return authorDao.deleteAuthorById(authorId);
    }
    /**
     * 
     * @param authorId
     * @param authorName
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final int updateAuthorById(String authorName, String authorId) 
            throws ClassNotFoundException, SQLException, 
            IllegalArgumentException{
        if (authorName == null || authorId == null || authorName.isEmpty() || 
                authorId.isEmpty()) {
            throw new InvalidInputException();
        }
        return authorDao.updateAuthorById(authorName, authorId);
    }
    /**
     * 
     * @param authorName
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public final int addNewAuthor(String authorName) throws ClassNotFoundException, 
            SQLException, IllegalArgumentException {
        if (authorName == null || authorName.isEmpty()) {
            throw new InvalidInputException();
        }
        return authorDao.addNewAuthor(authorName);
    }
    /**
     * 
     * @return 
     */
    public final IAuthorDao getAuthorDao() {
        return authorDao;
    }
    /**
     * 
     * @param authorDao 
     */
    public final void setAuthorDao(IAuthorDao authorDao) throws 
            IllegalArgumentException{
       if(authorDao == null){
           throw new InvalidInputException();
       }
        this.authorDao = authorDao;
    }

    @Override
    public final int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.authorDao);
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuthorService other = (AuthorService) obj;
        if (!Objects.equals(this.authorDao, other.authorDao)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "AuthorService";
    }
    
}
