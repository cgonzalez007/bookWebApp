package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
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
    
    public final Author retrieveAuthor(String authorTableName, String
            authorIdColName, String authorId) throws ClassNotFoundException, 
            SQLException{
        return authorDao.retrieveAuthor(authorTableName, authorIdColName, authorId);
    }
    
    public final int deleteAuthorById(String authorTableName, String authorIdColName,
            String authorId) throws ClassNotFoundException, SQLException{
        return authorDao.deleteAuthorById(authorTableName, authorIdColName, authorId);
    }
    public final int updateAuthorById(String authorTableName, List<String> colNames, 
            List<Object> colValues, String authorIdColName, Object authorId) 
            throws ClassNotFoundException, SQLException{
        return authorDao.updateAuthorById(authorTableName, colNames, colValues, 
                authorIdColName, authorId);
    }
    public final int addNewAuthor(String authorTableName, List<String> authorTableColNames,
            List<Object> authorTableColValues) throws ClassNotFoundException, 
            SQLException{
        return authorDao.addNewAuthor(authorTableName, authorTableColNames, 
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
        AuthorService test = new AuthorService(
                new AuthorDao(
                        new MySqlDbAccessor(),"com.mysql.jdbc.Driver", 
                        "jdbc:mysql://localhost:3306/book", 
                        "root", "admin")
                        );
//        
//        List<String> colNames = new ArrayList<>();
//        colNames.add("author_name");
//        colNames.add("date_added");
//        List<Object> colValues = new ArrayList<>();
//        colValues.add("Sean Connery");
//        colValues.add("2017-02-16");
//        
//        authorService.addNewAuthor("author", colNames, colValues);
        
                   
//
//        List<String> colNamesUpdate = new ArrayList<>();
//        colNamesUpdate.add("author_name");
//        colNamesUpdate.add("date_added");
//        List<Object> colValuesUpdate = new ArrayList<>();
//        colValuesUpdate.add("THIS IS ANOTHER TEST");
//        colValuesUpdate.add("1999-01-01"); 
//
//        authorService.updateAuthorById("author", 
//                colNamesUpdate, colValuesUpdate, "author_id", "12");
//
////        int recsDeleted = 
////                authorService.deleteAuthorById("author", "author_id", "3");
//        
//        List<Author> authors = authorService.retrieveAuthors("author", 50);
//        
////        System.out.println("records deleted:   " + recsDeleted);
//        System.out.println("\n\n"+authors);

    Author author = test.retrieveAuthor("author", "author_id", "15");
        System.out.println(author);
    }
}
