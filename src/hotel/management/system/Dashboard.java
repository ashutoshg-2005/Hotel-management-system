package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import javax.swing.border.*;

public class Dashboard extends JFrame {
    public static Dashboard instance;
    private JLabel clockLabel, dateLabel;
    private Timer clockTimer;
    private String userRole; // Added to store the user role

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard("admin"); // Default to admin role when run directly
            instance = dashboard;
            dashboard.setVisible(true);
        });
    }

    public Dashboard(String userRole) { // Modified constructor to accept user role
        super("Hotel Management System");
        this.userRole = userRole; // Store the user role

        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Background image with higher quality scaling
        JLabel backgroundLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon(ClassLoader.getSystemResource("icons/third.jpg"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImage));
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel, BorderLayout.CENTER);

        // Add semi-transparent overlay for better text visibility - darker for more contrast
        JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 100)); // Slightly darker overlay
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlay.setOpaque(false);
        backgroundLabel.add(overlay, BorderLayout.CENTER);
        
        // Header panel with richer gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Rich blue gradient for header
                GradientPaint gradient = new GradientPaint(0, 0, new Color(20, 30, 48, 200),
                        getWidth(), 0, new Color(36, 59, 85, 200));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add subtle highlight at top
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillRect(0, 0, getWidth(), 2);
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        backgroundLabel.add(headerPanel, BorderLayout.NORTH);
        
        // Title label with gold color
        JLabel titleLabel = new JLabel("THE ROYAL ORCHID", SwingConstants.CENTER);
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold color
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
        
        // Subtitle with elegant styling
        JLabel subtitleLabel = new JLabel("LUXURY HOTEL & RESORT", SwingConstants.CENTER);
        subtitleLabel.setForeground(new Color(255, 248, 220)); // Cornsilk color
        subtitleLabel.setFont(new Font("Serif", Font.ITALIC, 24));
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Add clock and date display with elegant styling
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timePanel.setOpaque(false);
        timePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 20));
        
        clockLabel = new JLabel();
        clockLabel.setForeground(new Color(255, 248, 220));
        clockLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        dateLabel = new JLabel(java.time.LocalDate.now().toString());
        dateLabel.setForeground(new Color(255, 248, 220));
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        JLabel separator = new JLabel(" | ");
        separator.setForeground(new Color(255, 248, 220));
        
        timePanel.add(dateLabel);
        timePanel.add(separator);
        timePanel.add(clockLabel);
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        headerPanel.add(timePanel, BorderLayout.EAST);

        // Enhanced menu bar with richer gradient
        JMenuBar menuBar = new JMenuBar() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dark gradient for menu bar
                GradientPaint gradient = new GradientPaint(0, 0, new Color(20, 20, 20),
                        0, getHeight(), new Color(40, 40, 40));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add subtle highlight at top
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillRect(0, 0, getWidth(), 1);
            }
        };
        menuBar.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        // Menu items with richer colors
        JMenu managementMenu = createMenu("Hotel Management", new Color(64, 224, 208)); // Turquoise
        JMenuItem receptionMenuItem = createMenuItem("Reception", e -> openReception());
        managementMenu.add(receptionMenuItem);
        menuBar.add(managementMenu);

        // Admin menu with warmer orange - Only add for admin and manager roles
        if ("admin".equalsIgnoreCase(userRole) || "Manager".equalsIgnoreCase(userRole)) {
            JMenu adminMenu = createMenu("Admin", new Color(255, 165, 0)); // Orange
            adminMenu.add(createMenuItem("Add Employee", e -> openAddEmployee()));
            adminMenu.add(createMenuItem("Add Rooms", e -> openAddRooms()));
            adminMenu.add(createMenuItem("Add Drivers", e -> openAddDrivers()));
            menuBar.add(adminMenu);
        }

        // Feedback menu with purple
        JMenu feedbackMenu = createMenu("Feedback", new Color(186, 85, 211)); // Medium Orchid
        feedbackMenu.add(createMenuItem("Open Feedback Form", e -> openFeedbackForm()));
        menuBar.add(feedbackMenu);
        
        
        
        // Logout button with deeper red
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(178, 34, 34)); // Firebrick red
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect to logout button
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(220, 20, 60)); // Crimson (lighter)
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(new Color(178, 34, 34)); // Back to Firebrick
            }
        });
        
        logoutButton.addActionListener(e -> logout());
        menuBar.add(logoutButton);

        // Quick access panel with elegant glass-like effect
        JPanel quickAccessPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 100)); // Translucent black
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        quickAccessPanel.setLayout(new BoxLayout(quickAccessPanel, BoxLayout.Y_AXIS));
        quickAccessPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        quickAccessPanel.setOpaque(false);
        
        // Add title to quick access panel
        JLabel quickAccessTitle = new JLabel("QUICK ACCESS");
        quickAccessTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        quickAccessTitle.setForeground(new Color(255, 215, 0)); // Gold color
        quickAccessTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        quickAccessPanel.add(quickAccessTitle);
        quickAccessPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add quick access buttons with elegant styling
        quickAccessPanel.add(createQuickAccessButton("Reception", "icons/fourth.jpg", e -> openReception()));
        quickAccessPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        quickAccessPanel.add(createQuickAccessButton("Room Information", "icons/eight.jpg", e -> openRoom()));
        quickAccessPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        quickAccessPanel.add(createQuickAccessButton("Customer Information", "icons/fifth.png", e -> openCustomerInfo()));
        
        // Add Room quick access button only for admin and manager
        if ("admin".equalsIgnoreCase(userRole) || "Manager".equalsIgnoreCase(userRole)) {
            quickAccessPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            quickAccessPanel.add(createQuickAccessButton("Add Room", "icons/twelve.jpg", e -> openAddRooms()));
        }

        // Status bar with rich color
        JPanel statusBar = new JPanel();
        statusBar.setBackground(new Color(20, 30, 48)); // Darker blue
        statusBar.setLayout(new BorderLayout());
        statusBar.setPreferredSize(new Dimension(getWidth(), 30));
        
        JLabel statusLabel = new JLabel("  Hotel Management System | Dashboard");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        
        // Show current user role in status bar
        JLabel userLabel = new JLabel("Logged in as: " + userRole + "  ");
        userLabel.setForeground(Color.WHITE);
        statusBar.add(userLabel, BorderLayout.EAST);

        backgroundLabel.add(quickAccessPanel, BorderLayout.EAST);
        backgroundLabel.add(statusBar, BorderLayout.SOUTH);
        
        setJMenuBar(menuBar);
        
        // Start the clock timer
        startClock();
        
        // Animate the subtitle with a fade effect
        animateSubtitle(subtitleLabel);
    }
    
    private JMenu createMenu(String title, Color foregroundColor) {
        JMenu menu = new JMenu(title);
        menu.setForeground(foregroundColor);
        menu.setFont(new Font("SansSerif", Font.BOLD, 18));
        return menu;
    }

    private JMenuItem createMenuItem(String title, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setFont(new Font("SansSerif", Font.PLAIN, 16));
        menuItem.setBackground(Color.WHITE);
        menuItem.setForeground(Color.DARK_GRAY);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }
    
    private JButton createQuickAccessButton(String text, String iconPath, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(new Color(255, 255, 255, 220)); // Slightly transparent white
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Create glass-like border with gradient
        button.setBorder(BorderFactory.createCompoundBorder(
            new Border() {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Create rich gradient background for button
                    GradientPaint gradient = new GradientPaint(x, y, new Color(36, 59, 85, 180),
                            x + width, y, new Color(20, 30, 48, 180));
                    g2d.setPaint(gradient);
                    g2d.fillRoundRect(x, y, width, height, 10, 10);
                    
                    // Add subtle highlight at top
                    g2d.setColor(new Color(255, 255, 255, 30));
                    g2d.drawLine(x + 5, y + 1, x + width - 5, y + 1);
                }

                @Override
                public Insets getBorderInsets(Component c) {
                    return new Insets(10, 15, 10, 15);
                }

                @Override
                public boolean isBorderOpaque() {
                    return false;
                }
            },
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        // Add the icon if found with better scaling
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            // If icon not found, don't add it
        }
        
        button.addActionListener(action);
        
        // Add hover effect with smooth animation
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(255, 215, 0)); // Gold color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(255, 255, 255, 220)); // Back to original color
            }
        });
        
        return button;
    }

    private void openReception() {
        try {
            new Reception().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void openRoom() {
        try {
            new Room().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void openCustomerInfo() {
        try {
            new CustomerInfo().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAddEmployee() {
        try {
            new AddEmployee().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAddRooms() {
        try {
            new AddRooms().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAddDrivers() {
        try {
            new AddDrivers().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFeedbackForm() {
        try {
            new FeedbackForm().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showAboutDialog() {
        JDialog aboutDialog = new JDialog(this, "About Hotel Management System", true);
        aboutDialog.setLayout(new BorderLayout());
        
        JPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Hotel Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold color
        
        JLabel versionLabel = new JLabel("Version 2.1.0", SwingConstants.CENTER);
        versionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        versionLabel.setForeground(Color.WHITE);
        
        JTextArea descTextArea = new JTextArea(
            "Hotel Management System is a comprehensive solution for " +
            "managing all aspects of hotel operations including room bookings, " +
            "customer management, employee records, and more.\n\n" +
            "© 2025 The Royal Orchid Hotel"
        );
        descTextArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setEditable(false);
        descTextArea.setOpaque(false);
        descTextArea.setForeground(Color.WHITE);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        titlePanel.add(versionLabel);
        
        gradientPanel.add(titlePanel, BorderLayout.NORTH);
        gradientPanel.add(descTextArea, BorderLayout.CENTER);
        
        JButton closeButton = new AnimatedButton("Close");
        closeButton.addActionListener(e -> aboutDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        
        gradientPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        aboutDialog.add(gradientPanel);
        aboutDialog.setSize(400, 300);
        aboutDialog.setLocationRelativeTo(this);
        aboutDialog.setResizable(false);
        aboutDialog.setVisible(true);
    }
    
    private void logout() {
        int option = JOptionPane.showConfirmDialog(
            this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            dispose();
            new Login().setVisible(true);
        }
    }
    
    private void startClock() {
        updateClock(); // Initial update
        
        // Update every second
        clockTimer = new Timer(1000, e -> updateClock());
        clockTimer.start();
    }
    
    private void updateClock() {
        LocalTime now = LocalTime.now();
        String time = String.format("%02d:%02d:%02d", now.getHour(), now.getMinute(), now.getSecond());
        clockLabel.setText(time);
    }
    
    private void animateSubtitle(JLabel label) {
        final Timer timer = new Timer(50, null);
        final float[] alpha = {0.0f};
        
        timer.addActionListener(e -> {
            alpha[0] += 0.05f;
            if (alpha[0] > 1.0f) {
                alpha[0] = 1.0f;
                timer.stop();
            }
            label.setForeground(new Color(255, 248, 220, (int)(alpha[0] * 200)));
        });
        
        timer.start();
    }
    
    @Override
    public void dispose() {
        if (clockTimer != null) {
            clockTimer.stop();
        }
        super.dispose();
    }
}
