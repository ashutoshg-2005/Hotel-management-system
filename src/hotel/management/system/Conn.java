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
}
