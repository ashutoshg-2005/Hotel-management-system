package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    public Login() {
        initializeUI();
    }

    private void initializeUI() {
        // Set up window properties
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hotel Management System - Login");
        setLayout(null);
        setResizable(false);

        // Create gradient background panel
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(33, 147, 176),
                        getWidth(), getHeight(), new Color(109, 213, 237));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setBounds(0, 0, 400, 300);
        gradientPanel.setLayout(null);
        add(gradientPanel);

        // Add hotel management system title
        JLabel titleLabel = new JLabel("Hotel Management System");
        titleLabel.setBounds(50, 10, 300, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gradientPanel.add(titleLabel);

        // Username input
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 70, 100, 30);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gradientPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 70, 180, 30);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gradientPanel.add(usernameField);

        // Password input
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 120, 100, 30);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gradientPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 120, 180, 30);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        // Add enter key listener to submit login
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    authenticateUser();
                }
            }
        });
        gradientPanel.add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(70, 180, 100, 40);
        loginButton.setBackground(new Color(255, 87, 34));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 87, 34).darker(), 1),
                BorderFactory.createEmptyBorder(4, 14, 4, 14)));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this);
        gradientPanel.add(loginButton);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 180, 100, 40);
        cancelButton.setBackground(new Color(97, 97, 97));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(97, 97, 97).darker(), 1),
                BorderFactory.createEmptyBorder(4, 14, 4, 14)));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> System.exit(0));
        gradientPanel.add(cancelButton);

        // Add bottom label with copyright info
        JLabel copyrightLabel = new JLabel("© " + java.time.Year.now().getValue() + " Hotel Management System");
        copyrightLabel.setBounds(0, 240, 400, 20);
        copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        copyrightLabel.setForeground(new Color(255, 255, 255, 180));
        copyrightLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        gradientPanel.add(copyrightLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            authenticateUser();
        }
    }
    
    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        // Basic input validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username and password cannot be empty", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String userRole = "admin"; // Default role assumption
        
        try {
            Conn c = new Conn();
            connection = c.c;
            
            // Use a PreparedStatement to avoid SQL injection
            String query = "SELECT * FROM login WHERE username = ? AND password = ?";
            pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password); // In a real app, passwords should be hashed
            
            rs = pst.executeQuery();

            if (rs.next()) {
                // Check if this username belongs to an employee and determine their role
                PreparedStatement pstEmp = connection.prepareStatement(
                    "SELECT job FROM employee WHERE username = ?");
                pstEmp.setString(1, username);
                ResultSet empRs = pstEmp.executeQuery();
                
                if (empRs.next()) {
                    userRole = empRs.getString("job");
                } else if ("admin".equals(username)) {
                    userRole = "admin";
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Login Successful", 
                    "Welcome " + username, 
                    JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                
                // Create Dashboard with appropriate role and make it visible
                Dashboard dashboard = new Dashboard(userRole);
                dashboard.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid username or password", 
                    "Authentication Failed", 
                    JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Database error: " + ex.getMessage(), 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "An unexpected error occurred: " + ex.getMessage(), 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                // We don't close the connection here as it's managed by Conn class
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(Login::new);
    }
}
