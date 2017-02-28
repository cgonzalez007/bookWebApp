package edu.wctc.cbg.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cgonz
 */
public interface DbAccessor {
    /**
     * 
     * @throws SQLException 
     */
    public abstract void closeConnection() throws SQLException;

    /**
     * Delete record(s) by id
     * @param tableName
     * @param idColName
     * @param id
     * @return
     * @throws SQLException
     */
    public abstract int deleteById(String tableName, String idColName, Object id) 
            throws SQLException;

    /**
     * 
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     * @throws SQLException 
     */        
    public int insertInto(String tableName, List<String> colNames, List<Object> 
            colValues) throws SQLException;
    /**
     * 
     * @param table
     * @param maxRecords
     * @return
     * @throws SQLException 
     */        
    public abstract List<Map<String, Object>> getAllRecords(String table,
            int maxRecords) throws SQLException;
    /**
     * 
     * @param table
     * @param idColName
     * @param recordId
     * @return
     * @throws SQLException 
     */
    public abstract Map<String,Object> getSingleRecord(String table, String 
            idColName, String recordId)throws SQLException;
    /**
     * 
     * @param tableName
     * @param colNamesToSet
     * @param colValues
     * @param conditionColName
     * @param conditionColValue
     * @return
     * @throws SQLException 
     */
    public int updateById(String tableName, List<String> colNamesToSet, 
            List<Object> colValues, String conditionColName, Object 
                    conditionColValue) throws SQLException;
    /**
     * 
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
