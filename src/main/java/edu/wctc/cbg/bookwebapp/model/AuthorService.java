package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Chris Gonzalez 2017
 */
public class AuthorService {
    private IAuthorDao authorDao;

    public AuthorService(IAuthorDao authorDao) {
        setAuthorDao(authorDao);
    }

    public final List<Author> retrieveAuthors(String tableName, int maxRecords) 
            throws ClassNotFoundException, SQLException{
        return authorDao.getAuthorList(tableName,maxRecords);
    }
    
    public final int deleteAuthorById(String tableName, String authorIdColName,
            String authorId) throws ClassNotFoundException, SQLException{
        return authorDao.deleteAuthorById(tableName, authorIdColName, authorId);
    }
    public final int updateAuthorById(String authorTableName, List<String> colNames, 
            List<Object> colValues, String authorIdColName, Object authorId) 
            throws ClassNotFoundException, SQLException{
        return authorDao.updateAuthorById(authorTableName, colNames, colValues, 
                authorIdColName, authorId);
    }
    public final int addNewAuthor(String tableName, List<String> authorTableColNames,
            List<Object> authorTableColValues) throws ClassNotFoundException, 
            SQLException{
        return authorDao.addNewAuthor(tableName, authorTableColNames, 
                authorTableColValues);
    }
    
    public final IAuthorDao getAuthorDao() {
        return authorDao;
    }

    public final void setAuthorDao(IAuthorDao authorDao) {
        //VALIDATE
        this.authorDao = authorDao;
    }
}
