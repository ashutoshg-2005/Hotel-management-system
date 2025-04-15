package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;

public class Room extends JFrame implements ActionListener {
    JTable table;
    JButton back;
    
    public Room(){
        // Modern UI setup
        setTitle("Hotel Management System - Room Details");
        setBounds(300, 200, 1050, 600);
        
        // Use gradient panel for background instead of plain white
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1050, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        contentPane.add(headerPanel);
        
        JLabel headerTitle = new JLabel("ROOM INFORMATION", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Main content - adjusted Y positions to account for header
        // Create a panel for the table with a border
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(20, 80, 500, 440);
        tablePanel.setLayout(null);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(tablePanel);
        
        // Modern styled column headers in a header panel
        JPanel tableHeader = new JPanel();
        tableHeader.setBounds(0, 0, 500, 40);
        tableHeader.setBackground(new Color(52, 73, 94));
        tableHeader.setLayout(null);
        tablePanel.add(tableHeader);
        
        // Labels for table columns with improved styling
        JLabel l1 = new JLabel("Room Number");
        l1.setBounds(10, 10, 100, 20);
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("SansSerif", Font.BOLD, 12));
        tableHeader.add(l1);
        
        JLabel l2 = new JLabel("Availability");
        l2.setBounds(120, 10, 100, 20);
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("SansSerif", Font.BOLD, 12));
        tableHeader.add(l2);
        
        JLabel l3 = new JLabel("Cleaning Status");
        l3.setBounds(230, 10, 120, 20);
        l3.setForeground(Color.WHITE);
        l3.setFont(new Font("SansSerif", Font.BOLD, 12));
        tableHeader.add(l3);
        
        JLabel l4 = new JLabel("Price");
        l4.setBounds(350, 10, 100, 20);
        l4.setForeground(Color.WHITE);
        l4.setFont(new Font("SansSerif", Font.BOLD, 12));
        tableHeader.add(l4);
        
        JLabel l5 = new JLabel("Bed Type");
        l5.setBounds(450, 10, 100, 20);
        l5.setForeground(Color.WHITE);
        l5.setFont(new Font("SansSerif", Font.BOLD, 12));
        tableHeader.add(l5);
        
        // Table with custom styling
        table = new JTable();
        table.setBounds(0, 40, 500, 400);
        
        // Style the table
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setGridColor(new Color(230, 230, 230));
        table.setForeground(new Color(33, 33, 33));
        table.setSelectionBackground(new Color(180, 210, 255));
        table.getTableHeader().setVisible(false); // Hide default header since we have custom header
        
        tablePanel.add(table);
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM room");
            table.setModel(DbUtils.resultSetToTableModel(rs)); 
        } catch(Exception e){
            e.printStackTrace();
        }
        
        // Display image on right side with improved scaling
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/eight.jpg"));
        Image i2 = i1.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(550, 80, 450, 450);
        add(image);
        
        // Use AnimatedButton instead of regular JButton
        back = new AnimatedButton("BACK");
        back.setBounds(200, 530, 150, 40);
        back.addActionListener(this);
        add(back);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 570, 1050, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Room Information Panel", JLabel.LEFT);
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        setVisible(false);
        new Reception();
    }
    
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Room();
    }
}
