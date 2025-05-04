package hotel.management.system;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FeedbackForm extends JFrame implements ActionListener {
    JTextField tfCustomerID, tfRoomNumber;
    JTextArea taComments;
    JComboBox<String> cbRating;
    JButton submit, back;

    public FeedbackForm() {
        // Modern UI setup
        setTitle("Hotel Management System - Feedback Form");
        setBounds(400, 200, 700, 500);
        
        // Use gradient panel for background instead of plain white
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 700, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        add(headerPanel);
        
        JLabel headerTitle = new JLabel("CUSTOMER FEEDBACK", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Form panel with improved styling
        JPanel formPanel = new JPanel();
        formPanel.setBounds(50, 80, 380, 350);
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(formPanel);
        
        // Label and text field for Customer ID
        JLabel lblCustomerID = new JLabel("Customer ID:");
        lblCustomerID.setBounds(30, 30, 100, 25);
        lblCustomerID.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblCustomerID);

        tfCustomerID = new JTextField();
        tfCustomerID.setBounds(160, 30, 180, 25);
        tfCustomerID.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfCustomerID.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfCustomerID);

        // Label and text field for Room Number
        JLabel lblRoomNumber = new JLabel("Room No:");
        lblRoomNumber.setBounds(30, 70, 100, 25);
        lblRoomNumber.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblRoomNumber);

        tfRoomNumber = new JTextField();
        tfRoomNumber.setBounds(160, 70, 180, 25);
        tfRoomNumber.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfRoomNumber.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfRoomNumber);

        // Rating combobox with improved styling
        JLabel lblRating = new JLabel("Rating:");
        lblRating.setBounds(30, 110, 100, 25);
        lblRating.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblRating);

        String[] ratings = {"Excellent", "Good", "Average", "Poor"};
        cbRating = new JComboBox<>(ratings);
        cbRating.setBounds(160, 110, 180, 25);
        cbRating.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cbRating.setBackground(Color.WHITE);
        formPanel.add(cbRating);

        // Comments text area with scroll pane
        JLabel lblComments = new JLabel("Comments:");
        lblComments.setBounds(30, 150, 100, 25);
        lblComments.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblComments);

        taComments = new JTextArea();
        taComments.setFont(new Font("SansSerif", Font.PLAIN, 14));
        taComments.setLineWrap(true);
        taComments.setWrapStyleWord(true);
        taComments.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        JScrollPane scrollPane = new JScrollPane(taComments);
        scrollPane.setBounds(160, 150, 180, 100);
        formPanel.add(scrollPane);

        // Use AnimatedButton instead of regular JButtons
        submit = new AnimatedButton("SUBMIT");
        submit.setBounds(70, 280, 120, 35);
        submit.addActionListener(this);
        formPanel.add(submit);

        back = new AnimatedButton("BACK");
        back.setBounds(200, 280, 120, 35);
        back.addActionListener(this);
        formPanel.add(back);
        
        // Add image on right side with improved scaling
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/fifth.png"));
            Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setBounds(450, 120, 220, 220);
            add(imageLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 470, 700, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Feedback Form", JLabel.LEFT);
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            // Retrieve input values
            String customerIDStr = tfCustomerID.getText();
            String roomNumberStr = tfRoomNumber.getText();
            String ratingStr = (String) cbRating.getSelectedItem();
            String comments = taComments.getText();

            // Validate input fields
            if(customerIDStr.isEmpty() || roomNumberStr.isEmpty() || comments.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Map rating text to a numeric value as a string (matching ENUM values)
            String numericRating;
            switch(ratingStr) {
                case "Excellent": numericRating = "5"; break;
                case "Good": numericRating = "4"; break;
                case "Average": numericRating = "3"; break;
                default: numericRating = "2"; break; // Poor
            }

            // Use TransactionManager for feedback submission
            TransactionManager txManager = null;
            
            try {
                // Parse customer ID and room number
                int customerID = Integer.parseInt(customerIDStr);
                int roomNumber = Integer.parseInt(roomNumberStr);
                
                txManager = new TransactionManager("front_desk");
                txManager.beginTransaction();
                
                Conn conn = new Conn();
                
                // First verify that the customer exists and is in the specified room
                ResultSet rs = conn.executeQuery(
                    "SELECT * FROM customer WHERE customer_id = ? AND room_number = ?", 
                    customerID, roomNumber
                );
                
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(
                        this, 
                        "No matching customer found for this ID and room number.", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    
                    // Rollback the transaction
                    if (txManager.isTransactionActive()) {
                        txManager.rollbackTransaction("No matching customer found");
                    }
                    return;
                }
                
                // Insert into feedback table
                int result = conn.executeUpdate(
                    "INSERT INTO feedback (customer_id, room_number, rating, comments) VALUES (?, ?, ?, ?)",
                    customerID, roomNumber, numericRating, comments
                );
                
                if (result > 0) {
                    // Commit the transaction
                    txManager.commitTransaction();
                    
                    JOptionPane.showMessageDialog(
                        this, 
                        "Feedback Submitted Successfully\nTransaction ID: " + txManager.getTransactionId(), 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    setVisible(false);
                } else {
                    // Rollback if no rows were affected
                    if (txManager.isTransactionActive()) {
                        txManager.rollbackTransaction("Failed to insert feedback");
                    }
                    
                    JOptionPane.showMessageDialog(
                        this, 
                        "Failed to submit feedback. Please try again.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Customer ID and Room Number must be numeric", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (SQLException ex) {
                // Rollback the transaction
                if (txManager != null && txManager.isTransactionActive()) {
                    try {
                        txManager.rollbackTransaction("SQL error: " + ex.getMessage());
                    } catch (SQLException rollbackEx) {
                        System.err.println("Error during rollback: " + rollbackEx.getMessage());
                    }
                }
                
                // Handle specific SQL errors
                if (ex.getMessage().contains("foreign key constraint fails")) {
                    JOptionPane.showMessageDialog(
                        this,
                        "The customer ID or room number is invalid.",
                        "Foreign Key Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } else if (ex.getMessage().contains("Deadlock")) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Transaction deadlock detected. Please try again.",
                        "Deadlock Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    
                    // Log the deadlock
                    if (txManager != null) {
                        try {
                            txManager.logDeadlock(ex.getMessage());
                        } catch (SQLException logEx) {
                            System.err.println("Error logging deadlock: " + logEx.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Database error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
                ex.printStackTrace();
            } catch (Exception ex) {
                // Rollback the transaction
                if (txManager != null && txManager.isTransactionActive()) {
                    try {
                        txManager.rollbackTransaction("Unexpected error: " + ex.getMessage());
                    } catch (SQLException rollbackEx) {
                        System.err.println("Error during rollback: " + rollbackEx.getMessage());
                    }
                }
                
                JOptionPane.showMessageDialog(
                    this,
                    "An unexpected error occurred: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new FeedbackForm();
    }
}
