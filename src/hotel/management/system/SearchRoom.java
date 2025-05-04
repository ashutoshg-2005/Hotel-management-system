package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;

public class SearchRoom extends JFrame implements ActionListener {
    JTable table;
    JButton back, submit;
    JComboBox<String> bedType;
    JCheckBox available;
    
    public SearchRoom(){
        // Modern UI setup
        setTitle("Hotel Management System - Search Room");
        setBounds(300, 200, 1000, 600);
        
        // Use gradient panel for background
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1000, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        add(headerPanel);
        
        JLabel headerTitle = new JLabel("SEARCH FOR ROOM", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Main search criteria panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(50, 80, 900, 70);
        searchPanel.setLayout(null);
        searchPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(searchPanel);
        
        JLabel lblbed = new JLabel("Bed Type");
        lblbed.setBounds(30, 25, 100, 20);
        lblbed.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchPanel.add(lblbed);
        
        // Updated bed type values with more options
        bedType = new JComboBox<>(new String[]{"Single", "Double", "Queen", "King", "Twin"});
        bedType.setBounds(130, 25, 150, 25);
        bedType.setBackground(Color.WHITE);
        bedType.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchPanel.add(bedType);
        
        available = new JCheckBox("Only Display Available Rooms");
        available.setBounds(350, 25, 200, 25);
        available.setBackground(null); // Transparent background
        available.setFont(new Font("SansSerif", Font.PLAIN, 14));
        available.setFocusPainted(false);
        searchPanel.add(available);
        
        submit = new AnimatedButton("SEARCH");
        submit.setBounds(600, 20, 120, 35);
        submit.addActionListener(this);
        searchPanel.add(submit);
        
        // Table panel with improved styling
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(50, 170, 900, 350);
        tablePanel.setLayout(null);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(tablePanel);
        
        // Modern styled column headers in a header panel
        JPanel tableHeader = new JPanel();
        tableHeader.setBounds(0, 0, 900, 40);
        tableHeader.setBackground(new Color(52, 73, 94));
        tableHeader.setLayout(null);
        tablePanel.add(tableHeader);
        
        // Column header labels with better styling
        JLabel l1 = new JLabel("Room Number");
        l1.setBounds(50, 10, 120, 20);
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.add(l1);
        
        JLabel l2 = new JLabel("Availability");
        l2.setBounds(230, 10, 100, 20);
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.add(l2);
        
        JLabel l3 = new JLabel("Cleaning Status");
        l3.setBounds(410, 10, 120, 20);
        l3.setForeground(Color.WHITE);
        l3.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.add(l3);
        
        JLabel l4 = new JLabel("Price");
        l4.setBounds(610, 10, 100, 20);
        l4.setForeground(Color.WHITE);
        l4.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.add(l4);
        
        JLabel l5 = new JLabel("Bed Type");
        l5.setBounds(780, 10, 100, 20);
        l5.setForeground(Color.WHITE);
        l5.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.add(l5);
        
        // Table with custom styling
        table = new JTable();
        table.setBounds(0, 40, 900, 310);
        
        // Style the table
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setGridColor(new Color(230, 230, 230));
        table.setForeground(new Color(33, 33, 33));
        table.setSelectionBackground(new Color(180, 210, 255));
        table.getTableHeader().setVisible(false); // Hide default header since we have custom header
        
        tablePanel.add(table);
        
        // Back button
        back = new AnimatedButton("BACK");
        back.setBounds(450, 530, 120, 35);
        back.addActionListener(this);
        add(back);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 570, 1000, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Search Room Panel");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel, BorderLayout.WEST);
        add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == submit){
            try{
                // Build query using the new bed_type values and availability condition
                String query1 = "SELECT * FROM room WHERE bed_type = '" + bedType.getSelectedItem() + "'";
                String query2 = "SELECT * FROM room WHERE availability = 'Available' AND bed_type = '" + bedType.getSelectedItem() + "'";
                Conn conn = new Conn();
                ResultSet rs;
                if (available.isSelected()){
                     rs = conn.s.executeQuery(query2);
                } else {
                    rs = conn.s.executeQuery(query1);
                }
                table.setModel(DbUtils.resultSetToTableModel(rs));
                
                // Format table after data is loaded
                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "No rooms match your search criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error searching for rooms: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            setVisible(false);
            dispose(); // Just dispose this window without creating a new Reception window
        }
    }
    
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new SearchRoom();
    }
}
