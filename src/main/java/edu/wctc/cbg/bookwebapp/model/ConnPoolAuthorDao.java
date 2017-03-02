package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
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
    
    private static final String AUTHOR_ID_COL_NAME = "author_id";
    private static final String AUTHOR_NAME_COL_NAME = "author_name";
    private static final String DATE_ADDED_COL_NAME = "date_added";
    
    private static final int MIN_MAX_RECORDS_PARAMETER = 1;

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
        db.openConnection(ds);
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
        db.openConnection(ds);
        
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
        db.openConnection(ds);
        
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
       db.openConnection(ds);
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
        db.openConnection(ds);
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
