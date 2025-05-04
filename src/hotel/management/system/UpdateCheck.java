package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class UpdateCheck extends JFrame implements ActionListener {
    Choice ccustomer;
    JTextField tfroom, tfname, tfcheckin, tfpaid, tfpending; 
    JButton update, check, back;
    private TransactionManager txManager;
    
    public UpdateCheck(){
        // Modern UI setup
        setTitle("Hotel Management System - Update Check Status");
        setBounds(300, 200, 900, 500);
        
        // Use gradient panel for background
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 900, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        add(headerPanel);
        
        JLabel headerTitle = new JLabel("UPDATE CHECK STATUS", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Form panel with improved styling
        JPanel formPanel = new JPanel();
        formPanel.setBounds(30, 80, 340, 370);
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(formPanel);
        
        // Form components
        JLabel lblid = new JLabel("Document ID");
        lblid.setBounds(30, 30, 120, 25);
        lblid.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblid);
        
        ccustomer = new Choice();
        ccustomer.setBounds(170, 30, 140, 25);
        ccustomer.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(ccustomer);
        
        try {
            Conn c = new Conn();
            // Display document numbers from database instead of customer_id
            ResultSet rs = c.s.executeQuery("SELECT customer_id, document_type, document_number FROM customer ORDER BY document_number");
            while (rs.next()){
                String customerId = rs.getString("customer_id");
                String docType = rs.getString("document_type");
                String docNumber = rs.getString("document_number");
                // Store customer_id as hidden value, but show document number to user
                ccustomer.add(docNumber + " (" + docType + ") [" + customerId + "]");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        // Add ItemListener to automatically load customer data when selection changes
        ccustomer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    // Extract the customer_id from the selection string
                    String selection = ccustomer.getSelectedItem();
                    String customerId = selection.substring(selection.lastIndexOf("[") + 1, selection.lastIndexOf("]"));
                    // Load customer data automatically when dropdown selection changes
                    loadCustomerData(customerId);
                }
            }
        });
        
        JLabel lblroom = new JLabel("Room Number");
        lblroom.setBounds(30, 70, 120, 25);
        lblroom.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblroom);
        
        tfroom = new JTextField();
        tfroom.setBounds(170, 70, 140, 25);
        tfroom.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfroom.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfroom);
        
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(30, 110, 120, 25);
        lblname.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblname);
        
        tfname = new JTextField();
        tfname.setBounds(170, 110, 140, 25);
        tfname.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfname.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfname);
        
        JLabel lblcheckin = new JLabel("Checkin Time");
        lblcheckin.setBounds(30, 150, 120, 25);
        lblcheckin.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblcheckin);
        
        tfcheckin = new JTextField();
        tfcheckin.setBounds(170, 150, 140, 25);
        tfcheckin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfcheckin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfcheckin);
        
        JLabel lblpaid = new JLabel("Amount Paid");
        lblpaid.setBounds(30, 190, 120, 25);
        lblpaid.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblpaid);
        
        tfpaid = new JTextField();
        tfpaid.setBounds(170, 190, 140, 25);
        tfpaid.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfpaid.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfpaid);
        
        JLabel lblpending = new JLabel("Pending Amount");
        lblpending.setBounds(30, 230, 120, 25);
        lblpending.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblpending);
        
        tfpending = new JTextField();
        tfpending.setBounds(170, 230, 140, 25);
        tfpending.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfpending.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        tfpending.setEditable(false); // Make read-only as it's calculated
        tfpending.setBackground(new Color(245, 245, 245));
        formPanel.add(tfpending);
        
        // Help label
        JLabel helpLabel = new JLabel("Click CHECK to load customer data first");
        helpLabel.setBounds(30, 270, 280, 20);
        helpLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        helpLabel.setForeground(new Color(100, 100, 100));
        formPanel.add(helpLabel);
        
        // Use AnimatedButton instead of regular JButton
        check = new AnimatedButton("CHECK");
        check.setBounds(30, 310, 90, 35);
        check.addActionListener(this);
        formPanel.add(check);
        
        update = new AnimatedButton("UPDATE");
        update.setBounds(130, 310, 90, 35);
        update.addActionListener(this);
        formPanel.add(update); 
        
        back = new AnimatedButton("BACK");
        back.setBounds(230, 310, 90, 35);
        back.addActionListener(this);
        formPanel.add(back);
        
        // Image panel with improved styling
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBounds(400, 80, 470, 370);
        imagePanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(imagePanel);
        
        // Image with improved scaling
        try {
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/nine.jpg"));
            Image i2 = i1.getImage().getScaledInstance(470, 370, Image.SCALE_SMOOTH);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel image = new JLabel(i3);
            imagePanel.add(image, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel errorImageLabel = new JLabel("Image not found", JLabel.CENTER);
            errorImageLabel.setForeground(Color.RED);
            imagePanel.add(errorImageLabel, BorderLayout.CENTER);
        }
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 470, 900, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Update Check Status Panel");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == check){
            String id = ccustomer.getSelectedItem();
            // Extract the customer_id from the selection string
            String customerId = id.substring(id.lastIndexOf("[") + 1, id.lastIndexOf("]"));
            // Query using new schema column: customer_id
            String query = "SELECT * FROM customer WHERE customer_id = '" + customerId + "'";
            try{
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                if(rs.next()){
                    // Use new column names: room_number, name, check_in_time, deposit
                    tfroom.setText(rs.getString("room_number"));
                    tfname.setText(rs.getString("name"));
                    tfcheckin.setText(rs.getString("check_in_time"));
                    tfpaid.setText(rs.getString("deposit"));
                    
                    // Query the room table with new column name: room_number
                    ResultSet rs2 = c.s.executeQuery("SELECT * FROM room WHERE room_number = '" + tfroom.getText() + "'");
                    if(rs2.next()){
                        String priceStr = rs2.getString("price");
                        double price = Double.parseDouble(priceStr);
                        double paid = Double.parseDouble(tfpaid.getText());
                        double pendingAmount = price - paid;
                        tfpending.setText(String.format("%.2f", pendingAmount)); // Format to 2 decimal places
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No customer found with ID: " + customerId, "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            } catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error retrieving data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if(ae.getSource() == update){
            String id = ccustomer.getSelectedItem();
            // Extract the customer_id from the selection string
            String customerId = id.substring(id.lastIndexOf("[") + 1, id.lastIndexOf("]"));
            String room = tfroom.getText();
            String name = tfname.getText();
            String checkin = tfcheckin.getText();
            String deposit = tfpaid.getText();
            
            // Basic validation
            if (room.isEmpty() || name.isEmpty() || checkin.isEmpty() || deposit.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please check customer data first or ensure all fields are filled", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                // Validate deposit is a number
                double depositAmount = Double.parseDouble(deposit);
                if (depositAmount < 0) {
                    JOptionPane.showMessageDialog(this, "Deposit amount cannot be negative", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Initialize transaction manager and begin transaction
                txManager = new TransactionManager("admin");
                String txId = txManager.beginTransaction();
                
                // Create a new connection that will be managed by the transaction manager
                // instead of using a separate connection
                Connection conn = txManager.getConnection();
                
                // First verify the customer still exists (check for concurrent deletion)
                PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT customer_id FROM customer WHERE customer_id = ?"
                );
                checkStmt.setString(1, customerId);
                ResultSet checkRs = checkStmt.executeQuery();
                
                if (!checkRs.next()) {
                    if (txManager.isTransactionActive()) {
                        txManager.rollbackTransaction("Customer no longer exists");
                    }
                    JOptionPane.showMessageDialog(this, "The customer record no longer exists. It may have been deleted by another user.", 
                        "Record Not Found", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Update customer using prepared statement to avoid SQL injection
                PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE customer SET room_number = ?, name = ?, check_in_time = ?, deposit = ? WHERE customer_id = ?"
                );
                pstmt.setString(1, room);
                pstmt.setString(2, name);
                pstmt.setString(3, checkin);
                pstmt.setString(4, deposit);
                pstmt.setString(5, customerId);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    // Commit the transaction
                    txManager.commitTransaction();
                    
                    // Verify the update by reading back from database
                    PreparedStatement verifyStmt = conn.prepareStatement(
                        "SELECT deposit FROM customer WHERE customer_id = ?"
                    );
                    verifyStmt.setString(1, customerId);
                    ResultSet verifyRs = verifyStmt.executeQuery();
                    
                    if (verifyRs.next()) {
                        String updatedDeposit = verifyRs.getString("deposit");
                        System.out.println("Verified update - New deposit: " + updatedDeposit);
                    }
                    
                    JOptionPane.showMessageDialog(this, 
                        "Customer check status updated successfully\nTransaction ID: " + txManager.getTransactionId(), 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    dispose();
                } else {
                    // If no rows were affected, roll back the transaction
                    if (txManager.isTransactionActive()) {
                        txManager.rollbackTransaction("No rows updated");
                    }
                    
                    JOptionPane.showMessageDialog(this, 
                        "Failed to update customer. Please try again.", 
                        "Update Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Deposit must be a valid number", "Input Error", JOptionPane.WARNING_MESSAGE);
            } catch(SQLException ex) {
                // Handle specific SQL exceptions
                if (txManager != null && txManager.isTransactionActive()) {
                    try {
                        if (ex.getMessage().contains("Deadlock")) {
                            txManager.logDeadlock(ex.getMessage());
                            JOptionPane.showMessageDialog(this,
                                "Transaction deadlock detected. Please try again.",
                                "Deadlock Error", JOptionPane.ERROR_MESSAGE);
                        } else if (ex.getMessage().contains("Optimistic lock failure")) {
                            JOptionPane.showMessageDialog(this,
                                "This record was modified by another user. Please refresh and try again.",
                                "Concurrent Modification", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, 
                                "Database error: " + ex.getMessage(), 
                                "SQL Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                        txManager.rollbackTransaction("SQL error: " + ex.getMessage());
                    } catch (SQLException rollbackEx) {
                        System.err.println("Error during rollback: " + rollbackEx.getMessage());
                    }
                }
                ex.printStackTrace();
            } catch(Exception e){
                // Roll back the transaction for any other exceptions
                if (txManager != null && txManager.isTransactionActive()) {
                    try {
                        txManager.rollbackTransaction("Error: " + e.getMessage());
                    } catch (SQLException rollbackEx) {
                        System.err.println("Error during rollback: " + rollbackEx.getMessage());
                    }
                }
                
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            setVisible(false);
            dispose(); // Just dispose this window without creating a new Reception window
        }
    }
    
    private void loadCustomerData(String customerId) {
        String query = "SELECT * FROM customer WHERE customer_id = '" + customerId + "'";
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                tfroom.setText(rs.getString("room_number"));
                tfname.setText(rs.getString("name"));
                tfcheckin.setText(rs.getString("check_in_time"));
                tfpaid.setText(rs.getString("deposit"));
                
                ResultSet rs2 = c.s.executeQuery("SELECT * FROM room WHERE room_number = '" + tfroom.getText() + "'");
                if (rs2.next()) {
                    String priceStr = rs2.getString("price");
                    double price = Double.parseDouble(priceStr);
                    double paid = Double.parseDouble(tfpaid.getText());
                    double pendingAmount = price - paid;
                    tfpending.setText(String.format("%.2f", pendingAmount));
                }
            } else {
                JOptionPane.showMessageDialog(this, "No customer found with ID: " + customerId, "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new UpdateCheck();
    }
}
