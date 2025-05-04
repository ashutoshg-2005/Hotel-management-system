package hotel.management.system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class Checkout extends JFrame implements ActionListener {
    Choice ccustomer;
    JLabel lblroomnumber, lblcheckintime, lblcheckouttime;
    JButton checkout, back, fetchDetails;

    Checkout() {
        // Modern UI setup
        setTitle("Hotel Management System - Checkout");
        setBounds(300, 200, 800, 400);
        
        // Use gradient panel for background instead of plain white
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 800, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        contentPane.add(headerPanel);
        
        JLabel headerTitle = new JLabel("CUSTOMER CHECKOUT", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Main content - adjusted Y positions to account for header
        JLabel lblid = new JLabel("Document ID");
        lblid.setBounds(30, 80, 100, 30);
        lblid.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lblid);
        
        ccustomer = new Choice();
        ccustomer.setBounds(150, 80, 180, 25);
        ccustomer.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(ccustomer);
        
        // Add Fetch Details button
        fetchDetails = new AnimatedButton("FETCH DETAILS");
        fetchDetails.setBounds(350, 80, 150, 25);
        fetchDetails.addActionListener(this);
        add(fetchDetails);
        
        try {
            Conn c = new Conn();
            // Display document numbers instead of customer_id, but store customer_id for reference
            ResultSet rs = c.s.executeQuery("SELECT customer_id, document_type, document_number FROM customer ORDER BY document_number");
            while (rs.next()) {
                String customerId = rs.getString("customer_id");
                String docType = rs.getString("document_type");
                String docNumber = rs.getString("document_number");
                ccustomer.add(docNumber + " (" + docType + ") [" + customerId + "]");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        JLabel lblroom = new JLabel("Room Number");
        lblroom.setBounds(30, 130, 120, 30);
        lblroom.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lblroom);
        
        lblroomnumber = new JLabel();
        lblroomnumber.setBounds(150, 130, 150, 30);
        lblroomnumber.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblroomnumber.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblroomnumber.setOpaque(true);
        lblroomnumber.setBackground(Color.WHITE);
        lblroomnumber.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblroomnumber);
        
        JLabel lblcheckin = new JLabel("Checkin Time");
        lblcheckin.setBounds(30, 180, 120, 30);
        lblcheckin.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lblcheckin);
        
        lblcheckintime = new JLabel();
        lblcheckintime.setBounds(150, 180, 180, 30);
        lblcheckintime.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblcheckintime.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblcheckintime.setOpaque(true);
        lblcheckintime.setBackground(Color.WHITE);
        lblcheckintime.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblcheckintime);
        
        JLabel lblcheckout = new JLabel("Checkout Time");
        lblcheckout.setBounds(30, 230, 120, 30);
        lblcheckout.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lblcheckout);
        
        Date date = new Date();
        lblcheckouttime = new JLabel("" + date);
        lblcheckouttime.setBounds(150, 230, 180, 30);
        lblcheckouttime.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblcheckouttime.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblcheckouttime.setOpaque(true);
        lblcheckouttime.setBackground(Color.WHITE);
        lblcheckouttime.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblcheckouttime);
        
        // Use AnimatedButton instead of regular JButton
        checkout = new AnimatedButton("CHECKOUT");
        checkout.setBounds(30, 300, 150, 40);
        checkout.addActionListener(this);
        add(checkout);
        
        back = new AnimatedButton("BACK");
        back.setBounds(200, 300, 150, 40);
        back.addActionListener(this);
        add(back);
        
        ccustomer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                try {
                    Conn c = new Conn();
                    String selection = ccustomer.getSelectedItem();
                    // Extract the customer_id from the selection string 
                    String customerId = selection.substring(selection.lastIndexOf("[") + 1, selection.lastIndexOf("]"));
                    
                    // Query using the extracted customer_id
                    ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE customer_id = '" + customerId + "'");
                    if (rs.next()) {
                        lblroomnumber.setText(rs.getString("room_number"));
                        lblcheckintime.setText(rs.getString("check_in_time"));
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        // Improved image handling with better scaling
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/sixth.JPG"));
        Image i5 = i4.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image1 = new JLabel(i6);
        image1.setBounds(510, 80, 400, 250);
        add(image1);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 370, 800, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        JLabel statusLabel = new JLabel("Hotel Management System | Checkout Panel");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel);
        add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchDetails) {
            // Get selected customer ID
            String selection = ccustomer.getSelectedItem();
            if (selection != null && !selection.isEmpty()) {
                // Extract the customer_id from the selection string
                String customerId = selection.substring(selection.lastIndexOf("[") + 1, selection.lastIndexOf("]"));
                updateCustomerInfo(customerId);
                JOptionPane.showMessageDialog(this, 
                    "Customer details loaded successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select a customer first", 
                    "No Selection", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else if(ae.getSource() == checkout) {
            String selection = ccustomer.getSelectedItem();
            // Extract the customer_id from the selection string
            String customerId = selection.substring(selection.lastIndexOf("[") + 1, selection.lastIndexOf("]"));
            String room = lblroomnumber.getText();
            
            // Use TransactionManager for checkout process
            TransactionManager txManager = null;
            try {
                txManager = new TransactionManager("admin");
                txManager.beginTransaction();
                
                Conn conn = new Conn();
                
                // First check if room exists and get its current version for optimistic locking
                ResultSet rsRoom = conn.executeQuery("SELECT version FROM room WHERE room_number = ?", 
                                                   Integer.parseInt(room));
                
                if (rsRoom.next()) {
                    int currentVersion = rsRoom.getInt("version");
                    
                    // FIRST - Delete any feedback records associated with this customer
                    conn.executeUpdate("DELETE FROM feedback WHERE customer_id = ?", 
                                      Integer.parseInt(customerId));
                    
                    // THEN - Delete customer (will trigger room status update via the after_customer_delete trigger)
                    int rowsDeleted = conn.executeUpdate("DELETE FROM customer WHERE customer_id = ?", 
                                                        Integer.parseInt(customerId));
                    
                    if (rowsDeleted == 0) {
                        throw new SQLException("Customer not found. May have been checked out by another user.");
                    }
                    
                    // Call stored procedure to update room with version control
                    CallableStatement cs = conn.c.prepareCall("{CALL update_room_with_version(?, ?, ?, ?, ?, ?)}");
                    cs.setInt(1, Integer.parseInt(room));
                    cs.setString(2, "Available");
                    cs.setString(3, "Dirty"); // Mark room as dirty - needs cleaning
                    
                    // Get current price and bed type to preserve them
                    ResultSet rsDetails = conn.executeQuery(
                        "SELECT price, bed_type FROM room WHERE room_number = ?", 
                        Integer.parseInt(room));
                    
                    if (rsDetails.next()) {
                        cs.setDouble(4, rsDetails.getDouble("price"));
                        cs.setString(5, rsDetails.getString("bed_type"));
                    } else {
                        // Fallback values
                        cs.setDouble(4, 0.0);
                        cs.setString(5, "Single");
                    }
                    
                    cs.setInt(6, currentVersion);
                    cs.executeQuery();
                    
                    // Commit the transaction
                    txManager.commitTransaction();
                    
                    // Show success message with transaction ID for tracking
                    JOptionPane.showMessageDialog(this, 
                        "Checkout Done Successfully\nTransaction ID: " + txManager.getTransactionId(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    setVisible(false);
                    dispose();
                    
                    // Schedule room maintenance (cursor will handle room cleaning scheduling)
                    CallableStatement maintCs = conn.c.prepareCall("{CALL schedule_room_maintenance()}");
                    maintCs.execute();
                } else {
                    throw new SQLException("Room not found: " + room);
                }
                
            } catch (SQLException ex) {
                // Handle specific types of errors
                if (ex.getMessage().contains("Optimistic lock failure")) {
                    JOptionPane.showMessageDialog(this, 
                        "Room status was changed by another user. Please refresh and try again.", 
                        "Concurrency Error", JOptionPane.ERROR_MESSAGE);
                } else if (ex.getMessage().contains("Deadlock")) {
                    JOptionPane.showMessageDialog(this, 
                        "Transaction deadlock detected. Please try again.", 
                        "Deadlock Error", JOptionPane.ERROR_MESSAGE);
                    
                    // Log the deadlock if transaction manager was initialized
                    if (txManager != null) {
                        try {
                            txManager.logDeadlock(ex.getMessage());
                        } catch (SQLException logEx) {
                            System.err.println("Error logging deadlock: " + logEx.getMessage());
                        }
                    }
                } else if (ex.getMessage().contains("foreign key constraint fails")) {
                    JOptionPane.showMessageDialog(this, 
                        "Cannot check out this customer because they have associated records. Please contact an administrator.",
                        "Constraint Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error during checkout: " + ex.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                }
                
                // Roll back transaction if it's active
                if (txManager != null && txManager.isTransactionActive()) {
                    try {
                        txManager.rollbackTransaction("Error: " + ex.getMessage());
                    } catch (SQLException rollbackEx) {
                        System.err.println("Error during rollback: " + rollbackEx.getMessage());
                    }
                }
                
                ex.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Unexpected error: " + ex.getMessage(), 
                    "System Error", JOptionPane.ERROR_MESSAGE);
                
                // Roll back transaction if it's active
                if (txManager != null && txManager.isTransactionActive()) {
                    try {
                        txManager.rollbackTransaction("Unexpected error: " + ex.getMessage());
                    } catch (SQLException rollbackEx) {
                        System.err.println("Error during rollback: " + rollbackEx.getMessage());
                    }
                }
                
                ex.printStackTrace();
            }
        } else {
            // Simply close this window without creating a new Reception window
            setVisible(false);
            dispose();
        }
    }

    private void showCustomerSearchDialog() {
        // Create search dialog
        JDialog searchDialog = new JDialog(this, "Find Customer", true);
        searchDialog.setLayout(new BorderLayout());
        searchDialog.setSize(600, 400);
        searchDialog.setLocationRelativeTo(this);
        
        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel searchLabel = new JLabel("Search by:");
        String[] searchOptions = {"Name", "Room Number", "Phone Number"};
        JComboBox<String> searchType = new JComboBox<>(searchOptions);
        JTextField searchField = new JTextField(20);
        JButton searchButton = new AnimatedButton("Search");
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchType);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Create results table
        String[] columnNames = {"ID", "Name", "Room", "Phone", "Document"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable resultsTable = new JTable(model);
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton selectButton = new AnimatedButton("Select");
        JButton cancelButton = new AnimatedButton("Cancel");
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        
        // Add panels to dialog
        searchDialog.add(searchPanel, BorderLayout.NORTH);
        searchDialog.add(scrollPane, BorderLayout.CENTER);
        searchDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Search button action
        searchButton.addActionListener(e -> {
            String searchValue = searchField.getText().trim();
            if (searchValue.isEmpty()) {
                JOptionPane.showMessageDialog(searchDialog, 
                    "Please enter a search value", 
                    "Search Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Clear previous results
            model.setRowCount(0);
            
            try {
                Conn conn = new Conn();
                ResultSet rs;
                String searchTypeStr = (String) searchType.getSelectedItem();
                
                if (searchTypeStr.equals("Name")) {
                    rs = conn.executeQuery(
                        "SELECT customer_id, name, room_number, phone, document_type, document_number " +
                        "FROM customer WHERE name LIKE ?", 
                        "%" + searchValue + "%");
                } else if (searchTypeStr.equals("Room Number")) {
                    rs = conn.executeQuery(
                        "SELECT customer_id, name, room_number, phone, document_type, document_number " +
                        "FROM customer WHERE room_number = ?", 
                        searchValue);
                } else { // Phone Number
                    rs = conn.executeQuery(
                        "SELECT customer_id, name, room_number, phone, document_type, document_number " +
                        "FROM customer WHERE phone LIKE ?", 
                        "%" + searchValue + "%");
                }
                
                boolean foundResults = false;
                while (rs.next()) {
                    foundResults = true;
                    String id = rs.getString("customer_id");
                    String name = rs.getString("name");
                    String room = rs.getString("room_number");
                    String phone = rs.getString("phone");
                    String doc = rs.getString("document_number") + " (" + rs.getString("document_type") + ")";
                    
                    model.addRow(new Object[]{id, name, room, phone, doc});
                }
                
                if (!foundResults) {
                    JOptionPane.showMessageDialog(searchDialog, 
                        "No customers found matching your search criteria", 
                        "No Results", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(searchDialog, 
                    "Error searching for customers: " + ex.getMessage(), 
                    "Search Error", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        
        // Select button action
        selectButton.addActionListener(e -> {
            int selectedRow = resultsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(searchDialog, 
                    "Please select a customer from the results", 
                    "Selection Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String customerId = model.getValueAt(selectedRow, 0).toString();
            
            // Find and select the matching item in the Choice component
            boolean found = false;
            for (int i = 0; i < ccustomer.getItemCount(); i++) {
                String item = ccustomer.getItem(i);
                if (item.contains("[" + customerId + "]")) {
                    ccustomer.select(i);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                JOptionPane.showMessageDialog(searchDialog, 
                    "Could not find customer in the main list. The list may need refreshing.", 
                    "Selection Error", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                // Trigger the item listener to update room info
                updateCustomerInfo(customerId);
                searchDialog.dispose();
            }
        });
        
        // Cancel button action
        cancelButton.addActionListener(e -> searchDialog.dispose());
        
        // Show dialog
        searchDialog.setVisible(true);
    }
    
    private void updateCustomerInfo(String customerId) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.executeQuery(
                "SELECT * FROM customer WHERE customer_id = ?", 
                Integer.parseInt(customerId));
            
            if (rs.next()) {
                lblroomnumber.setText(rs.getString("room_number"));
                lblcheckintime.setText(rs.getString("check_in_time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error updating customer information: " + e.getMessage(), 
                "Update Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Checkout();
    }
}
