package hotel.management.system;

import java.awt.*;

import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.regex.Pattern;

public class AddEmployee extends JFrame {

    private JTextField nameField, ageField, salaryField, phoneField, aadharField, emailField;
    private JComboBox<String> jobComboBox;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JButton saveButton, cancelButton;
    private JLabel errorLabel;
    
    // Regular expressions for validation
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String PHONE_REGEX = "^[0-9]{10}$";
    private static final String AADHAR_REGEX = "^[0-9]{12}$";
    
    public AddEmployee() {
        initializeUI();
        setupEventListeners();
    }
    
    private void initializeUI() {
        // Frame setup
        setTitle("Add Employee Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        
        // Set custom look
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(new BorderLayout());
        
        // Create main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));
        add(mainPanel);
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel headerLabel = new JLabel("Add Employee Details");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(33, 106, 224));
        headerLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        
        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = createFormLabel("Full Name:");
        formPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        nameField = createFormTextField();
        formPanel.add(nameField, gbc);
        
        // Age field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel ageLabel = createFormLabel("Age:");
        formPanel.add(ageLabel, gbc);
        
        gbc.gridx = 1;
        ageField = createFormTextField();
        formPanel.add(ageField, gbc);
        
        // Gender field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel genderLabel = createFormLabel("Gender:");
        formPanel.add(genderLabel, gbc);
        
        gbc.gridx = 1;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.setOpaque(false);
        
        maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setSelected(true);
        maleRadioButton.setFont(new Font("Arial", Font.PLAIN, 14));
        maleRadioButton.setOpaque(false);
        
        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setFont(new Font("Arial", Font.PLAIN, 14));
        femaleRadioButton.setOpaque(false);
        
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        
        genderPanel.add(maleRadioButton);
        genderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        genderPanel.add(femaleRadioButton);
        formPanel.add(genderPanel, gbc);
        
        // Job field
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel jobLabel = createFormLabel("Job Position:");
        formPanel.add(jobLabel, gbc);
        
        gbc.gridx = 1;
        String[] jobs = {
            "Front Desk Clerk", 
            "Porter", 
            "Housekeeping Staff", 
            "Kitchen Staff", 
            "Room Service Staff", 
            "Waiter/Waitress", 
            "Restaurant Manager", 
            "Hotel Manager", 
            "Accountant", 
            "Chef"
        };
        jobComboBox = new JComboBox<>(jobs);
        jobComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        jobComboBox.setBackground(Color.WHITE);
        formPanel.add(jobComboBox, gbc);
        
        // Salary field
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel salaryLabel = createFormLabel("Salary (₹):");
        formPanel.add(salaryLabel, gbc);
        
        gbc.gridx = 1;
        salaryField = createFormTextField();
        formPanel.add(salaryField, gbc);
        
        // Phone field
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel phoneLabel = createFormLabel("Phone Number:");
        formPanel.add(phoneLabel, gbc);
        
        gbc.gridx = 1;
        phoneField = createFormTextField();
        formPanel.add(phoneField, gbc);
        
        // Aadhar field
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel aadharLabel = createFormLabel("Aadhar Number:");
        formPanel.add(aadharLabel, gbc);
        
        gbc.gridx = 1;
        aadharField = createFormTextField();
        formPanel.add(aadharField, gbc);
        
        // Email field
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel emailLabel = createFormLabel("Email Address:");
        formPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        emailField = createFormTextField();
        formPanel.add(emailField, gbc);
        
        // Error label
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        errorLabel.setForeground(Color.RED);
        formPanel.add(errorLabel, gbc);
        
        // Button panel
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        
        saveButton = new JButton("Save Employee");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(66, 139, 202));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(204, 204, 204));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        formPanel.add(buttonPanel, gbc);
        
        // Add form to the left side
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add image to the right side
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(350, 0));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/tenth.jpg"));
            Image img = icon.getImage().getScaledInstance(350, 400, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imagePanel.add(imageLabel, BorderLayout.CENTER);
            
            JLabel captionLabel = new JLabel("Employee Registration", JLabel.CENTER);
            captionLabel.setFont(new Font("Arial", Font.BOLD, 16));
            captionLabel.setBorder(new EmptyBorder(15, 0, 15, 0));
            captionLabel.setForeground(new Color(33, 106, 224));
            imagePanel.add(captionLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            JLabel errorImageLabel = new JLabel("Image not found", JLabel.CENTER);
            errorImageLabel.setForeground(Color.RED);
            imagePanel.add(errorImageLabel, BorderLayout.CENTER);
        }
        
        mainPanel.add(imagePanel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }
    
    private JTextField createFormTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(204, 204, 204), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }
    
    private void setupEventListeners() {
        saveButton.addActionListener(e -> saveEmployee());
        
        cancelButton.addActionListener(e -> {
            dispose();
        });
    }
    
    private boolean validateForm() {
        errorLabel.setText(" ");
        
        // Check empty fields
        if (nameField.getText().trim().isEmpty()) {
            errorLabel.setText("Error: Name cannot be empty");
            nameField.requestFocus();
            return false;
        }
        
        // Validate age
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            if (age < 18 || age > 65) {
                errorLabel.setText("Error: Age must be between 18 and 65");
                ageField.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("Error: Age must be a valid number");
            ageField.requestFocus();
            return false;
        }
        
        // Validate salary
        try {
            double salary = Double.parseDouble(salaryField.getText().trim());
            if (salary <= 0) {
                errorLabel.setText("Error: Salary must be greater than 0");
                salaryField.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("Error: Salary must be a valid number");
            salaryField.requestFocus();
            return false;
        }
        
        // Validate phone
        if (!Pattern.matches(PHONE_REGEX, phoneField.getText().trim())) {
            errorLabel.setText("Error: Phone number must be 10 digits");
            phoneField.requestFocus();
            return false;
        }
        
        // Validate aadhar
        if (!Pattern.matches(AADHAR_REGEX, aadharField.getText().trim())) {
            errorLabel.setText("Error: Aadhar number must be 12 digits");
            aadharField.requestFocus();
            return false;
        }
        
        // Validate email
        if (!emailField.getText().trim().isEmpty() && !Pattern.matches(EMAIL_REGEX, emailField.getText().trim())) {
            errorLabel.setText("Error: Invalid email format");
            emailField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void saveEmployee() {
        if (!validateForm()) {
            return;
        }
        
        // Get form data
        String name = nameField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";
        String job = (String) jobComboBox.getSelectedItem();
        double salary = Double.parseDouble(salaryField.getText().trim());
        String phone = phoneField.getText().trim();
        String aadhar = aadharField.getText().trim();
        String email = emailField.getText().trim();
        
        Connection connection = null;
        PreparedStatement pst = null;
        
        try {
            Conn c = new Conn();
            connection = c.c;
            
            String query = "INSERT INTO employee (name, age, gender, job, salary, phone, aadhar, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(query);
            
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, gender);
            pst.setString(4, job);
            pst.setDouble(5, salary);
            pst.setString(6, phone);
            pst.setString(7, aadhar);
            pst.setString(8, email);
            
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(
                this,
                "Employee added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            clearForm();
            
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(
                this,
                "This employee already exists or duplicate information provided.",
                "Duplicate Entry",
                JOptionPane.ERROR_MESSAGE
            );
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
        nameField.setText("");
        ageField.setText("");
        maleRadioButton.setSelected(true);
        jobComboBox.setSelectedIndex(0);
        salaryField.setText("");
        phoneField.setText("");
        aadharField.setText("");
        emailField.setText("");
        errorLabel.setText(" ");
        nameField.requestFocus();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new AddEmployee());
    }
}
