package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author Chris Gonzalez 2017
 */
public interface IAuthorDao {
    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public abstract boolean equals(Object obj);
    /**
     * 
     * @param tableName
     * @param maxRecords
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public abstract List<Author> getAuthorList() 
            throws ClassNotFoundException, SQLException;
    /**
     * 
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public abstract Author retrieveAuthor(String authorId) throws ClassNotFoundException, 
            SQLException;
    /**
     * 
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public abstract int deleteAuthorById(String authorId) throws 
            ClassNotFoundException, SQLException;
    /**
     * 
     * @param authorName
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public int addNewAuthor(String authorName) throws 
            ClassNotFoundException, SQLException;
    /**
     * 
     * @param authorName
     * @param authorId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public int updateAuthorById(String authorName, String authorId) 
            throws SQLException,ClassNotFoundException;
    /**
     * 
     * @return 
     */
    public abstract DbAccessor getDb();
    /**
     * 
     * @return 
     */
    
}
