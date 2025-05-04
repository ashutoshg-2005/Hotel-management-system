package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Reception extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }
        new Reception();
    }
    
    public Reception() {
        setTitle("Hotel Management System - Reception");
        setBounds(530, 200, 850, 700);
        contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // IMPORTANT FIX: Set to DISPOSE_ON_CLOSE
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Add header panel with title and back button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBounds(0, 0, 850, 50);
        headerPanel.setBackground(new Color(45, 62, 80));
        contentPane.add(headerPanel);
        
        // Add prominent Back to Dashboard button at the top
        JButton backButton = new JButton("← Back to Dashboard");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(217, 83, 79));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> closeReception());
        headerPanel.add(backButton, BorderLayout.WEST);
        
        // Add reception title
        JLabel titleLabel = new JLabel("RECEPTION", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Add and scale image - adjust Y position to account for header panel
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/fourth.jpg"));
            Image img = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setBounds(250, 70, 500, 500);  // Y position adjusted from 30 to 70
            add(imageLabel);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        
        // Create buttons - adjust Y positions to account for header panel
        AnimatedButton btnNewCustomerForm = new AnimatedButton("New Customer Form");
        btnNewCustomerForm.setBounds(10, 70, 200, 40);
        btnNewCustomerForm.addActionListener(e -> {
            new AddCustomer().setVisible(true);
        });
        contentPane.add(btnNewCustomerForm);
        
        AnimatedButton btnRoom = new AnimatedButton("Room");
        btnRoom.setBounds(10, 120, 200, 40);
        btnRoom.addActionListener(e -> {
            new Room().setVisible(true);
        });
        contentPane.add(btnRoom);
        
        AnimatedButton btnDepartment = new AnimatedButton("Department");
        btnDepartment.setBounds(10, 170, 200, 40);
        btnDepartment.addActionListener(e -> {
            new Department().setVisible(true);
        });
        contentPane.add(btnDepartment);
        
        AnimatedButton btnCheckOut = new AnimatedButton("Check Out");
        btnCheckOut.setBounds(10, 220, 200, 40);
        btnCheckOut.addActionListener(e -> {
            new Checkout().setVisible(true);
        });
        contentPane.add(btnCheckOut);
        
        AnimatedButton btnUpdateCheck = new AnimatedButton("Update Check Status");
        btnUpdateCheck.setBounds(10, 270, 200, 40);
        btnUpdateCheck.addActionListener(e -> {
            new UpdateCheck().setVisible(true);
        });
        contentPane.add(btnUpdateCheck);
        
        AnimatedButton btnUpdateRoom = new AnimatedButton("Update Room Status");
        btnUpdateRoom.setBounds(10, 320, 200, 40);
        btnUpdateRoom.addActionListener(e -> {
            new UpdateRoom().setVisible(true);
        });
        contentPane.add(btnUpdateRoom);
        
        AnimatedButton btnPickUp = new AnimatedButton("Pick up Service");
        btnPickUp.setBounds(10, 370, 200, 40);
        btnPickUp.addActionListener(e -> {
            new Pickup().setVisible(true);
        });
        contentPane.add(btnPickUp);
        
        AnimatedButton btnSearchRoom = new AnimatedButton("Search Room");
        btnSearchRoom.setBounds(10, 420, 200, 40);
        btnSearchRoom.addActionListener(e -> {
            new SearchRoom().setVisible(true);
        });
        contentPane.add(btnSearchRoom);
        
        AnimatedButton btnSummaries = new AnimatedButton("Summaries");
        btnSummaries.setBounds(10, 470, 200, 40);
        btnSummaries.addActionListener(e -> {
            new DatabaseUtilities().setVisible(true);
        });
        contentPane.add(btnSummaries);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 670, 850, 30);  // Adjusted position for added button
        statusBar.setBackground(new Color(45, 62, 80));
        JLabel statusLabel = new JLabel("Hotel Management System | Reception Panel");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel);
        contentPane.add(statusBar);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Clean method to close just this window
    private void closeReception() {
        setVisible(false);
        dispose();
    }
}

// Custom JPanel that paints a vertical gradient background
class GradientPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        Color topColor = new Color(230, 240, 255);
        Color bottomColor = Color.WHITE;
        GradientPaint gp = new GradientPaint(0, 0, topColor, 0, h, bottomColor);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}

// Custom animated button that smoothly transitions its background color on hover
class AnimatedButton extends JButton {
    private Color normalColor = new Color(45, 45, 45);
    private Color hoverColor = new Color(70, 70, 70);
    private Timer timer;
    private int animationSteps = 10;
    private int currentStep = 0;
    
    public AnimatedButton(String text) {
        super(text);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setBackground(normalColor);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animateBackground(getBackground(), hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                animateBackground(getBackground(), normalColor);
            }
        });
    }
    
    private void animateBackground(Color start, Color end) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        currentStep = 0;
        timer = new Timer(15, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentStep++;
                float ratio = (float) currentStep / animationSteps;
                int red = (int) (start.getRed() + ratio * (end.getRed() - start.getRed()));
                int green = (int) (start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
                int blue = (int) (start.getBlue() + ratio * (end.getBlue() - start.getBlue()));
                setBackground(new Color(red, green, blue));
                if (currentStep >= animationSteps) {
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}
