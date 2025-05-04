package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateRoom extends JFrame implements ActionListener {
    Choice croom;
    JTextField tfavailable, tfstatus;
    JButton check, update, back;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new UpdateRoom();
    }

    public UpdateRoom() {
        // Modern UI setup
        setTitle("Hotel Management System - Update Room Status");
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
        
        JLabel headerTitle = new JLabel("UPDATE ROOM STATUS", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Form panel with improved styling
        JPanel formPanel = new JPanel();
        formPanel.setBounds(30, 80, 340, 350);
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(formPanel);
        
        // Form components
        JLabel lblroom = new JLabel("Room Number");
        lblroom.setBounds(30, 30, 120, 25);
        lblroom.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblroom);

        croom = new Choice();
        croom.setBounds(170, 30, 140, 25);
        croom.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(croom);

        try {
            Conn c = new Conn();
            // Populate the choice with room numbers from the room table
            ResultSet rs = c.s.executeQuery("SELECT room_number FROM room ORDER BY room_number");
            while (rs.next()) {
                croom.add(rs.getString("room_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblavail = new JLabel("Availability");
        lblavail.setBounds(30, 80, 120, 25);
        lblavail.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblavail);

        tfavailable = new JTextField();
        tfavailable.setBounds(170, 80, 140, 25);
        tfavailable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfavailable.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfavailable);

        JLabel lblstatus = new JLabel("Cleaning Status");
        lblstatus.setBounds(30, 130, 120, 25);
        lblstatus.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblstatus);

        tfstatus = new JTextField();
        tfstatus.setBounds(170, 130, 140, 25);
        tfstatus.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfstatus.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfstatus);
        
        // Add ItemListener to automatically load room data when selection changes
        croom.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    // Load room data automatically when dropdown selection changes
                    loadRoomData(croom.getSelectedItem());
                }
            }
        });
        
        // Help label
        JLabel helpLabel = new JLabel("Room data loads automatically");
        helpLabel.setBounds(30, 180, 280, 20);
        helpLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        helpLabel.setForeground(new Color(100, 100, 100));
        formPanel.add(helpLabel);

        // Use AnimatedButton instead of regular JButton
        check = new AnimatedButton("CHECK");
        check.setBounds(30, 220, 90, 35);
        check.addActionListener(this);
        formPanel.add(check);

        update = new AnimatedButton("UPDATE");
        update.setBounds(130, 220, 90, 35);
        update.addActionListener(this);
        formPanel.add(update);

        back = new AnimatedButton("BACK");
        back.setBounds(230, 220, 90, 35);
        back.addActionListener(this);
        formPanel.add(back);
        
        // Load initial room data
        if (croom.getItemCount() > 0) {
            loadRoomData(croom.getItem(0));
        }
        
        // Image panel with improved styling
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBounds(400, 80, 470, 350);
        imagePanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(imagePanel);
        
        // Image with improved scaling
        try {
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/seventh.jpg"));
            Image i2 = i1.getImage().getScaledInstance(470, 350, Image.SCALE_SMOOTH);
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
        JLabel statusLabel = new JLabel(" Hotel Management System | Update Room Status Panel");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void loadRoomData(String roomNumber) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM room WHERE room_number = '" + roomNumber + "'");
            if (rs.next()) {
                tfavailable.setText(rs.getString("availability"));
                tfstatus.setText(rs.getString("cleaning_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving room data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String room = croom.getSelectedItem();
            String available = tfavailable.getText();
            String status = tfstatus.getText();
            
            // Basic validation
            if (room.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a room", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (available.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate availability format
            if (!available.equals("Available") && !available.equals("Occupied")) {
                JOptionPane.showMessageDialog(this, "Availability must be 'Available' or 'Occupied'", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate cleaning status format
            if (!status.equals("Clean") && !status.equals("Dirty") && !status.equals("In Progress")) {
                JOptionPane.showMessageDialog(this, "Cleaning Status must be 'Clean', 'Dirty', or 'In Progress'", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Use TransactionManager for room update with optimistic locking
            TransactionManager txManager = null;
            try {
                txManager = new TransactionManager("admin");
                txManager.beginTransaction();
                
                Conn conn = new Conn();
                
                // Get current room details including version for optimistic locking
                ResultSet rs = conn.executeQuery("SELECT price, bed_type, version FROM room WHERE room_number = ?", 
                                              Integer.parseInt(room));
                
                if (rs.next()) {
                    int currentVersion = rs.getInt("version");
                    double price = rs.getDouble("price");
                    String bedType = rs.getString("bed_type");
                    
                    // Call stored procedure to update with optimistic locking instead of direct SQL
                    CallableStatement cs = conn.c.prepareCall("{CALL update_room_with_version(?, ?, ?, ?, ?, ?)}");
                    cs.setInt(1, Integer.parseInt(room));
                    cs.setString(2, available);
                    cs.setString(3, status);
                    cs.setDouble(4, price);
                    cs.setString(5, bedType);
                    cs.setInt(6, currentVersion);
                    
                    // Execute and get new version
                    ResultSet rsUpdate = cs.executeQuery();
                    if (rsUpdate.next()) {
                        int newVersion = rsUpdate.getInt("version");
                        System.out.println("Room " + room + " updated to version " + newVersion);
                    }
                    
                    // Commit the transaction
                    txManager.commitTransaction();
                    
                    JOptionPane.showMessageDialog(this, 
                        "Room status updated successfully\nTransaction ID: " + txManager.getTransactionId(), 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    dispose();
                } else {
                    throw new SQLException("Room not found: " + room);
                }
            } catch (SQLException ex) {
                // Handle specific error types
                if (ex.getMessage().contains("Optimistic lock failure")) {
                    JOptionPane.showMessageDialog(this, 
                        "Room was updated by another user. Please try again with fresh data.", 
                        "Concurrency Error", JOptionPane.ERROR_MESSAGE);
                } else if (ex.getMessage().contains("Lock wait timeout")) {
                    JOptionPane.showMessageDialog(this, 
                        "Database is busy. Please try again in a moment.", 
                        "Timeout Error", JOptionPane.ERROR_MESSAGE);
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
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error updating room status: " + ex.getMessage(), 
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
        } else if (ae.getSource() == check) {
            // Just load the current room data
            loadRoomData(croom.getSelectedItem());
        } else { // back button
            setVisible(false);
            dispose();
        }
    }
}
