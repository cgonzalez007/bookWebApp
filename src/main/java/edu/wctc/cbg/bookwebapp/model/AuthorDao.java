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
    
    private static final String AUTHOR_ID_COL_NAME = "author_id";
    private static final String AUTHOR_NAME_COL_NAME = "author_name";
    private static final String DATE_ADDED_COL_NAME = "date_added";
    
    private static final int MIN_MAX_RECORDS_PARAMETER = 1;

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
     * @param authorTableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final int deleteAuthorById(String authorTableName, String authorIdColName, 
            Object authorId) throws ClassNotFoundException, SQLException, 
            IllegalArgumentException{
        if(authorTableName == null || authorIdColName == null || authorId == null ||
                authorTableName.isEmpty() || authorIdColName.isEmpty()){
            throw new InvalidInputException();
        }
        db.openConnection(driverClass, url, userName, password);
        int recsDeleted = db.deleteById(authorTableName, authorIdColName, authorId);
        db.closeConnection();
        return recsDeleted;
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
    @Override
    public final Author retrieveAuthor(String authorTableName, String 
            authorIdColName, String authorId)throws ClassNotFoundException, 
            SQLException, IllegalArgumentException {
        if(authorTableName == null || authorIdColName == null || authorId == null ||
                authorTableName.isEmpty() || authorIdColName.isEmpty() ||
                authorId.isEmpty()){
            throw new InvalidInputException();
        }
        db.openConnection(driverClass, url, userName, password);
        
        Map<String,Object> rawRec = db.getSingleRecord(
                authorTableName, authorIdColName, authorId);
        
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
        
        db.closeConnection();
        
        return author;
    }
    /**
     * 
     * @param authorTableName
     * @param maxRecords
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final List<Author> getAuthorList(String authorTableName, int maxRecords) 
            throws ClassNotFoundException, SQLException,IllegalArgumentException{
        if(authorTableName == null || authorTableName.isEmpty() || maxRecords <
                MIN_MAX_RECORDS_PARAMETER){
            throw new InvalidInputException();
        }
        db.openConnection(driverClass, url, userName, password);
        
        List<Author> records = new ArrayList<>();      
        List<Map<String, Object>> rawData = db.getAllRecords(authorTableName, 
                maxRecords);
        
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
        db.closeConnection();
        
        return records;
    }
    /**
     * 
     * @param authorTableName
     * @param colNames
     * @param colValues
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public final int updateAuthorById(String authorTableName, List<String> 
            colNames, List<Object> colValues, String authorIdColName, Object 
            authorId) throws SQLException, ClassNotFoundException,
            IllegalArgumentException{
        if(authorTableName == null || colNames == null || colValues == null ||
                authorIdColName ==null || authorId == null || 
                authorTableName.isEmpty() || colNames.isEmpty() || 
                colValues.isEmpty() || authorIdColName.isEmpty()){
            throw new InvalidInputException();
        }
        int authorRecordsUpdated = 0;
        db.openConnection(driverClass, url, userName, password);
        authorRecordsUpdated = db.updateById(authorTableName, colNames, 
                colValues, authorIdColName, authorId);
        db.closeConnection();
        return authorRecordsUpdated;
    }
    /**
     * 
     * @param authorTableName
     * @param authorTableColNames
     * @param authorTableColValues
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final int addNewAuthor(String authorTableName, List<String> 
            authorTableColNames, List<Object> authorTableColValues) throws 
            ClassNotFoundException, SQLException, IllegalArgumentException{
        if(authorTableName == null || authorTableColNames == null || 
                authorTableColValues == null || authorTableName.isEmpty() || 
                authorTableColNames.isEmpty()|| authorTableColValues.isEmpty()){
            throw new InvalidInputException();
        }
        int authorsAdded = 0;
        db.openConnection(driverClass, url, userName, password);
        authorsAdded = db.insertInto(authorTableName, authorTableColNames, 
                authorTableColValues);
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
    @Override
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
    @Override
    public final String getDriverClass() {
        return driverClass;
    }
    /**
     * 
     * @param driverClass 
     */
    @Override
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
    @Override
    public final String getUrl() {
        return url;
    }
    /**
     * 
     * @param url 
     */
    @Override
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
    @Override
    public final String getUserName() {
        return userName;
    }
    /**
     * 
     * @param userName 
     */
    @Override
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
    @Override
    public final String getPassword() {
        return password;
    }
    /**
     * 
     * @param password 
     */
    @Override
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

    @Override
    public final String toString() {
        return "AuthorDao";
    }
    
}
