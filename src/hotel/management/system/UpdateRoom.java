package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class UpdateRoom extends JFrame implements ActionListener {
    Choice ccustomer;
    JTextField tfroom, tfavailable, tfstatus;
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
        JLabel lblid = new JLabel("Customer ID");
        lblid.setBounds(30, 30, 120, 25);
        lblid.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblid);

        ccustomer = new Choice();
        ccustomer.setBounds(170, 30, 140, 25);
        ccustomer.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(ccustomer);

        try {
            Conn c = new Conn();
            // Populate the choice with customer_id from the new schema
            ResultSet rs = c.s.executeQuery("SELECT customer_id FROM customer");
            while (rs.next()) {
                ccustomer.add(rs.getString("customer_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblroom = new JLabel("Room Number");
        lblroom.setBounds(30, 80, 120, 25);
        lblroom.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblroom);

        tfroom = new JTextField();
        tfroom.setBounds(170, 80, 140, 25);
        tfroom.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfroom.setBackground(Color.WHITE);
        tfroom.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        tfroom.setEditable(false); // Make read-only
        formPanel.add(tfroom);

        JLabel lblavail = new JLabel("Availability");
        lblavail.setBounds(30, 130, 120, 25);
        lblavail.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblavail);

        tfavailable = new JTextField();
        tfavailable.setBounds(170, 130, 140, 25);
        tfavailable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfavailable.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfavailable);

        JLabel lblstatus = new JLabel("Cleaning Status");
        lblstatus.setBounds(30, 180, 120, 25);
        lblstatus.setFont(new Font("SansSerif", Font.BOLD, 14));
        formPanel.add(lblstatus);

        tfstatus = new JTextField();
        tfstatus.setBounds(170, 180, 140, 25);
        tfstatus.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfstatus.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(tfstatus);
        
        // Help label
        JLabel helpLabel = new JLabel("Click CHECK to load room data first");
        helpLabel.setBounds(30, 220, 280, 20);
        helpLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        helpLabel.setForeground(new Color(100, 100, 100));
        formPanel.add(helpLabel);

        // Use AnimatedButton instead of regular JButton
        check = new AnimatedButton("CHECK");
        check.setBounds(30, 260, 90, 35);
        check.addActionListener(this);
        formPanel.add(check);

        update = new AnimatedButton("UPDATE");
        update.setBounds(130, 260, 90, 35);
        update.addActionListener(this);
        formPanel.add(update);

        back = new AnimatedButton("BACK");
        back.setBounds(230, 260, 90, 35);
        back.addActionListener(this);
        formPanel.add(back);
        
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

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == check) {
            String id = ccustomer.getSelectedItem();
            // Query using new schema: customer_id and room_number
            String query = "SELECT * FROM customer WHERE customer_id = '" + id + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    // Set room number from customer table (new schema column name: room_number)
                    tfroom.setText(rs.getString("room_number"));
                }
                // Now lookup the room details using new column names
                ResultSet rs2 = c.s.executeQuery("SELECT * FROM room WHERE room_number = '" + tfroom.getText() + "'");
                if (rs2.next()) {
                    tfavailable.setText(rs2.getString("availability"));
                    tfstatus.setText(rs2.getString("cleaning_status"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error retrieving room data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == update) {
            String id = ccustomer.getSelectedItem();
            String room = tfroom.getText();
            String available = tfavailable.getText();
            String status = tfstatus.getText();
            
            // Basic validation
            if (room.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please check customer data first", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (available.isEmpty() || status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                Conn c = new Conn();
                // Update using new column names
                c.s.executeUpdate("UPDATE room SET availability = '" + available + "', cleaning_status = '" + status + "' WHERE room_number = '" + room + "'");
                JOptionPane.showMessageDialog(this, "Room status updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new Reception();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating room status: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else { // back button
            setVisible(false);
            new Reception();
        }
    }
}
