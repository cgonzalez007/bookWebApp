package edu.wctc.cbg.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chris Gonzalez
 */
public class MySqlDbAccessor implements DbAccessor {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private static final String ERROR_INVALID_INPUT = "Error: Invalid input. "
            + "Input cannot be null or empty. Number values cannot be less than"
            + "1.";
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
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        
        Class.forName(driverClass);
        connection = DriverManager.getConnection(url,userName,password);
    }
    
    @Override
    public final void closeConnection() throws SQLException{
        if(connection!= null){
            connection.close();
        }
    }
    /**
     * Retrieve all records
     * @param table
     * @param maxRecords
     * @return
     * @throws SQLException 
     */
    @Override
    public List<Map<String,Object>> getAllRecords(String table, int maxRecords) 
            throws SQLException{
        if(table.isEmpty() || table == null){
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
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
     * @param idColName
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteById(String table, String idColName, Object id) 
            throws SQLException{
        if(table.isEmpty() || table == null || idColName.isEmpty() || 
                idColName == null || id == null){
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        int recordsDeleted = 0;
        String sql1 = "DELETE FROM  " + table + " WHERE " + idColName + 
                " = ";
        String sql2 = "";
        if(id instanceof String){
            sql2 = "'" + id.toString() + "'";
        }else{
            sql2 = id.toString();
        }
        String sql = sql1 + sql2;      
        statement = connection.createStatement();
        recordsDeleted = statement.executeUpdate(sql);
            
        return recordsDeleted;
    }
//
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        DbAccessor test = new MySqlDbAccessor();
//        test.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        
//        List<Map<String,Object>> records = test.getAllRecords("author", 3);
//        
//        System.out.println(records);
//        test.closeConnection();
//    }
}
