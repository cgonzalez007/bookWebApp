package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
     /**
     * Db info
     */
    public static final String AUTHOR_TABLE_NAME = "author";
    public static final String AUTHOR_ID_COL_NAME = "author_id";
    public static final String AUTHOR_NAME_COL_NAME = "author_name";
    public static final String DATE_ADDED_COL_NAME = "date_added";
    public static final int MAX_RECORDS = 100;
    
    /*used for setting current datetime when adding new author to db*/
    private LocalDateTime currentDate;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 
     * @param db
     * @param driverClass
     * @param url
     * @param userName
     * @param password 
     */
    public AuthorDao(DbAccessor db, String driverClass, String url, String 
            userName, String password) {
        setDb(db);
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
    }
    /**
     * 
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final int deleteAuthorById(String authorId) throws 
            ClassNotFoundException, SQLException, IllegalArgumentException{
        if(authorId == null){
            throw new InvalidInputException();
        }
        db.openConnection(driverClass, url, userName, password);
        
        int recsDeleted = db.deleteById(AUTHOR_TABLE_NAME, AUTHOR_ID_COL_NAME, authorId);
        
        db.closeConnection();
        return recsDeleted;
    }
    /**
     * 
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final Author retrieveAuthor(String authorId)throws ClassNotFoundException, 
            SQLException, IllegalArgumentException {
        if(authorId == null || authorId.isEmpty()){
            throw new InvalidInputException();
        }
        db.openConnection(driverClass, url, userName, password);
        
        Map<String,Object> rawRec = db.getSingleRecord(
                AUTHOR_TABLE_NAME, AUTHOR_ID_COL_NAME, authorId);
        
        db.closeConnection();
        
        Author author = new Author();
        
        Object objId = rawRec.get(AUTHOR_ID_COL_NAME);
        Integer id = (Integer)objId;
        author.setAuthorId(id);

        Object objName = rawRec.get(AUTHOR_NAME_COL_NAME);
        String authorName = (objName != null) ? objName.toString() : "";
        author.setAuthorName(authorName);

        Object objDateAdded = rawRec.get(DATE_ADDED_COL_NAME);
        Date dateAdded = (objDateAdded != null) ? (Date)objDateAdded : null;
        author.setDateAdded(dateAdded);
        
        return author;
    }
    /**
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final List<Author> getAuthorList() throws ClassNotFoundException, 
            SQLException {
        db.openConnection(driverClass, url, userName, password);
        
        List<Author> records = new ArrayList<>();      
        List<Map<String, Object>> rawData = db.getAllRecords(AUTHOR_TABLE_NAME, 
                MAX_RECORDS);
        db.closeConnection();
        
        for(Map<String,Object> rawRec : rawData){
            Author author = new Author();
            
            Object objId = rawRec.get(AUTHOR_ID_COL_NAME);
            Integer authorId = (Integer)objId;
            author.setAuthorId(authorId);
            
            Object objName = rawRec.get(AUTHOR_NAME_COL_NAME);
            String authorName = (objName != null) ? objName.toString() : "";
            author.setAuthorName(authorName);
            
            Object objDateAdded = rawRec.get(DATE_ADDED_COL_NAME);
            Date dateAdded = (objDateAdded != null) ? (Date)objDateAdded : null;
            author.setDateAdded(dateAdded);
            
            records.add(author);
        }
        
        return records;
    }
    /**
     * 
     * @param authorId
     * @param authorName
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public final int updateAuthorById(String authorName, String authorId) 
            throws SQLException, ClassNotFoundException, IllegalArgumentException{
        if(authorName == null || authorId == null || authorName.isEmpty() || 
                authorId.isEmpty()){
            throw new InvalidInputException();
        }
        int authorRecordsUpdated = 0;
        List<String> colNames = new ArrayList<>();
                       colNames.add(AUTHOR_NAME_COL_NAME);
                       List<Object> colValues = new ArrayList<>();
                       colValues.add(authorName);
                       
        db.openConnection(driverClass, url, userName, password);
        
        authorRecordsUpdated = db.updateById(AUTHOR_TABLE_NAME, colNames, 
                colValues, AUTHOR_ID_COL_NAME, authorId);
        
        db.closeConnection();
        return authorRecordsUpdated;
    }
    /**
     * 
     * @param authorName
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final int addNewAuthor(String authorName) throws 
            ClassNotFoundException, SQLException, IllegalArgumentException{
        if(authorName == null || authorName.isEmpty()){
            throw new InvalidInputException();
        }
        currentDate = LocalDateTime.now();

                        List<String> colNames = new ArrayList<>();
                        colNames.add(AUTHOR_NAME_COL_NAME);
                        colNames.add(DATE_ADDED_COL_NAME);
                        List<Object> colValues = new ArrayList<>();
                        colValues.add(authorName);
                        colValues.add(DATE_TIME_FORMATTER.format(currentDate));
        int authorsAdded = 0;
        
        db.openConnection(driverClass, url, userName, password);
        
        authorsAdded = db.insertInto(AUTHOR_TABLE_NAME, colNames, 
                colValues);
        
        db.closeConnection();
        
        return authorsAdded;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final DbAccessor getDb() {
        return db;
    }
    /**
     * 
     * @param db 
     */
    public final void setDb(DbAccessor db) throws IllegalArgumentException {
       if(db == null){
           throw new InvalidInputException();
       }
        this.db = db;
    }
    /**
     * 
     * @return 
     */
    public final String getDriverClass() {
        return driverClass;
    }
    /**
     * 
     * @param driverClass 
     */
    public final void setDriverClass(String driverClass) throws 
            IllegalArgumentException {
       if(driverClass == null){
           throw new InvalidInputException();
       }
        this.driverClass = driverClass;
    }
    /**
     * 
     * @return 
     */
    public final String getUrl() {
        return url;
    }
    /**
     * 
     * @param url 
     */
    public final void setUrl(String url) throws IllegalArgumentException {
       if(url == null){
           throw new InvalidInputException();
       }
        this.url = url;
    }
    /**
     * 
     * @return 
     */
    public final String getUserName() {
        return userName;
    }
    /**
     * 
     * @param userName 
     */
    public final void setUserName(String userName) throws 
            IllegalArgumentException {
       if(userName == null){
           throw new InvalidInputException();
       }
        this.userName = userName;
    }
    /**
     * 
     * @return 
     */
    public final String getPassword() {
        return password;
    }
    /**
     * 
     * @param password 
     */
    public final void setPassword(String password) throws 
            IllegalArgumentException {
       if(password == null){
           throw new InvalidInputException();
       }
        this.password = password;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.driverClass);
        hash = 97 * hash + Objects.hashCode(this.url);
        hash = 97 * hash + Objects.hashCode(this.userName);
        hash = 97 * hash + Objects.hashCode(this.password);
        return hash;
    }
    /**
     * 
     * @param obj
     * @return 
     */
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
    /**
     * 
     * @return 
     */
    @Override
    public final String toString() {
        return "AuthorDao";
    }
    
}
