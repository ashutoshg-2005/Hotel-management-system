package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.*;

public class AddCustomer extends JFrame implements ActionListener {
    private JComboBox<String> comboid;
    private JTextField tfnumber, tfname, tfcountry, tfdeposit;
    private JRadioButton rmale, rfemale;
    private ButtonGroup genderGroup;
    private Choice croom;
    private JLabel checkintime, lblRoomPrice;
    private JButton btnAdd, btnBack;

    public AddCustomer() {
        // Modern UI setup
        setTitle("Hotel Management System - Add Customer");
        setBounds(300, 200, 800, 650);  // Increased height from 600 to 650
        
        // Use gradient panel for background
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 800, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        add(headerPanel);
        
        JLabel headerTitle = new JLabel("ADD NEW CUSTOMER", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Create main content panel
        JPanel formPanel = new JPanel(  );
        formPanel.setBounds(50, 80, 400, 530);
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(formPanel);
        
        // Form components with improved styling
        JLabel lblid = createLabel("ID Type", 30, 30);
        comboid = new JComboBox<>(new String[]{"Aadhar Card", "Passport", "Driving License", "Voter-ID", "Ration Card"});
        comboid.setBounds(170, 30, 180, 30);
        comboid.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboid.setBackground(Color.WHITE);
        formPanel.add(lblid);
        formPanel.add(comboid);

        JLabel lblnumber = createLabel("ID Number", 30, 80);
        tfnumber = createTextField(170, 80);
        formPanel.add(lblnumber);
        formPanel.add(tfnumber);

        JLabel lblname = createLabel("Full Name", 30, 130);
        tfname = createTextField(170, 130);
        formPanel.add(lblname);
        formPanel.add(tfname);

        JLabel lblgender = createLabel("Gender", 30, 180);
        
        JPanel genderPanel = new JPanel();
        genderPanel.setBounds(170, 180, 180, 30);
        genderPanel.setLayout(new GridLayout(1, 2));
        genderPanel.setOpaque(false);
        
        rmale = new JRadioButton("Male");
        rmale.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rmale.setOpaque(false);
        rmale.setSelected(true);
        rfemale = new JRadioButton("Female");
        rfemale.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rfemale.setOpaque(false);
        
        genderGroup = new ButtonGroup();
        genderGroup.add(rmale);
        genderGroup.add(rfemale);
        
        genderPanel.add(rmale);
        genderPanel.add(rfemale);
        
        formPanel.add(lblgender);
        formPanel.add(genderPanel);

        JLabel lblcountry = createLabel("Country", 30, 230);
        tfcountry = createTextField(170, 230);
        formPanel.add(lblcountry);
        formPanel.add(tfcountry);

        JLabel lblroom = createLabel("Room Number", 30, 280);
        JPanel roomPanel = new JPanel();
        roomPanel.setBounds(170, 280, 180, 30);
        roomPanel.setLayout(new BorderLayout());
        roomPanel.setBackground(Color.WHITE);
        roomPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        croom = new Choice();
        croom.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loadAvailableCleanRooms();
        roomPanel.add(croom, BorderLayout.CENTER);
        formPanel.add(lblroom);
        formPanel.add(roomPanel);
        
        // Add room price label
        JLabel lblPrice = createLabel("Room Price", 30, 330);
        lblRoomPrice = new JLabel("₹ 0.00");
        lblRoomPrice.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblRoomPrice.setBounds(170, 330, 180, 30);
        lblRoomPrice.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        lblRoomPrice.setOpaque(true);
        lblRoomPrice.setBackground(new Color(245, 245, 245));
        formPanel.add(lblPrice);
        formPanel.add(lblRoomPrice);
        
        // Add ItemListener to update room price when selection changes
        croom.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && croom.getSelectedItem() != null) {
                updateRoomPrice(croom.getSelectedItem());
            }
        });

        JLabel lbltime = createLabel("Check-In Time", 30, 380);
        JPanel timePanel = new JPanel();
        timePanel.setBounds(170, 380, 180, 30);
        timePanel.setLayout(new BorderLayout());
        timePanel.setBackground(new Color(245, 245, 245));
        timePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        Date currentDate = new Date();
        String formattedDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate);
        checkintime = new JLabel(formattedDate);
        checkintime.setFont(new Font("SansSerif", Font.PLAIN, 14));
        checkintime.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        timePanel.add(checkintime, BorderLayout.CENTER);
        
        formPanel.add(lbltime);
        formPanel.add(timePanel);

        JLabel lbldeposit = createLabel("Deposit", 30, 430);
        tfdeposit = createTextField(170, 430);
        formPanel.add(lbldeposit);
        formPanel.add(tfdeposit);

        btnAdd = new AnimatedButton("ADD CUSTOMER");
        btnAdd.setBounds(60, 480, 150, 35);
        btnAdd.addActionListener(this);
        formPanel.add(btnAdd);
        
        btnBack = new AnimatedButton("BACK");
        btnBack.setBounds(220, 480, 120, 35);
        btnBack.addActionListener(this);
        formPanel.add(btnBack);
        
        // Image panel with improved styling
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBounds(480, 80, 270, 470);
        imagePanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(imagePanel);
        
        // Image with improved scaling
        try {
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fifth.png"));
            Image i2 = i1.getImage().getScaledInstance(270, 470, Image.SCALE_SMOOTH);
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
        statusBar.setBounds(0, 620, 800, 30);  // Updated position from 570 to 620
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Add Customer Panel");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        // Initialize room price if there are rooms available
        if (croom.getItemCount() > 0) {
            updateRoomPrice(croom.getSelectedItem());
        }
    }

    /**
     * Loads only rooms that are both available and clean
     */
    private void loadAvailableCleanRooms() {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery(
                "SELECT room_number, price FROM room " +
                "WHERE availability = 'Available' AND cleaning_status = 'Clean' " +
                "ORDER BY room_number");
            
            while (rs.next()) {
                String roomNumber = rs.getString("room_number");
                double price = rs.getDouble("price");
                // Store room number in the choice component
                croom.add(roomNumber);
            }
            
            // Show message if no clean rooms are available
            if (croom.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, 
                    "No clean rooms are currently available.", 
                    "Room Availability", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading room data: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Updates the room price display based on selected room
     */
    private void updateRoomPrice(String roomNumber) {
        if (roomNumber == null || roomNumber.isEmpty()) {
            lblRoomPrice.setText("₹ 0.00");
            return;
        }
        
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.executeQuery(
                "SELECT price, bed_type FROM room WHERE room_number = ?", 
                Integer.parseInt(roomNumber));
            
            if (rs.next()) {
                double price = rs.getDouble("price");
                String bedType = rs.getString("bed_type");
                lblRoomPrice.setText(String.format("₹ %.2f (%s)", price, bedType));
                
                // Set minimum deposit suggestion (10% of room price)
                double minDeposit = price * 0.1;
                if (tfdeposit.getText().isEmpty()) {
                    tfdeposit.setText(String.format("%.2f", minDeposit));
                }
            } else {
                lblRoomPrice.setText("₹ 0.00");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblRoomPrice.setText("Error");
        }
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setBounds(x, y, 120, 30);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 180, 30);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        return textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            // Validate input fields
            if (tfnumber.getText().isEmpty() || tfname.getText().isEmpty() || 
                tfcountry.getText().isEmpty() || tfdeposit.getText().isEmpty() || 
                croom.getSelectedItem() == null) {
                
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String id = (String) comboid.getSelectedItem();
            String number = tfnumber.getText();
            String name = tfname.getText();
            String gender = rmale.isSelected() ? "Male" : "Female";
            String country = tfcountry.getText();
            String room = croom.getSelectedItem();
            String deposit = tfdeposit.getText();

            try {
                // Validate deposit is a valid number
                double depositAmount = Double.parseDouble(deposit);
                if (depositAmount < 0) {
                    JOptionPane.showMessageDialog(this, "Deposit amount cannot be negative", 
                        "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Initialize TransactionManager for transaction control
                TransactionManager txManager = new TransactionManager("admin");
                
                try {
                    // Begin a transaction and log it
                    txManager.beginTransaction();
                    
                    Conn conn = new Conn();
                    String checkInTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                    // Get room price for deposit validation
                    ResultSet rsRoom = conn.executeQuery("SELECT price FROM room WHERE room_number = ?", Integer.parseInt(room));
                    
                    if (rsRoom.next()) {
                        double roomPrice = rsRoom.getDouble("price");
                        
                        // Validate minimum deposit (10% of room price) - the trigger will enforce this too
                        if (depositAmount < roomPrice * 0.1) {
                            JOptionPane.showMessageDialog(this, 
                                "Deposit must be at least " + (roomPrice * 0.1) + " (10% of room price)", 
                                "Deposit Too Low", JOptionPane.WARNING_MESSAGE);
                            txManager.rollbackTransaction("Deposit too low");
                            return;
                        }
                    }

                    // Insert customer record
                    String query = "INSERT INTO customer (document_type, document_number, name, gender, country, room_number, check_in_time, deposit) " +
                                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    
                    PreparedStatement pstmt = conn.c.prepareStatement(query);
                    pstmt.setString(1, id);
                    pstmt.setString(2, number);
                    pstmt.setString(3, name);
                    pstmt.setString(4, gender);
                    pstmt.setString(5, country);
                    pstmt.setInt(6, Integer.parseInt(room));
                    pstmt.setString(7, checkInTime);
                    pstmt.setDouble(8, depositAmount);
                    
                    pstmt.executeUpdate();

                    // Get the current version of the room for optimistic concurrency control
                    ResultSet rsVersion = conn.executeQuery("SELECT version FROM room WHERE room_number = ?", Integer.parseInt(room));
                    
                    if (rsVersion.next()) {
                        int currentVersion = rsVersion.getInt("version");
                        
                        // Call the stored procedure to update room with version control
                        CallableStatement cs = conn.c.prepareCall("{CALL update_room_with_version(?, ?, ?, ?, ?, ?)}");
                        cs.setInt(1, Integer.parseInt(room));
                        cs.setString(2, "Occupied");
                        cs.setString(3, "Clean"); // Keep current cleaning status
                        
                        // Get current price and bed type
                        ResultSet rsDetails = conn.executeQuery(
                            "SELECT price, bed_type FROM room WHERE room_number = ?", 
                            Integer.parseInt(room));
                        
                        if (rsDetails.next()) {
                            cs.setDouble(4, rsDetails.getDouble("price"));
                            cs.setString(5, rsDetails.getString("bed_type"));
                        } else {
                            cs.setDouble(4, 0.0);
                            cs.setString(5, "Single");
                        }
                        
                        cs.setInt(6, currentVersion);
                        cs.executeQuery();
                    }
                    
                    // Commit the transaction
                    txManager.commitTransaction();
                    
                    JOptionPane.showMessageDialog(this, 
                        "Customer Added Successfully\nTransaction ID: " + txManager.getTransactionId(), 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    dispose();
                    
                } catch (SQLException ex) {
                    // Check for specific error types
                    if (ex.getMessage().contains("Deposit must be at least")) {
                        JOptionPane.showMessageDialog(this, 
                            "Database enforced deposit rule: " + ex.getMessage(), 
                            "Deposit Error", JOptionPane.ERROR_MESSAGE);
                    } else if (ex.getMessage().contains("Optimistic lock failure")) {
                        JOptionPane.showMessageDialog(this, 
                            "Room was updated by another user. Please try again.", 
                            "Concurrency Error", JOptionPane.ERROR_MESSAGE);
                    } else if (ex.getMessage().contains("Deadlock")) {
                        JOptionPane.showMessageDialog(this, 
                            "Transaction deadlock detected. Please try again.", 
                            "Deadlock Error", JOptionPane.ERROR_MESSAGE);
                        // Log the deadlock
                        txManager.logDeadlock(ex.getMessage());
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Error adding customer: " + ex.getMessage(), 
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    // Roll back the transaction if still active
                    if (txManager.isTransactionActive()) {
                        try {
                            txManager.rollbackTransaction("Error: " + ex.getMessage());
                        } catch (SQLException rollbackEx) {
                            System.err.println("Error during rollback: " + rollbackEx.getMessage());
                        }
                    }
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for deposit", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                    "System Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnBack) {
            setVisible(false);
            dispose(); // Just dispose this window without creating a new Reception window
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new AddCustomer());
    }
}
