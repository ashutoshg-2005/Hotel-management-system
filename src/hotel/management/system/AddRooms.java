package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;

public class AddRooms extends JFrame {

    private JPanel contentPane;
    private JTextField roomNumberField, priceField;
    private JComboBox<String> availabilityComboBox, cleaningStatusComboBox, bedTypeComboBox, roomTypeComboBox;
    private JButton addButton, cancelButton;
    private JLabel errorLabel;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new AddRooms());
    }

    public AddRooms() {
        initializeUI();
        setupListeners();
        setVisible(true);
    }

    private void initializeUI() {
        // Frame setup
        setTitle("Hotel Management System - Add Room");
        setBounds(450, 200, 900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        // Use gradient panel for background
        contentPane = new GradientPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());
        
        // Header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(900, 60));
        JLabel headerTitle = new JLabel("ADD NEW ROOM", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        contentPane.add(headerPanel, BorderLayout.NORTH);
        
        // Main panel with form and image
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false); // Make transparent to show gradient background
        contentPane.add(mainPanel, BorderLayout.CENTER);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        formPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(20, 20, 20, 20)
        ));
        mainPanel.add(formPanel);
        
        // Room Number
        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        roomNumberLabel.setBounds(30, 20, 120, 25);
        formPanel.add(roomNumberLabel);
        
        roomNumberField = new JTextField();
        roomNumberField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomNumberField.setBounds(160, 20, 180, 25);
        formPanel.add(roomNumberField);
        
        // Room Type
        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        roomTypeLabel.setBounds(30, 60, 120, 25);
        formPanel.add(roomTypeLabel);
        
        String[] roomTypes = { "Standard", "Deluxe", "Suite", "Executive", "Presidential" };
        roomTypeComboBox = new JComboBox<>(roomTypes);
        roomTypeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomTypeComboBox.setBounds(160, 60, 180, 25);
        roomTypeComboBox.setBackground(Color.WHITE);
        formPanel.add(roomTypeComboBox);
        
        // Availability
        JLabel availabilityLabel = new JLabel("Availability:");
        availabilityLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        availabilityLabel.setBounds(30, 100, 120, 25);
        formPanel.add(availabilityLabel);
        
        String[] availabilityStatus = { "Available", "Occupied", "Reserved", "Maintenance" };
        availabilityComboBox = new JComboBox<>(availabilityStatus);
        availabilityComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        availabilityComboBox.setBounds(160, 100, 180, 25);
        availabilityComboBox.setBackground(Color.WHITE);
        formPanel.add(availabilityComboBox);
        
        // Cleaning Status
        JLabel cleaningStatusLabel = new JLabel("Cleaning Status:");
        cleaningStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cleaningStatusLabel.setBounds(30, 140, 120, 25);
        formPanel.add(cleaningStatusLabel);
        
        String[] cleaningStatus = { "Clean", "Dirty", "Cleaning in Progress" };
        cleaningStatusComboBox = new JComboBox<>(cleaningStatus);
        cleaningStatusComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cleaningStatusComboBox.setBounds(160, 140, 180, 25);
        cleaningStatusComboBox.setBackground(Color.WHITE);
        formPanel.add(cleaningStatusComboBox);
        
        // Price
        JLabel priceLabel = new JLabel("Price per Night:");
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        priceLabel.setBounds(30, 180, 120, 25);
        formPanel.add(priceLabel);
        
        priceField = new JTextField();
        priceField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        priceField.setBounds(160, 180, 180, 25);
        formPanel.add(priceField);
        
        // Bed Type
        JLabel bedTypeLabel = new JLabel("Bed Type:");
        bedTypeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        bedTypeLabel.setBounds(30, 220, 120, 25);
        formPanel.add(bedTypeLabel);
        
        String[] bedTypes = { "Single", "Double", "Queen", "King", "Twin" };
        bedTypeComboBox = new JComboBox<>(bedTypes);
        bedTypeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bedTypeComboBox.setBounds(160, 220, 180, 25);
        bedTypeComboBox.setBackground(Color.WHITE);
        formPanel.add(bedTypeComboBox);
        
        // Error label
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(30, 260, 310, 25);
        formPanel.add(errorLabel);
        
        // Buttons - use AnimatedButton instead
        addButton = new AnimatedButton("Add Room");
        addButton.setBounds(60, 300, 120, 35);
        formPanel.add(addButton);
        
        cancelButton = new AnimatedButton("Cancel");
        cancelButton.setBounds(200, 300, 120, 35);
        formPanel.add(cancelButton);
        
        // Image panel with improved styling
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        imagePanel.setBorder(new LineBorder(new Color(200, 200, 200)));
        
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/twelve.jpg"));
            Image img = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imagePanel.add(imageLabel, BorderLayout.CENTER);
            
            JPanel captionPanel = new JPanel();
            captionPanel.setBackground(new Color(45, 62, 80));
            captionPanel.setPreferredSize(new Dimension(400, 40));
            JLabel captionLabel = new JLabel("Room Management", JLabel.CENTER);
            captionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            captionLabel.setForeground(Color.WHITE);
            captionPanel.add(captionLabel);
            imagePanel.add(captionPanel, BorderLayout.NORTH);
        } catch (Exception e) {
            JLabel errorImageLabel = new JLabel("Image not found", JLabel.CENTER);
            errorImageLabel.setForeground(Color.RED);
            imagePanel.add(errorImageLabel, BorderLayout.CENTER);
        }
        mainPanel.add(imagePanel);
        
        // Status bar
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(45, 62, 80));
        statusPanel.setPreferredSize(new Dimension(900, 30));
        JLabel statusLabel = new JLabel(" Hotel Management System | Add Room Panel");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        statusLabel.setForeground(Color.WHITE);
        statusPanel.add(statusLabel, BorderLayout.WEST);
        contentPane.add(statusPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }

    private void setupListeners() {
        addButton.addActionListener(e -> addRoom());
        
        cancelButton.addActionListener(e -> {
            dispose();
        });
        
        // Listen for Enter key in room number field
        roomNumberField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addRoom();
                }
            }
        });
        
        // Listen for Enter key in price field
        priceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addRoom();
                }
            }
        });
    }

    private boolean validateForm() {
        errorLabel.setText("");
        
        // Validate room number
        if (roomNumberField.getText().trim().isEmpty()) {
            errorLabel.setText("Error: Room number cannot be empty");
            roomNumberField.requestFocus();
            return false;
        }
        
        try {
            int roomNumber = Integer.parseInt(roomNumberField.getText().trim());
            if (roomNumber <= 0) {
                errorLabel.setText("Error: Room number must be positive");
                roomNumberField.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("Error: Room number must be a valid integer");
            roomNumberField.requestFocus();
            return false;
        }
        
        // Validate price
        if (priceField.getText().trim().isEmpty()) {
            errorLabel.setText("Error: Price cannot be empty");
            priceField.requestFocus();
            return false;
        }
        
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (price <= 0) {
                errorLabel.setText("Error: Price must be greater than 0");
            priceField.requestFocus();
            return false;
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("Error: Price must be a valid number");
            priceField.requestFocus();
            return false;
        }
        
        return true;
    }

    private void addRoom() {
        if (!validateForm()) {
            return;
        }
        
        Connection connection = null;
        PreparedStatement pst = null;
        
        try {
            int roomNumber = Integer.parseInt(roomNumberField.getText().trim());
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            String availability = (String) availabilityComboBox.getSelectedItem();
            String cleaningStatus = (String) cleaningStatusComboBox.getSelectedItem();
            double price = Double.parseDouble(priceField.getText().trim());
            String bedType = (String) bedTypeComboBox.getSelectedItem();
            
            Conn c = new Conn();
            connection = c.c;
            
            // Check if room number already exists
            String checkQuery = "SELECT COUNT(*) FROM room WHERE room_number = ?";
            pst = connection.prepareStatement(checkQuery);
            pst.setInt(1, roomNumber);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Room number already exists. Please use a different room number.",
                    "Duplicate Room",
                    JOptionPane.ERROR_MESSAGE
                );
                roomNumberField.requestFocus();
                return;
            }
            
            // Close previous statement
            pst.close();
            
            // Insert new room
            String insertQuery = "INSERT INTO room (room_number, room_type, availability, cleaning_status, price, bed_type) VALUES (?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(insertQuery);
            pst.setInt(1, roomNumber);
            pst.setString(2, roomType);
            pst.setString(3, availability);
            pst.setString(4, cleaningStatus);
            pst.setDouble(5, price);
            pst.setString(6, bedType);
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Room has been added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                clearForm();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Failed to add room. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(
                this,
                "This room number already exists.",
                "Duplicate Entry",
                JOptionPane.ERROR_MESSAGE
            );
            roomNumberField.requestFocus();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Database error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "An unexpected error occurred: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void clearForm() {
        roomNumberField.setText("");
        roomTypeComboBox.setSelectedIndex(0);
        availabilityComboBox.setSelectedIndex(0);
        cleaningStatusComboBox.setSelectedIndex(0);
        priceField.setText("");
        bedTypeComboBox.setSelectedIndex(0);
        errorLabel.setText("");
        roomNumberField.requestFocus();
    }
}
