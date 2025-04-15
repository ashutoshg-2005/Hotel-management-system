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
    private JLabel checkintime;
    private JButton btnAdd, btnBack;

    public AddCustomer() {
        // Modern UI setup
        setTitle("Hotel Management System - Add Customer");
        setBounds(300, 200, 800, 600);
        
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
        JPanel formPanel = new JPanel();
        formPanel.setBounds(50, 80, 400, 470);
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
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT room_number FROM room WHERE availability = 'Available'");
            while (rs.next()) {
                croom.add(rs.getString("room_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        roomPanel.add(croom, BorderLayout.CENTER);
        formPanel.add(lblroom);
        formPanel.add(roomPanel);

        JLabel lbltime = createLabel("Check-In Time", 30, 330);
        JPanel timePanel = new JPanel();
        timePanel.setBounds(170, 330, 180, 30);
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

        JLabel lbldeposit = createLabel("Deposit", 30, 380);
        tfdeposit = createTextField(170, 380);
        formPanel.add(lbldeposit);
        formPanel.add(tfdeposit);

        btnAdd = new AnimatedButton("ADD CUSTOMER");
        btnAdd.setBounds(60, 430, 150, 35);
        btnAdd.addActionListener(this);
        formPanel.add(btnAdd);
        
        btnBack = new AnimatedButton("BACK");
        btnBack.setBounds(220, 430, 120, 35);
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
        statusBar.setBounds(0, 570, 800, 30);
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
                
                Conn conn = new Conn();
                String checkInTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

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

                PreparedStatement updateRoom = conn.c.prepareStatement("UPDATE room SET availability = 'Occupied' WHERE room_number = ?");
                updateRoom.setInt(1, Integer.parseInt(room));
                updateRoom.executeUpdate();

                JOptionPane.showMessageDialog(this, "Customer Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new Reception();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for deposit", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding customer: " + ex.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnBack) {
            dispose();
            new Reception();
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
