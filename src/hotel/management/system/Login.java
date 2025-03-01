package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        initializeUI();
    }

    private void initializeUI() {

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        setLayout(null);

        JPanel gradientPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(33, 147, 176),
                        getWidth(), getHeight(), new Color(109, 213, 237));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setBounds(0, 0, 400, 300);
        gradientPanel.setLayout(null);
        add(gradientPanel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gradientPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 50, 180, 30);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gradientPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 30);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gradientPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 100, 180, 30);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gradientPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(70, 180, 100, 40);
        loginButton.setBackground(new Color(255, 87, 34));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this);
        gradientPanel.add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(220, 180, 100, 40);
        cancelButton.setBackground(new Color(97, 97, 97));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> setVisible(false));
        gradientPanel.add(cancelButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Conn c = new Conn();
            // Use a PreparedStatement to avoid SQL injection and handle parameters properly.
            String query = "SELECT * FROM login WHERE username = ? AND password = ?";
            PreparedStatement pst = c.c.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new Dashboard();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
