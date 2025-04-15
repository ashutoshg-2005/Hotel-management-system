package hotel.management.system;

import javax.swing.*;
import javax.swing.border.*;
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

            try {
                Conn conn = new Conn();
                // Insert into feedback table with new schema columns
                String query = "INSERT INTO feedback (customer_id, room_number, rating, comments) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setInt(1, Integer.parseInt(customerIDStr));
                pst.setInt(2, Integer.parseInt(roomNumberStr));
                pst.setString(3, numericRating);
                pst.setString(4, comments);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Feedback Submitted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Customer ID and Room Number must be numeric", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error submitting feedback: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
