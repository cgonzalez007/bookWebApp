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
    
    //TESTING PURPOSES
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService authorService = new AuthorService(
                new AuthorDao(
                        new MySqlDbAccessor(),"com.mysql.jdbc.Driver", 
                        "jdbc:mysql://localhost:3306/book", 
                        "root", "admin")
                        );
        
        List<String> colNames = new ArrayList<>();
        colNames.add("author_name");
        colNames.add("date_added");
        List<Object> colValues = new ArrayList<>();
        colValues.add("Sean Connery");
        colValues.add("2017-02-16");
        
        authorService.addNewAuthor("author", colNames, colValues);
        
                        
//        int recsDeleted = 
//                authorService.deleteAuthorById("author", "author_id", "3");
        
        List<Author> authors = authorService.retrieveAuthors("author", 50);
        
//        System.out.println("records deleted:   " + recsDeleted);
        System.out.println("\n\n"+authors);
    }
}
