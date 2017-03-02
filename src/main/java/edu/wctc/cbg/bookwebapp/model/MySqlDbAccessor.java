package edu.wctc.cbg.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.sql.DataSource;

/**
 * 
 * @author Chris Gonzalez 2017
 */
public class MySqlDbAccessor implements DbAccessor {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private static final int MIN_MAX_RECORDS_PARAMETER = 1;
    
    /**
     * Open a connection using a connection pool configured on server.
     *
     * @param ds - a reference to a connection pool via a JNDI name, producing
     * this object. Typically done in a servlet using InitalContext object.
     * @throws SQLException - if ds cannot be established
     */
    @Override
    public final void openConnection(DataSource ds) throws SQLException {
        connection = ds.getConnection();
    }

    /**
     * Open database connection
     * @param driverClass
     * @param url
     * @param userName
     * @param password
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    @Override
    public final void openConnection(String driverClass, String url, String userName, 
            String password) throws ClassNotFoundException, SQLException{
        if(driverClass.isEmpty() || driverClass == null || userName.isEmpty() ||
                userName == null || password.isEmpty() || password == null ||
                url.isEmpty() || url == null){        
            throw new InvalidInputException();
        }
        Class.forName(driverClass);
        connection = DriverManager.getConnection(url,userName,password);
    }
    /**
     * 
     * @throws SQLException 
     */
    @Override
    public final void closeConnection() throws SQLException{
        if(connection!= null){
            connection.close();
        }
    }
    /**
     * 
     * @param table
     * @param idColName
     * @param recordId
     * @return
     * @throws SQLException 
     */
    @Override
    public final Map<String,Object> getSingleRecord(String table, String 
            idColName, String recordId)throws SQLException, 
            IllegalArgumentException{
        if(table == null || idColName == null || recordId == null || 
                table.isEmpty() || idColName.isEmpty() || recordId.isEmpty()){
            throw new InvalidInputException();
        }
        
        String sql = "SELECT * FROM " + table + " WHERE " + idColName + " = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, recordId);
        resultSet = preparedStatement.executeQuery();
        
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colCount = rsmd.getColumnCount();
        
        Map<String,Object> map = new LinkedHashMap<>();
        
        while(resultSet.next()){
            for(int col = 1 ; col < colCount+1 ;col++){
                map.put(rsmd.getColumnName(col), resultSet.getObject(col));
            }
        }

        return map;
    }
    /**
     * Retrieve all records
     * @param table
     * @param maxRecords
     * @return
     * @throws SQLException 
     */
    @Override
    public final List<Map<String,Object>> getAllRecords(String table, int maxRecords) 
            throws SQLException{
        if(table == null || table.isEmpty() || maxRecords <
                MIN_MAX_RECORDS_PARAMETER){
            throw new InvalidInputException();
        }
        String sql = "";
        
        if(maxRecords > 0){
            sql = "SELECT * FROM " + table + " LIMIT " + maxRecords;
        } else {
            sql = "SELECT * FROM " + table;
        }
        
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colCount = rsmd.getColumnCount();
        
        List<Map<String,Object>> results = new ArrayList<>();
        
        while(resultSet.next()){
            Map<String,Object> map = new LinkedHashMap<>();
            
            for(int col = 1 ; col < colCount+1 ;col++){
                map.put(rsmd.getColumnName(col), resultSet.getObject(col));
            }
            results.add(map);
        }
        return results;
    }
    /**
     * Delete record(s) by id
     * @param table
     * @param pkName
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public final int deleteById(String table, String pkName, Object id) throws 
            SQLException{
        if(table == null ||table.isEmpty() || pkName == null || pkName.isEmpty() 
                || id == null){
            throw new InvalidInputException();
        }
        int recordsDeleted = 0;
        String sql = "DELETE FROM  " + table + " WHERE " + pkName + 
                " = ?";
            
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, id);
        recordsDeleted = preparedStatement.executeUpdate();
            
        return recordsDeleted;
    }
    /**
     * 
     * @param tableName
     * @param colNames
     * @param colValues
     * @param idColName
     * @param idColValue
     * @return
     * @throws SQLException 
     */
    @Override
    public final int updateById(String tableName, List<String> colNames, 
            List<Object> colValues, String idColName, Object 
                    idColValue) throws SQLException{
        if(tableName == null  || colNames == null || colValues == null || 
                idColName == null || idColValue ==null || idColName.isEmpty() 
                || tableName.isEmpty() || colNames.isEmpty() || 
                colValues.isEmpty()){
            throw new InvalidInputException();
        }
        int recordsUpdated = 0;
        
        String sql = "UPDATE " + tableName + " SET ";
        
        StringJoiner sj = new StringJoiner(",");
        
        for(String colName : colNames){
            sj.add(colName + " = ?");
        }
        sql += sj.toString();
        
        sql += " WHERE " + idColName + " = " + " ? ";
        preparedStatement = connection.prepareStatement(sql);
        
        for(int i =0;i < colNames.size() ; i++){
            preparedStatement.setObject(i+1, colValues.get(i));
        }
        
        preparedStatement.setObject(colNames.size()+1, idColValue);
        recordsUpdated = preparedStatement.executeUpdate();

        return recordsUpdated;
    }
    /**
     * 
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     * @throws SQLException 
     */
    @Override
    public final int insertInto(String tableName, List<String> colNames, List<Object> 
            colValues) throws SQLException{
        if(tableName == null || tableName.isEmpty() || colNames == null ||
                colValues == null || colNames.isEmpty() || colValues.isEmpty()){
             throw new InvalidInputException();
        }
        int recordsInserted = 0;
        
        String sql = "INSERT INTO " + tableName + " ";
        StringJoiner sj = new StringJoiner(",","(",")");
        
        for(String colName : colNames){
            sj.add(colName);
        }
        sql += sj.toString();
        sql += " VALUES ";
        
        sj = new StringJoiner(",","(",")");
        
        for(Object colValue : colValues){
            sj.add("?");
        }
        
        sql += sj.toString();
        
        preparedStatement = connection.prepareStatement(sql);
        
        for(int i = 0; i < colValues.size(); i++){
            preparedStatement.setObject(i+1, colValues.get(i));
        }
        
        recordsInserted = preparedStatement.executeUpdate();
        
        return recordsInserted;
    }
}
