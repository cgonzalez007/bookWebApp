package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Chris Gonzalez 2017
 */
public class AuthorDao implements IAuthorDao {
    private DbAccessor db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public AuthorDao(DbAccessor db, String driverClass, String url, String 
            userName, String password) {
        setDb(db);
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
    }
    
    @Override
    public final int deleteAuthorById(String tableName, String authorIdColName, 
            Object authorId) throws ClassNotFoundException, SQLException{
        db.openConnection(driverClass, url, userName, password);
        int recsDeleted = db.deleteById(tableName, authorIdColName, authorId);
        db.closeConnection();
        return recsDeleted;
    }
    
    @Override
    public final List<Author> getAuthorList(String tableName, int maxRecords) 
            throws ClassNotFoundException, SQLException{
        db.openConnection(driverClass, url, userName, password);
        
        List<Author> records = new ArrayList<>();      
        List<Map<String, Object>> rawData = db.getAllRecords(tableName, 
                maxRecords);
        
        for(Map<String,Object> rawRec : rawData){
            Author author = new Author();
            
            Object objId = rawRec.get("author_id");
            Integer authorId = (Integer)objId;
            author.setAuthorId(authorId);
            
            Object objName = rawRec.get("author_name");
            String authorName = (objName != null) ? objName.toString() : "";
            author.setAuthorName(authorName);
            
            Object objDateAdded = rawRec.get("date_added");
            Date dateAdded = (objDateAdded != null) ? (Date)objDateAdded : null;
            author.setDateAdded(dateAdded);
            
            records.add(author);
        }
        db.closeConnection();
        
        return records;
    }
    @Override
    public final int addNewAuthor(String tableName, List<String> 
            authorTableColNames, List<Object> authorTableColValues) 
            throws ClassNotFoundException, SQLException{
        int authorsAdded = 0;
        db.openConnection(driverClass, url, userName, password);
        authorsAdded = db.insertInto(tableName, authorTableColNames, authorTableColValues);
        db.closeConnection();
        return authorsAdded;
    }
    

    @Override
    public final DbAccessor getDb() {
        return db;
    }

    @Override
    public final void setDb(DbAccessor db) {
        //VALIDATE
        this.db = db;
    }

    @Override
    public final String getDriverClass() {
        return driverClass;
    }

    @Override
    public final void setDriverClass(String driverClass) {
        //VALIDATE
        this.driverClass = driverClass;
    }

    @Override
    public final String getUrl() {
        return url;
    }

    @Override
    public final void setUrl(String url) {
        //VALIDATE
        this.url = url;
    }

    @Override
    public final String getUserName() {
        return userName;
    }

    @Override
    public final void setUserName(String userName) {
        //VALIDATE
        this.userName = userName;
    }

    @Override
    public final String getPassword() {
        return password;
    }

    @Override
    public final void setPassword(String password) {
        //VALIDATE
        this.password = password;
    }

    @Override
    public final int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.driverClass);
        hash = 97 * hash + Objects.hashCode(this.url);
        hash = 97 * hash + Objects.hashCode(this.userName);
        hash = 97 * hash + Objects.hashCode(this.password);
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
        final AuthorDao other = (AuthorDao) obj;
        if (!Objects.equals(this.driverClass, other.driverClass)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }   
    //TESTING PURPOSES
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDao test = new AuthorDao(new MySqlDbAccessor(),
                "com.mysql.jdbc.Driver", 
                        "jdbc:mysql://localhost:3306/book", 
                        "root", "admin");
        
        
//        test.deleteAuthorById("author", "author_id", "5");

        List<String> colNames = new ArrayList<>();
        colNames.add("author_name");
        colNames.add("date_added");
        List<Object> colValues = new ArrayList<>();
        colValues.add("Alex Trebek");
        colValues.add("2011-01-27");
        test.addNewAuthor("author", colNames, colValues);
        

        List<Author> authors = test.getAuthorList("author", 50);
        
        System.out.println(authors);
        
    }
}
