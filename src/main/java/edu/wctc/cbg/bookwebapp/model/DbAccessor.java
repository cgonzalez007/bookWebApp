package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cgonz
 */
public interface DbAccessor {

    public abstract void closeConnection() throws SQLException;

    /**
     * Delete record(s) by id
     * @param table
     * @param idColName
     * @param id
     * @return
     * @throws SQLException
     */
    public abstract int deleteById(String table, String idColName, Object id) 
            throws SQLException;

    /**
     * Retrieve all records
     * @param table
     * @param maxRecords
     * @return
     * @throws SQLException
     */
    public abstract List<Map<String, Object>> getAllRecords(String table,
            int maxRecords) throws SQLException;

    /**
     * Open database connection
     * @param driverClass
     * @param url
     * @param userName
     * @param password
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public abstract void openConnection(String driverClass, String url, 
            String userName, String password) throws ClassNotFoundException, 
            SQLException;
    
}
