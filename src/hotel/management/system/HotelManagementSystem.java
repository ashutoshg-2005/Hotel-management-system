package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HotelManagementSystem extends JFrame implements ActionListener {

    public HotelManagementSystem() {
        initializeUI();
    }

    private void initializeUI() {
        
        setSize(1366, 565);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);

        
        JLabel backgroundImage = new JLabel(new ImageIcon(ClassLoader.getSystemResource("icons/first.jpg")));
        backgroundImage.setBounds(0, 0, 1366, 565);
        add(backgroundImage);

        
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(33, 147, 176, 150), getWidth(), getHeight(), new Color(109, 213, 237, 150));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setBounds(0, 0, 1366, 565);
        gradientPanel.setOpaque(false);
        gradientPanel.setLayout(null);
        backgroundImage.add(gradientPanel);

        
        JLabel titleLabel = new JLabel("HOTEL MANAGEMENT SYSTEM", SwingConstants.CENTER);
        titleLabel.setBounds(0, 100, 1366, 80);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        gradientPanel.add(titleLabel);

        
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(608, 400, 150, 50);
        nextButton.setBackground(new Color(255, 87, 34));
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createEmptyBorder());
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextButton.addActionListener(this);
        gradientPanel.add(nextButton);

        
        animateTitle(titleLabel);
        animateButton(nextButton);

        setVisible(true);
    }

    private void animateTitle(JLabel label) {
        new Thread(() -> {
            try {
                int y = label.getY();
                int direction = 1;

                while (true) {
                    if (y <= 90 || y >= 110) {
                        direction *= -1;
                    }
                    y += direction;
                    label.setLocation(label.getX(), y);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void animateButton(JButton button) {
        new Thread(() -> {
            try {
                int x = button.getX();
                int direction = 1;

                while (true) {
                    if (x <= 608 || x >= 618) {
                        direction *= -1;
                    }
                    x += direction;
                    button.setLocation(x, button.getY());
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new Login(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelManagementSystem::new);
    }
}
