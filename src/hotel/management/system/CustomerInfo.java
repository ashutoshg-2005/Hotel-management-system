package hotel.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;

public class CustomerInfo extends JFrame implements ActionListener {
    JTable table;
    JButton back;
    
    public CustomerInfo(){
        // Modern UI setup
        setTitle("Hotel Management System - Customer Information");
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
        
        JLabel headerTitle = new JLabel("CUSTOMER INFORMATION", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Table panel with improved styling
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(20, 80, 960, 440);
        tablePanel.setLayout(null);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(tablePanel);
        
        // Modern styled column headers in a header panel
        JPanel tableHeader = new JPanel();
        tableHeader.setBounds(0, 0, 960, 40);
        tableHeader.setBackground(new Color(52, 73, 94));
        tableHeader.setLayout(null);
        tablePanel.add(tableHeader);
        
        // Header labels with improved styling
        String[] headers = {"Customer ID", "Document No", "Name", "Gender", "Country", "Room No", "Checkin Time", "Deposit"};
        int[] positions = {30, 150, 270, 390, 510, 630, 750, 870};
        
        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i]);
            label.setBounds(positions[i], 10, 100, 20);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            tableHeader.add(label);
        }
        
        // Table with custom styling
        table = new JTable();
        table.setBounds(0, 40, 960, 400);
        
        // Style the table
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setGridColor(new Color(230, 230, 230));
        table.setForeground(new Color(33, 33, 33));
        table.setSelectionBackground(new Color(180, 210, 255));
        table.getTableHeader().setVisible(false); // Hide default header since we have custom header
        
        tablePanel.add(table);
        
        // Add a scroll pane for better usability with many records
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 40, 960, 400);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane);
        
        try {
            Conn c = new Conn();
            // Explicitly select the columns from the new schema:
            String query = "SELECT customer_id, document_number, name, gender, country, room_number, check_in_time, deposit FROM customer";
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
            // Add table empty message
            if (table.getRowCount() == 0) {
                JLabel noDataLabel = new JLabel("No customer records found", JLabel.CENTER);
                noDataLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
                noDataLabel.setForeground(new Color(150, 150, 150));
                noDataLabel.setBounds(0, 200, 960, 30);
                tablePanel.add(noDataLabel);
            }
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customer data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Use AnimatedButton instead of regular JButton
        back = new AnimatedButton("BACK");
        back.setBounds(430, 530, 150, 40);
        back.addActionListener(this);
        add(back);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 570, 1000, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Customer Information Panel");
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
        new CustomerInfo();
    }
}
