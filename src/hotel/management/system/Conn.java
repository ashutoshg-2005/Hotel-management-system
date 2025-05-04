package hotel.management.system;
import java.sql.*;
import java.io.*;
import java.util.Properties;

public class Conn {
    Connection c;
    Statement s;
    
    Conn(){
        try{
            // Load database properties from a configuration file if exists
            Properties props = new Properties();
            String dbUrl = "jdbc:mysql://localhost:3306/hotelmanagementsystem2";
            String username = "root";
            String password = "1234";
            
            try {
                FileInputStream configFile = new FileInputStream("config.properties");
                props.load(configFile);
                configFile.close();
                
                // Override default values if properties exist
                dbUrl = props.getProperty("db.url", dbUrl);
                username = props.getProperty("db.username", username);
                password = props.getProperty("db.password", password);
            } catch (IOException e) {
                System.out.println("Using default database configuration");
            }
            
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            c = DriverManager.getConnection(dbUrl, username, password);
            
            // Set auto-commit to false by default for transaction control
            c.setAutoCommit(false);
            
            s = c.createStatement();
            
            System.out.println("Database connection established successfully");
        } catch(SQLException e){
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            System.err.println("Database driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch(Exception e){
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Method to close resources
    public void closeConnection() {
        try {
            if (s != null) s.close();
            if (c != null) c.close();
            System.out.println("Database connection closed");
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    // Method to commit a transaction
    public void commit() throws SQLException {
        c.commit();
    }
    
    // Method to rollback a transaction
    public void rollback() throws SQLException {
        c.rollback();
    }
    
    // New methods for database views and stored procedures
    
    /**
     * Execute a query on a database view
     * @param viewName the name of the view to query
     * @return ResultSet containing the view data
     * @throws SQLException if a database error occurs
     */
    public ResultSet queryView(String viewName) throws SQLException {
        String query = "SELECT * FROM " + viewName;
        return s.executeQuery(query);
    }
    
    /**
     * Execute a filtered query on a database view
     * @param viewName the name of the view to query
     * @param whereClause the WHERE clause to filter results
     * @return ResultSet containing the filtered view data
     * @throws SQLException if a database error occurs
     */
    public ResultSet queryViewWithFilter(String viewName, String whereClause) throws SQLException {
        String query = "SELECT * FROM " + viewName + " WHERE " + whereClause;
        return s.executeQuery(query);
    }
    
    /**
     * Execute the room maintenance procedure that uses a cursor
     * @return ResultSet containing the maintenance schedule
     * @throws SQLException if a database error occurs
     */
    public ResultSet executeScheduleRoomMaintenance() throws SQLException {
        CallableStatement cstmt = c.prepareCall("{CALL schedule_room_maintenance()}");
        return cstmt.executeQuery();
    }
    
    /**
     * Execute a parameterized SQL query using PreparedStatement
     * @param sql the SQL query with placeholders
     * @param params the parameters to bind to the query
     * @return ResultSet containing the query results
     * @throws SQLException if a database error occurs
     */
    public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement(sql);
        
        // Set parameters
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i+1, params[i]);
        }
        
        return pstmt.executeQuery();
    }
    
    /**
     * Execute a parameterized SQL update using PreparedStatement
     * @param sql the SQL update with placeholders
     * @param params the parameters to bind to the update
     * @return number of rows affected
     * @throws SQLException if a database error occurs
     */
    public int executeUpdate(String sql, Object... params) throws SQLException {
        PreparedStatement pstmt = c.prepareStatement(sql);
        
        // Set parameters
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i+1, params[i]);
        }
        
        return pstmt.executeUpdate();
    }
    
    /**
     * Execute a SQL script to create database views and triggers
     * @param scriptPath the path to the SQL script file
     * @throws Exception if an error occurs
     */
    public void executeSQLScript(String scriptPath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(scriptPath));
        StringBuilder sb = new StringBuilder();
        String line;
        
        // Read the script file
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        
        // Split the script into individual statements by delimiter
        String[] statements = sb.toString().split(";");
        
        // Execute each statement
        for (String statement : statements) {
            statement = statement.trim();
            if (!statement.isEmpty()) {
                s.execute(statement);
            }
        }
    }
}
