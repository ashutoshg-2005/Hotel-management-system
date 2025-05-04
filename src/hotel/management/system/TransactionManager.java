package hotel.management.system;

import java.sql.*;

/**
 * TransactionManager - A helper class for database transaction management
 * Works with the hotel_transaction_management.sql script to provide
 * transaction control, rollback mechanisms, and deadlock prevention
 */
public class TransactionManager {
    private Connection conn;
    private String transactionId;
    private String username;
    private boolean transactionActive = false;
    
    /**
     * Creates a new TransactionManager with an existing database connection
     * @param conn Database connection to use for transactions
     * @param username User performing the transaction (for logging)
     */
    public TransactionManager(Connection conn, String username) {
        this.conn = conn;
        this.username = username;
    }
    
    /**
     * Creates a new TransactionManager with a new database connection
     * @param username User performing the transaction (for logging)
     * @throws SQLException if connection fails
     */
    public TransactionManager(String username) throws SQLException {
        Conn dbConn = new Conn();
        this.conn = dbConn.c;
        this.username = username;
    }
    
    /**
     * Begins a new transaction with logging
     * @return Transaction ID for the new transaction
     * @throws SQLException if transaction start fails
     */
    public String beginTransaction() throws SQLException {
        if (transactionActive) {
            throw new SQLException("Transaction already active");
        }
        
        try {
            // Call the stored procedure to start a transaction
            CallableStatement stmt = conn.prepareCall("{CALL start_logged_transaction(?)}");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                transactionId = rs.getString("transaction_id");
                
                // Set the transaction ID as a session variable
                PreparedStatement setVar = conn.prepareStatement("SET @current_transaction_id = ?");
                setVar.setString(1, transactionId);
                setVar.execute();
                setVar.close();
                
                transactionActive = true;
                return transactionId;
            } else {
                throw new SQLException("Failed to start transaction: No transaction ID returned");
            }
        } catch (SQLException e) {
            System.err.println("Error starting transaction: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Commits the current transaction with logging
     * @throws SQLException if commit fails
     */
    public void commitTransaction() throws SQLException {
        if (!transactionActive) {
            throw new SQLException("No active transaction to commit");
        }
        
        try {
            // Call the stored procedure to commit the transaction
            CallableStatement stmt = conn.prepareCall("{CALL commit_logged_transaction(?, ?)}");
            stmt.setString(1, transactionId);
            stmt.setString(2, username);
            stmt.execute();
            
            transactionActive = false;
        } catch (SQLException e) {
            System.err.println("Error committing transaction: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Rolls back the current transaction with logging
     * @param reason Reason for the rollback (for logging)
     * @throws SQLException if rollback fails
     */
    public void rollbackTransaction(String reason) throws SQLException {
        if (!transactionActive) {
            throw new SQLException("No active transaction to roll back");
        }
        
        try {
            // Call the stored procedure to roll back the transaction
            CallableStatement stmt = conn.prepareCall("{CALL rollback_logged_transaction(?, ?, ?)}");
            stmt.setString(1, transactionId);
            stmt.setString(2, username);
            stmt.setString(3, reason);
            stmt.execute();
            
            transactionActive = false;
        } catch (SQLException e) {
            System.err.println("Error rolling back transaction: " + e.getMessage());
            // Still try to do a direct rollback as a last resort
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error during direct rollback: " + ex.getMessage());
            }
            throw e;
        }
    }
    
    /**
     * Logs a deadlock that was detected
     * @param errorMessage The error message from the deadlock
     * @throws SQLException if logging fails
     */
    public void logDeadlock(String errorMessage) throws SQLException {
        try {
            CallableStatement stmt = conn.prepareCall("{CALL log_deadlock(?, ?)}");
            stmt.setString(1, transactionId);
            stmt.setString(2, errorMessage);
            stmt.execute();
        } catch (SQLException e) {
            System.err.println("Error logging deadlock: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Updates a room with optimistic concurrency control
     * @param roomNumber Room number to update
     * @param availability New availability value
     * @param cleaningStatus New cleaning status
     * @param price New price
     * @param bedType New bed type
     * @param currentVersion Current version of the room record
     * @return New version number after update
     * @throws SQLException if update fails due to concurrent modification or other error
     */
    public int updateRoomWithVersion(int roomNumber, String availability, 
                                   String cleaningStatus, double price,
                                   String bedType, int currentVersion) throws SQLException {
        try {
            CallableStatement stmt = conn.prepareCall("{CALL update_room_with_version(?, ?, ?, ?, ?, ?)}");
            stmt.setInt(1, roomNumber);
            stmt.setString(2, availability);
            stmt.setString(3, cleaningStatus);
            stmt.setDouble(4, price);
            stmt.setString(5, bedType);
            stmt.setInt(6, currentVersion);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("version");
            } else {
                throw new SQLException("Room update failed: No version returned");
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Optimistic lock failure")) {
                // Handle optimistic locking failure - typically by informing the user to retry
                System.err.println("Room was modified by another user. Please reload and try again.");
            }
            throw e;
        }
    }
    
    /**
     * Recovers a failed transaction using the transaction log
     * @param transactionIdToRecover ID of the transaction to recover
     * @throws SQLException if recovery fails
     */
    public void recoverFailedTransaction(String transactionIdToRecover) throws SQLException {
        try {
            CallableStatement stmt = conn.prepareCall("{CALL recover_failed_transaction(?)}");
            stmt.setString(1, transactionIdToRecover);
            stmt.execute();
        } catch (SQLException e) {
            System.err.println("Error recovering transaction: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Gets the current transaction ID
     * @return Current transaction ID or null if no transaction is active
     */
    public String getTransactionId() {
        return transactionId;
    }
    
    /**
     * Checks if a transaction is currently active
     * @return true if a transaction is active, false otherwise
     */
    public boolean isTransactionActive() {
        return transactionActive;
    }
    
    /**
     * Gets the connection being used for the current transaction
     * @return Database connection object
     */
    public Connection getConnection() {
        return conn;
    }
    
    /**
     * Executes a function within a transaction, with automatic commit/rollback
     * @param <T> Return type of the function
     * @param function Function to execute within the transaction
     * @return Result of the function
     * @throws Exception if the function throws an exception
     */
    public <T> T executeInTransaction(TransactionFunction<T> function) throws Exception {
        T result = null;
        boolean success = false;
        
        try {
            beginTransaction();
            result = function.apply(conn);
            commitTransaction();
            success = true;
            return result;
        } catch (SQLException e) {
            if (transactionActive) {
                if (e.getMessage().contains("Deadlock")) {
                    logDeadlock(e.getMessage());
                }
                rollbackTransaction("Error: " + e.getMessage());
            }
            throw e;
        } catch (Exception e) {
            if (transactionActive) {
                rollbackTransaction("Error: " + e.getMessage());
            }
            throw e;
        } finally {
            if (transactionActive && !success) {
                try {
                    rollbackTransaction("Transaction was not explicitly committed or rolled back");
                } catch (SQLException e) {
                    System.err.println("Error in final rollback: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Functional interface for executing code within a transaction
     * @param <T> Return type of the function
     */
    @FunctionalInterface
    public interface TransactionFunction<T> {
        T apply(Connection connection) throws Exception;
    }
}