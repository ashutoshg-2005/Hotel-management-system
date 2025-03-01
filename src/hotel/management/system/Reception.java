package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Reception extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        // Set a modern look and feel (Nimbus in this example)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        new Reception();
    }
    
    public Reception() {
        setTitle("Hotel Management System - Reception");
        // Adjusted height to accommodate all buttons
        setBounds(530, 200, 850, 650);
        // Use a custom gradient panel for a modern background effect
        contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add and scale image
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/fourth.jpg"));
        Image img = icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(250, 30, 500, 500);
        add(imageLabel);
        
        // Create buttons with modern animated effects using our custom AnimatedButton class
        AnimatedButton btnNewCustomerForm = new AnimatedButton("New Customer Form");
        btnNewCustomerForm.setBounds(10, 30, 200, 40);
        btnNewCustomerForm.addActionListener(e -> {
            new AddCustomer().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnNewCustomerForm);
        
        AnimatedButton btnRoom = new AnimatedButton("Room");
        btnRoom.setBounds(10, 80, 200, 40);
        btnRoom.addActionListener(e -> {
            new Room().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnRoom);
        
        AnimatedButton btnDepartment = new AnimatedButton("Department");
        btnDepartment.setBounds(10, 130, 200, 40);
        btnDepartment.addActionListener(e -> {
            new Department().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnDepartment);
        
        AnimatedButton btnEmployeeInfo = new AnimatedButton("All Employee Info");
        btnEmployeeInfo.setBounds(10, 180, 200, 40);
        btnEmployeeInfo.addActionListener(e -> {
            new EmployeeInfo().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnEmployeeInfo);
        
        AnimatedButton btnCustomerInfo = new AnimatedButton("Customer Info");
        btnCustomerInfo.setBounds(10, 230, 200, 40);
        btnCustomerInfo.addActionListener(e -> {
            new CustomerInfo().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnCustomerInfo);
        
        AnimatedButton btnManagerInfo = new AnimatedButton("Manager Info");
        btnManagerInfo.setBounds(10, 280, 200, 40);
        btnManagerInfo.addActionListener(e -> {
            new ManagerInfo().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnManagerInfo);
        
        AnimatedButton btnCheckOut = new AnimatedButton("Check Out");
        btnCheckOut.setBounds(10, 330, 200, 40);
        btnCheckOut.addActionListener(e -> {
            new Checkout().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnCheckOut);
        
        AnimatedButton btnUpdateCheck = new AnimatedButton("Update Check Status");
        btnUpdateCheck.setBounds(10, 380, 200, 40);
        btnUpdateCheck.addActionListener(e -> {
            new UpdateCheck().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnUpdateCheck);
        
        AnimatedButton btnUpdateRoom = new AnimatedButton("Update Room Status");
        btnUpdateRoom.setBounds(10, 430, 200, 40);
        btnUpdateRoom.addActionListener(e -> {
            new UpdateRoom().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnUpdateRoom);
        
        AnimatedButton btnPickUp = new AnimatedButton("Pick up Service");
        btnPickUp.setBounds(10, 480, 200, 40);
        btnPickUp.addActionListener(e -> {
            new Pickup().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnPickUp);
        
        AnimatedButton btnSearchRoom = new AnimatedButton("Search Room");
        btnSearchRoom.setBounds(10, 530, 200, 40);
        btnSearchRoom.addActionListener(e -> {
            new SearchRoom().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnSearchRoom);
        
        AnimatedButton btnLogout = new AnimatedButton("Log Out");
        btnLogout.setBounds(10, 580, 200, 40);
        btnLogout.addActionListener(e -> {
            new Login().setVisible(true);
            setVisible(false);
            // If a Dashboard instance exists, close it as well
            if (Dashboard.instance != null) {
                Dashboard.instance.dispose();
                Dashboard.instance = null;
            }
        });
        contentPane.add(btnLogout);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
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
