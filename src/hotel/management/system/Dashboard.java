package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    public static Dashboard instance;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }

    public Dashboard() {
        super("Hotel Management System");

        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Background image
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon(
                new ImageIcon(ClassLoader.getSystemResource("icons/third.jpg"))
                        .getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH)
        ));
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel);

        // Title label
        JLabel titleLabel = new JLabel("The Royal Orchid Welcomes You", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(33, 33, 33));
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Hotel Management menu
        JMenu managementMenu = createMenu("Hotel Management", Color.CYAN);
        JMenuItem receptionMenuItem = createMenuItem("Reception", e -> openReception());
        managementMenu.add(receptionMenuItem);
        menuBar.add(managementMenu);

        // Admin menu
        JMenu adminMenu = createMenu("Admin", Color.ORANGE);
        adminMenu.add(createMenuItem("Add Employee", e -> openAddEmployee()));
        adminMenu.add(createMenuItem("Add Rooms", e -> openAddRooms()));
        adminMenu.add(createMenuItem("Add Drivers", e -> openAddDrivers()));
        menuBar.add(adminMenu);

        // Feedback menu
        JMenu feedbackMenu = createMenu("Feedback", Color.MAGENTA);
        feedbackMenu.add(createMenuItem("Open Feedback Form", e -> openFeedbackForm()));
        menuBar.add(feedbackMenu);

        setJMenuBar(menuBar);

        // Styling
        backgroundLabel.setOpaque(true);
        setVisible(true);
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

    private void openReception() {
        try {
            new Reception().setVisible(true);
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
}
