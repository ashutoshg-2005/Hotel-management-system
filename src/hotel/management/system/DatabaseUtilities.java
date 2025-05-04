package hotel.management.system;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

/**
 * A utility class that provides access to database views, triggers, and stored procedures
 * Allows administrators to view data from SQL views and execute the room maintenance stored procedure
 */
public class DatabaseUtilities extends JFrame implements ActionListener {
    JButton btnRoomStatus, btnEmpDetails, btnCustomers, btnRevenue, btnDrivers, btnMaintenance, btnBack;
    JTable table;
    JScrollPane scrollPane;
    Conn conn;
    
    public DatabaseUtilities() {
        // Modern UI setup
        setTitle("Hotel Management System - Summaries");
        setBounds(300, 200, 1000, 600);
        
        // Use gradient panel for background
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1000, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        add(headerPanel);
        
        JLabel headerTitle = new JLabel("HOTEL SUMMARIES", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Initialize database connection
        conn = new Conn();
        
        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(30, 80, 940, 50);
        buttonPanel.setLayout(new GridLayout(1, 6, 10, 0));
        buttonPanel.setOpaque(false);
        add(buttonPanel);
        
        // Create buttons for different views
        btnRoomStatus = new AnimatedButton("Room Status");
        btnRoomStatus.addActionListener(this);
        buttonPanel.add(btnRoomStatus);
        
        btnEmpDetails = new AnimatedButton("Employee Details");
        btnEmpDetails.addActionListener(this);
        buttonPanel.add(btnEmpDetails);
        
        btnCustomers = new AnimatedButton("Active Customers");
        btnCustomers.addActionListener(this);
        buttonPanel.add(btnCustomers);
        
        btnRevenue = new AnimatedButton("Revenue Summary");
        btnRevenue.addActionListener(this);
        buttonPanel.add(btnRevenue);
        
        btnDrivers = new AnimatedButton("Available Drivers");
        btnDrivers.addActionListener(this);
        buttonPanel.add(btnDrivers);
        
        btnMaintenance = new AnimatedButton("Schedule Maintenance");
        btnMaintenance.addActionListener(this);
        buttonPanel.add(btnMaintenance);
        
        // Create table panel with improved styling
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(30, 150, 940, 380);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(tablePanel);
        
        // Create table to display results
        table = new JTable();
        table.setRowHeight(25);
        table.setGridColor(new Color(240, 240, 240));
        
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Back button
        btnBack = new AnimatedButton("BACK");
        btnBack.setBounds(450, 540, 100, 30);
        btnBack.addActionListener(this);
        add(btnBack);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 570, 1000, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Database Utilities Panel");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            ResultSet rs = null;
            
            if (ae.getSource() == btnRoomStatus) {
                rs = conn.queryView("RoomStatusView");
                displayResultSet(rs, "Room Status View");
            } 
            else if (ae.getSource() == btnEmpDetails) {
                rs = conn.queryView("EmployeeDetailView");
                displayResultSet(rs, "Employee Details View");
            }
            else if (ae.getSource() == btnCustomers) {
                rs = conn.queryView("CustomerCheckInView");
                displayResultSet(rs, "Customer Check-In View");
            }
            else if (ae.getSource() == btnRevenue) {
                rs = conn.queryView("RevenueSummaryView");
                displayResultSet(rs, "Revenue Summary View");
            }
            else if (ae.getSource() == btnDrivers) {
                rs = conn.queryView("DriverAvailabilityView");
                displayResultSet(rs, "Available Drivers View");
            }
            else if (ae.getSource() == btnMaintenance) {
                rs = conn.executeScheduleRoomMaintenance();
                displayResultSet(rs, "Room Maintenance Schedule");
                JOptionPane.showMessageDialog(this, "Room maintenance has been scheduled!");
            }
            else if (ae.getSource() == btnBack) {
                setVisible(false);
                dispose();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    // Helper method to display ResultSet in JTable
    private void displayResultSet(ResultSet rs, String title) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        // Create column names array
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i-1] = metaData.getColumnName(i);
        }
        
        // Create data array for table model
        Object[][] data = new Object[100][columnCount]; // Assuming max 100 rows for simplicity
        int row = 0;
        
        while (rs.next() && row < 100) {
            for (int i = 1; i <= columnCount; i++) {
                data[row][i-1] = rs.getObject(i);
            }
            row++;
        }
        
        // Resize data array to actual row count
        Object[][] actualData = new Object[row][columnCount];
        for (int i = 0; i < row; i++) {
            System.arraycopy(data[i], 0, actualData[i], 0, columnCount);
        }
        
        // Create and set table model
        DefaultTableModel model = new DefaultTableModel(actualData, columnNames);
        table.setModel(model);
        
        // Set column header appearance
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(45, 62, 80));
        table.getTableHeader().setForeground(Color.WHITE);
        
        setTitle("Database Utilities - " + title);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new DatabaseUtilities());
    }
}