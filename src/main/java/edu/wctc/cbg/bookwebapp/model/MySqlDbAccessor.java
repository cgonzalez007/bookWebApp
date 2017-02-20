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

/**
 *
 * @author Chris Gonzalez
 */
public class MySqlDbAccessor implements DbAccessor {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
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
    public final List<Map<String,Object>> getAllRecords(String table, int maxRecords) 
            throws SQLException{
        if(table == null || table.isEmpty()){
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
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        int recordsDeleted = 0;
        String sql = "DELETE FROM  " + table + " WHERE " + pkName + 
                " = ?";
            
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, id);
        recordsDeleted = preparedStatement.executeUpdate();
            
        return recordsDeleted;
    }
    
    @Override
    public final int updateById(String tableName, List<String> colNamesToSet, 
            List<Object> colValues, String conditionColName, Object 
                    conditionColValue) throws SQLException{
        if(tableName == null || tableName.isEmpty() || colNamesToSet == null ||
                colValues == null || conditionColName == null || 
                conditionColName.isEmpty() || conditionColValue ==null){
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        int recordsUpdated = 0;
        
        String sql = "UPDATE " + tableName + " SET ";
        
        StringJoiner sj = new StringJoiner(",");
        
        for(String colName : colNamesToSet){
            sj.add(colName + " = ?");
        }
        sql += sj.toString();
        
        sql += " WHERE " + conditionColName + " = " + " ? ";
        preparedStatement = connection.prepareStatement(sql);
        
        for(int i =0;i < colNamesToSet.size() ; i++){
            preparedStatement.setObject(i+1, colValues.get(i));
        }
        
        preparedStatement.setObject(colNamesToSet.size()+1, conditionColValue);
        System.out.println(preparedStatement.toString());
        recordsUpdated = preparedStatement.executeUpdate();

        return recordsUpdated;
    }
    
    @Override
    public final int insertInto(String tableName, List<String> colNames, List<Object> 
            colValues) throws SQLException{
        if(tableName == null || tableName.isEmpty() || colNames == null ||
                colValues == null){
             throw new IllegalArgumentException(ERROR_INVALID_INPUT);
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
    //TESTING PURPOSES
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        MySqlDbAccessor test = new MySqlDbAccessor();
        
        test.openConnection("com.mysql.jdbc.Driver", 
                        "jdbc:mysql://localhost:3306/book", 
                        "root", "admin");
//        test.deleteById("author", "author_id", "2");
        
        
//        List<String> colNames = new ArrayList<>();
//        colNames.add("author_name");
//        colNames.add("date_added");
//        List<Object> colValues = new ArrayList<>();
//        colValues.add("Chris Gonzalez ");
//        colValues.add("2017-02-17");
//        test.insertInto("author", colNames, colValues);


        List<String> colNamesUpdate = new ArrayList<>();
        colNamesUpdate.add("author_name");
        colNamesUpdate.add("date_added");
        List<Object> colValuesUpdate = new ArrayList<>();
        colValuesUpdate.add("TEST TEST TEST");
        colValuesUpdate.add("2011-11-11"); 


        test.updateById("author", colNamesUpdate, colValuesUpdate, "author_id", "12");
        



        List<Map<String,Object>> records = test.getAllRecords("author", 50);
        
        System.out.println(records);
        
        test.closeConnection();
    }
}
