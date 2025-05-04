package hotel.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;
import javax.swing.table.DefaultTableCellRenderer;

public class Department extends JFrame implements ActionListener {
    JTable table;
    JButton back;
    
    public Department(){
        // Modern UI setup
        setTitle("Hotel Management System - Department Information");
        setBounds(400, 200, 700, 500);
        
        // Use gradient panel for background
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 700, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        add(headerPanel);
        
        JLabel headerTitle = new JLabel("DEPARTMENT INFORMATION", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Table panel with improved styling
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(20, 80, 660, 350);
        tablePanel.setLayout(null);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(tablePanel);
        
        // Modern styled column headers in a header panel
        JPanel tableHeader = new JPanel();
        tableHeader.setBounds(0, 0, 660, 40);
        tableHeader.setBackground(new Color(52, 73, 94));
        tableHeader.setLayout(null);
        tablePanel.add(tableHeader);
        
        // Header labels with improved styling
        JLabel l1 = new JLabel("Department Name");
        l1.setBounds(120, 10, 200, 20);
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.add(l1);
 
        JLabel l3 = new JLabel("Budget");
        l3.setBounds(450, 10, 100, 20);
        l3.setForeground(Color.WHITE);
        l3.setFont(new Font("SansSerif", Font.BOLD, 14));
        tableHeader.add(l3);
        
        // Table with custom styling
        table = new JTable();
        table.setBounds(0, 40, 660, 310);
        
        // Style the table
        table.setRowHeight(35);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setGridColor(new Color(230, 230, 230));
        table.setForeground(new Color(33, 33, 33));
        table.setSelectionBackground(new Color(180, 210, 255));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setVisible(false); // Hide default header since we have custom header
        
        tablePanel.add(table);
        
        // Add a scroll pane for better usability with many records
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 40, 660, 310);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane);
        
        try{
            Conn c = new Conn();
            // Select only the required columns: name and budget from the new department table schema
            ResultSet rs = c.s.executeQuery("SELECT name, budget FROM department");
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
            // Center align the columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            
            // Add table empty message
            if (table.getRowCount() == 0) {
                JLabel noDataLabel = new JLabel("No department records found", JLabel.CENTER);
                noDataLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
                noDataLabel.setForeground(new Color(150, 150, 150));
                noDataLabel.setBounds(0, 150, 660, 30);
                tablePanel.add(noDataLabel);
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading department data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Use AnimatedButton instead of regular JButton
        back = new AnimatedButton("BACK");
        back.setBounds(280, 440, 150, 40);
        back.addActionListener(this);
        add(back);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 470, 700, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Department Information Panel");
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
        dispose(); // Just dispose this window without creating a new Reception window
    }
    
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Department();
    }
}
