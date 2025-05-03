package hotel.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.sql.*;

public class AddDrivers extends JFrame implements ActionListener {

    JPanel contentPane;
    JTextField t1, t2, t3, t4, t5;
    JComboBox<String> comboBox, comboBox_1;
    JButton b1, b2;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AddDrivers().setVisible(true);
    }

    public AddDrivers() {
        // Modern UI setup
        setTitle("Hotel Management System - Add Driver");
        setBounds(450, 200, 900, 550);
        
        // Use gradient panel for background
        contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 900, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        contentPane.add(headerPanel);
        
        JLabel headerTitle = new JLabel("ADD NEW DRIVER", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Form panel with improved styling
        JPanel formPanel = new JPanel();
        formPanel.setBounds(30, 80, 340, 420);
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        contentPane.add(formPanel);
        
        // Form components with improved styling
        JLabel l1 = new JLabel("Name");
        l1.setFont(new Font("SansSerif", Font.BOLD, 14));
        l1.setBounds(30, 30, 100, 25);
        formPanel.add(l1);
        
        t1 = new JTextField();
        t1.setBounds(150, 30, 160, 25);
        t1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(t1);
        
        JLabel l2 = new JLabel("Age");
        l2.setFont(new Font("SansSerif", Font.BOLD, 14));
        l2.setBounds(30, 70, 100, 25);
        formPanel.add(l2);
        
        t2 = new JTextField();
        t2.setBounds(150, 70, 160, 25);
        t2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(t2);
        
        JLabel l3 = new JLabel("Gender");
        l3.setFont(new Font("SansSerif", Font.BOLD, 14));
        l3.setBounds(30, 110, 100, 25);
        formPanel.add(l3);
        
        comboBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });
        comboBox.setBounds(150, 110, 160, 25);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        formPanel.add(comboBox);
        
        JLabel l4 = new JLabel("Car Company");
        l4.setFont(new Font("SansSerif", Font.BOLD, 14));
        l4.setBounds(30, 150, 100, 25);
        formPanel.add(l4);
        
        t3 = new JTextField();
        t3.setBounds(150, 150, 160, 25);
        t3.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t3.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(t3);
        
        JLabel l5 = new JLabel("Car Brand");
        l5.setFont(new Font("SansSerif", Font.BOLD, 14));
        l5.setBounds(30, 190, 100, 25);
        formPanel.add(l5);
        
        t4 = new JTextField();
        t4.setBounds(150, 190, 160, 25);
        t4.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t4.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(t4);
        
        JLabel l6 = new JLabel("Available");
        l6.setFont(new Font("SansSerif", Font.BOLD, 14));
        l6.setBounds(30, 230, 100, 25);
        formPanel.add(l6);
        
        comboBox_1 = new JComboBox<>(new String[] { "Available", "Busy" });
        comboBox_1.setBounds(150, 230, 160, 25);
        comboBox_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboBox_1.setBackground(Color.WHITE);
        formPanel.add(comboBox_1);
        
        JLabel l7 = new JLabel("Location");
        l7.setFont(new Font("SansSerif", Font.BOLD, 14));
        l7.setBounds(30, 270, 100, 25);
        formPanel.add(l7);
        
        t5 = new JTextField();
        t5.setBounds(150, 270, 160, 25);
        t5.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t5.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        formPanel.add(t5);
        
        // Help label
        JLabel helpLabel = new JLabel("All fields are required");
        helpLabel.setBounds(30, 310, 280, 20);
        helpLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        helpLabel.setForeground(new Color(100, 100, 100));
        formPanel.add(helpLabel);
        
        // Use AnimatedButton instead of regular JButton
        b1 = new AnimatedButton("ADD DRIVER");
        b1.setBounds(60, 350, 120, 35);
        b1.addActionListener(this);
        formPanel.add(b1);
        
        b2 = new AnimatedButton("BACK");
        b2.setBounds(190, 350, 100, 35);
        b2.addActionListener(this);
        formPanel.add(b2);
        
        // Image panel with improved styling
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBounds(400, 80, 470, 420);
        imagePanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        contentPane.add(imagePanel);
        
        // Image with improved scaling
        try {
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/eleven.jpg"));
            Image i2 = i1.getImage().getScaledInstance(470, 420, Image.SCALE_SMOOTH);
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
        statusBar.setBounds(0, 520, 900, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Add Driver Panel");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        contentPane.add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            try {
                // Validate input fields
                if (t1.getText().isEmpty() || t2.getText().isEmpty() || t3.getText().isEmpty() || 
                    t4.getText().isEmpty() || t5.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Validate age is a number
                int age;
                try {
                    age = Integer.parseInt(t2.getText());
                    if (age <= 0) {
                        JOptionPane.showMessageDialog(this, "Age must be a positive number", "Input Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Age must be a valid number", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                Conn c = new Conn();
                String name = t1.getText();
                String gender = (String) comboBox.getSelectedItem();
                String company  = t3.getText();
                String brand = t4.getText();
                String available = (String) comboBox_1.getSelectedItem();
                String location = t5.getText();
                
                String query = "INSERT INTO driver (name, age, gender, company, brand, available, location) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, name);
                pst.setInt(2, age);
                pst.setString(3, gender);
                pst.setString(4, company);
                pst.setString(5, brand);
                pst.setString(6, available);
                pst.setString(7, location);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "Driver Successfully Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch(Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding driver: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == b2) {
            this.dispose();
        }
    }
}
