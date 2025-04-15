package hotel.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;

public class ManagerInfo extends JFrame implements ActionListener {
    JTable table;
    JButton back;
    
    public ManagerInfo(){
        // Modern UI setup
        setTitle("Hotel Management System - Manager Information");
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
        
        JLabel headerTitle = new JLabel("MANAGER INFORMATION", JLabel.CENTER);
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
        String[] headers = {"Name", "Age", "Gender", "Job", "Salary", "Phone", "Email", "Aadhar"};
        int[] positions = {40, 170, 290, 400, 540, 670, 790, 910};
        
        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i]);
            label.setBounds(positions[i], 10, 100, 20);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            tableHeader.add(label);
        }
        
        // Table with custom styling
        table = new JTable();
        table.setBounds(0, 40, 960, 400);
        
        // Style the table
        table.setRowHeight(35);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setGridColor(new Color(230, 230, 230));
        table.setForeground(new Color(33, 33, 33));
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setSelectionBackground(new Color(180, 210, 255));
        table.getTableHeader().setVisible(false); // Hide default header since we have custom header
        
        tablePanel.add(table);
        
        // Add a scroll pane for better usability with many records
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 40, 960, 400);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane);
        
        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT name, age, gender, job, salary, phone, email, aadhar FROM employee WHERE job = 'Manager'");
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
            // Add table empty message
            if (table.getRowCount() == 0) {
                JLabel noDataLabel = new JLabel("No manager records found", JLabel.CENTER);
                noDataLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
                noDataLabel.setForeground(new Color(150, 150, 150));
                noDataLabel.setBounds(0, 200, 960, 30);
                tablePanel.add(noDataLabel);
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading manager data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Use AnimatedButton instead of regular JButton
        back = new AnimatedButton("BACK");
        back.setBounds(420, 530, 150, 40);
        back.addActionListener(this);
        add(back);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 570, 1000, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Manager Information Panel");
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
        new ManagerInfo();
    }
}
