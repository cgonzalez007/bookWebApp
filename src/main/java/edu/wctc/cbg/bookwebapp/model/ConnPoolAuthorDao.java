package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;

/**
 * This DAO is a strategy object that uses a server-side connection pool to 
 * optimize the creation and use of connections by a web app. 
 * Such connections are VERY performant because they are created in advance, 
 * stored in an in-memory pool and loaned out for quick access. When done the 
 * connection is returned to the pool.
 * 
 * @author Chris Gonzalez 2017
 */
public class ConnPoolAuthorDao implements IAuthorDao {
    
    private DataSource ds;
    private DbAccessor db;
    
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
     * @param ds
     * @param db
     */
    public ConnPoolAuthorDao(DataSource ds, DbAccessor db) {
        setDb(db);
        setDs(ds);
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
        db.openConnection(ds);
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
        db.openConnection(ds);
        
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
        db.openConnection(ds);
        
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
        db.openConnection(ds);
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
        db.openConnection(ds);
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

    public final DataSource getDs() {
        return ds;
    }

    public final void setDs(DataSource ds) throws IllegalArgumentException {
        if(ds == null){
           throw new InvalidInputException();
       }
        this.ds = ds;
    }
    /**
     * 
     * @return 
     */
    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.ds);
        hash = 89 * hash + Objects.hashCode(this.db);
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
        final ConnPoolAuthorDao other = (ConnPoolAuthorDao) obj;
        if (!Objects.equals(this.ds, other.ds)) {
            return false;
        }
        if (!Objects.equals(this.db, other.db)) {
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
        return "ConnPoolAuthorDao";
    }
    
}
