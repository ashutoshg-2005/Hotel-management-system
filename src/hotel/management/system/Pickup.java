package hotel.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;

public class Pickup extends JFrame implements ActionListener {
    JTable table;
    JButton back, submit;
    Choice typeofcar;

    public Pickup(){
        // Modern UI setup
        setTitle("Hotel Management System - Pickup Service");
        setBounds(300, 200, 1000, 600);
        
        // Use gradient panel for background instead of plain white
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1000, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        add(headerPanel);
        
        JLabel headerTitle = new JLabel("PICKUP SERVICE", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Search panel with improved styling
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(50, 80, 900, 70);
        searchPanel.setLayout(null);
        searchPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(searchPanel);
        
        JLabel lblCar = new JLabel("Type of Car");
        lblCar.setBounds(50, 25, 100, 20);
        lblCar.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchPanel.add(lblCar);
        
        typeofcar = new Choice();
        typeofcar.setBounds(150, 25, 200, 25);
        typeofcar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchPanel.add(typeofcar);
        
        // Populate the choice with available car brands from the driver table.
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM driver");
            while (rs.next()){
                // Using the new schema column "brand"
                typeofcar.add(rs.getString("brand"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        // Use AnimatedButton for submit
        submit = new AnimatedButton("SEARCH");
        submit.setBounds(400, 20, 120, 35);
        submit.addActionListener(this);
        searchPanel.add(submit);
        
        // Table panel with improved styling
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(50, 170, 900, 330);
        tablePanel.setLayout(null);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(tablePanel);
        
        // Modern styled column headers in a header panel
        JPanel tableHeader = new JPanel();
        tableHeader.setBounds(0, 0, 900, 40);
        tableHeader.setBackground(new Color(52, 73, 94));
        tableHeader.setLayout(null);
        tablePanel.add(tableHeader);
        
        // Column header labels with better styling
        String[] headers = {"Name", "Age", "Gender", "Company", "Brand", "Availability", "Location"};
        int[] positions = {50, 200, 330, 460, 630, 740, 890};
        
        for (int i = 0; i < headers.length; i++) {
            JLabel label = new JLabel(headers[i]);
            label.setBounds(positions[i], 10, 100, 20);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            tableHeader.add(label);
        }
        
        // Table with custom styling
        table = new JTable();
        table.setBounds(0, 40, 900, 290);
        
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
            ResultSet rs = c.s.executeQuery("SELECT * FROM driver");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        // Use AnimatedButton for back button
        back = new AnimatedButton("BACK");
        back.setBounds(440, 520, 150, 40);
        back.addActionListener(this);
        add(back);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 570, 1000, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        statusBar.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel(" Hotel Management System | Pickup Service Panel", JLabel.LEFT);
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
            try {
                // Query drivers based on the selected brand from the new schema.
                String query = "SELECT * FROM driver WHERE brand = '" + typeofcar.getSelectedItem() + "'";
                Conn conn = new Conn();
                ResultSet rs = conn.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Reception();
        }
    }
    
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Pickup();
    }
}
