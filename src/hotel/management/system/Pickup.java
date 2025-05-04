package hotel.management.system;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;

public class Pickup extends JFrame implements ActionListener {
    JTable table;
    JButton back, submit, updateAvailability, assignDriver;
    Choice typeofcar;
    JScrollPane scrollPane;

    public Pickup(){
        // Modern UI setup
        setTitle("Hotel Management System - Pickup Service");
        setBounds(300, 200, 1000, 650); // Increased height to accommodate new buttons
        
        // Use gradient panel for background instead of plain white
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1000, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        add(headerPanel);
        
        JLabel headerTitle = new JLabel("PICKUP SERVICE", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Search panel with improved styling
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(50, 80, 900, 70);
        searchPanel.setLayout(null);
        searchPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(searchPanel);
        
        JLabel lblCar = new JLabel("Type of Car");
        lblCar.setBounds(50, 25, 100, 20);
        lblCar.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchPanel.add(lblCar);
        
        typeofcar = new Choice();
        typeofcar.setBounds(150, 25, 200, 25);
        typeofcar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchPanel.add(typeofcar);
        
        // Populate the choice with available car brands from the driver table.
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT DISTINCT brand FROM driver ORDER BY brand");
            while (rs.next()){
                typeofcar.add(rs.getString("brand"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        // Use AnimatedButton for submit
        submit = new AnimatedButton("SEARCH");
        submit.setBounds(400, 20, 120, 35);
        submit.addActionListener(this);
        searchPanel.add(submit);
        
        // Table panel with improved styling
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(50, 170, 900, 330);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(tablePanel);
        
        // Table with custom styling
        table = new JTable();
        
        // Style the table
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(5, 5));
        table.setGridColor(new Color(230, 230, 230));
        table.setForeground(new Color(33, 33, 33));
        table.setSelectionBackground(new Color(180, 210, 255));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        // Fix column alignment with a custom renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Add table to a scroll pane for better viewing
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create a panel for the column headers
        JPanel headerLabelsPanel = new JPanel();
        headerLabelsPanel.setLayout(null);
        headerLabelsPanel.setBackground(new Color(52, 73, 94));
        headerLabelsPanel.setPreferredSize(new Dimension(900, 40));
        
        // Column header labels with better styling
        String[] headers = {"Driver ID", "Name", "Age", "Gender", "Company", "Brand", "Availability", "Location"};
        int[] widths = {80, 140, 60, 80, 140, 120, 120, 140};
        int xPos = 10;
        
        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i], JLabel.CENTER);
            label.setBounds(xPos, 10, widths[i], 20);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            headerLabelsPanel.add(label);
            xPos += widths[i] + 5;
        }
        
        tablePanel.add(headerLabelsPanel, BorderLayout.NORTH);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(50, 510, 900, 50);
        buttonsPanel.setLayout(new GridLayout(1, 3, 20, 0));
        buttonsPanel.setOpaque(false);
        add(buttonsPanel);
        
        // Update availability button
        updateAvailability = new AnimatedButton("UPDATE AVAILABILITY");
        updateAvailability.addActionListener(this);
        buttonsPanel.add(updateAvailability);
        
        // Assign driver to customer button
        assignDriver = new AnimatedButton("ASSIGN TO CUSTOMER");
        assignDriver.addActionListener(this);
        buttonsPanel.add(assignDriver);
        
        // Back button
        back = new AnimatedButton("BACK");
        back.addActionListener(this);
        buttonsPanel.add(back);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 620, 1000, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Pickup Service Panel", JLabel.LEFT);
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        add(statusBar);
        
        // Load driver data
        loadDriverData();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    // Method to load driver data
    private void loadDriverData() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(
                "SELECT driver_id, name, age, gender, company, brand, available, location " +
                "FROM driver ORDER BY driver_id"
            );
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
            // Set column widths
            int[] columnWidths = {80, 140, 60, 80, 140, 120, 120, 140};
            for (int i = 0; i < columnWidths.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
                table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        setHorizontalAlignment(JLabel.CENTER);
                        return c;
                    }
                });
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to update driver availability
    private void updateDriverAvailability() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a driver from the table", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String driverId = table.getValueAt(selectedRow, 0).toString();
        String currentStatus = table.getValueAt(selectedRow, 6).toString();
        String newStatus = currentStatus.equals("Available") ? "Busy" : "Available";
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Change driver status from " + currentStatus + " to " + newStatus + "?",
            "Update Driver Status", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                TransactionManager txManager = new TransactionManager("admin");
                txManager.beginTransaction();
                
                Connection conn = txManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE driver SET available = ? WHERE driver_id = ?"
                );
                pstmt.setString(1, newStatus);
                pstmt.setString(2, driverId);
                
                int result = pstmt.executeUpdate();
                
                if (result > 0) {
                    txManager.commitTransaction();
                    JOptionPane.showMessageDialog(
                        this,
                        "Driver status updated successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    // Refresh table
                    loadDriverData();
                } else {
                    txManager.rollbackTransaction("No rows affected");
                    JOptionPane.showMessageDialog(
                        this,
                        "Failed to update driver status",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    // Method to assign driver to a customer
    private void assignDriverToCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a driver from the table", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String driverId = table.getValueAt(selectedRow, 0).toString();
        String driverName = table.getValueAt(selectedRow, 1).toString();
        String availability = table.getValueAt(selectedRow, 6).toString();
        
        if (!availability.equals("Available")) {
            JOptionPane.showMessageDialog(this, "Selected driver is not available", "Driver Unavailable", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get list of customers who don't already have a driver assigned
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(
                "SELECT c.customer_id, c.name, c.room_number " +
                "FROM customer c " +
                "LEFT JOIN driver d ON c.customer_id = d.customer_id " +
                "WHERE d.customer_id IS NULL"
            );
            
            // Check if there are any customers without assigned drivers
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "No customers available for driver assignment", "No Customers", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Create a list of customers to choose from
            java.util.List<String> customerOptions = new java.util.ArrayList<>();
            java.util.Map<String, Integer> customerIdMap = new java.util.HashMap<>();
            
            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String customerName = rs.getString("name");
                String roomNumber = rs.getString("room_number");
                String displayOption = customerId + " - " + customerName + " (Room: " + roomNumber + ")";
                customerOptions.add(displayOption);
                customerIdMap.put(displayOption, customerId);
            }
            
            String[] options = customerOptions.toArray(new String[0]);
            
            String selectedCustomer = (String) JOptionPane.showInputDialog(
                this,
                "Select a customer to assign driver " + driverName + " to:",
                "Assign Driver to Customer",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            
            if (selectedCustomer == null) {
                return; // User canceled
            }
            
            int customerId = customerIdMap.get(selectedCustomer);
            
            // Assign driver to customer in transaction
            TransactionManager txManager = new TransactionManager("admin");
            txManager.beginTransaction();
            
            try {
                Connection conn = txManager.getConnection();
                
                // First update the driver's customer_id
                PreparedStatement pstmt1 = conn.prepareStatement(
                    "UPDATE driver SET customer_id = ?, available = 'Busy' WHERE driver_id = ?"
                );
                pstmt1.setInt(1, customerId);
                pstmt1.setString(2, driverId);
                
                int result = pstmt1.executeUpdate();
                
                if (result > 0) {
                    txManager.commitTransaction();
                    JOptionPane.showMessageDialog(
                        this,
                        "Driver " + driverName + " has been assigned to the selected customer",
                        "Assignment Successful",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    // Refresh table
                    loadDriverData();
                } else {
                    txManager.rollbackTransaction("No rows affected");
                    JOptionPane.showMessageDialog(
                        this,
                        "Failed to assign driver to customer",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (SQLException ex) {
                if (txManager.isTransactionActive()) {
                    try {
                        txManager.rollbackTransaction("Error: " + ex.getMessage());
                    } catch (SQLException rollbackEx) {
                        System.err.println("Error during rollback: " + rollbackEx.getMessage());
                    }
                }
                
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error retrieving customer data: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == submit){
            try {
                // Query drivers based on the selected brand from the new schema.
                String query = "SELECT driver_id, name, age, gender, company, brand, available, location FROM driver WHERE brand = '" + typeofcar.getSelectedItem() + "'";
                Conn conn = new Conn();
                ResultSet rs = conn.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
                
                // Apply column renderers again after model change
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            setHorizontalAlignment(JLabel.CENTER);
                            return c;
                        }
                    });
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == updateAvailability) {
            updateDriverAvailability();
        } else if (ae.getSource() == assignDriver) {
            assignDriverToCustomer();
        } else if (ae.getSource() == back) {
            setVisible(false);
            dispose(); // Just dispose this window without creating a new Reception window
        }
    }
    
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Pickup();
    }
}
