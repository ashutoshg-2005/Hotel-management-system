package hotel.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class Checkout extends JFrame implements ActionListener {
    Choice ccustomer;
    JLabel lblroomnumber, lblcheckintime, lblcheckouttime;
    JButton checkout, back;

    Checkout() {
        // Modern UI setup
        setTitle("Hotel Management System - Checkout");
        setBounds(300, 200, 800, 400);
        
        // Use gradient panel for background instead of plain white
        JPanel contentPane = new GradientPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        // Add header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 800, 60);
        headerPanel.setBackground(new Color(45, 62, 80));
        headerPanel.setLayout(new BorderLayout());
        contentPane.add(headerPanel);
        
        JLabel headerTitle = new JLabel("CUSTOMER CHECKOUT", JLabel.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.CENTER);
        
        // Main content - adjusted Y positions to account for header
        JLabel lblid = new JLabel("Customer ID");
        lblid.setBounds(30, 80, 100, 30);
        lblid.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lblid);
        
        ccustomer = new Choice();
        ccustomer.setBounds(150, 80, 150, 25);
        ccustomer.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(ccustomer);
        
        try {
            Conn c = new Conn();
            // Populate with customer_id from the new schema
            ResultSet rs = c.s.executeQuery("SELECT customer_id FROM customer");
            while (rs.next()) {
                ccustomer.add(rs.getString("customer_id"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        JLabel lblroom = new JLabel("Room Number");
        lblroom.setBounds(30, 130, 120, 30);
        lblroom.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lblroom);
        
        lblroomnumber = new JLabel();
        lblroomnumber.setBounds(150, 130, 150, 30);
        lblroomnumber.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblroomnumber.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblroomnumber.setOpaque(true);
        lblroomnumber.setBackground(Color.WHITE);
        lblroomnumber.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblroomnumber);
        
        JLabel lblcheckin = new JLabel("Checkin Time");
        lblcheckin.setBounds(30, 180, 120, 30);
        lblcheckin.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lblcheckin);
        
        lblcheckintime = new JLabel();
        lblcheckintime.setBounds(150, 180, 180, 30);
        lblcheckintime.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblcheckintime.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblcheckintime.setOpaque(true);
        lblcheckintime.setBackground(Color.WHITE);
        lblcheckintime.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblcheckintime);
        
        JLabel lblcheckout = new JLabel("Checkout Time");
        lblcheckout.setBounds(30, 230, 120, 30);
        lblcheckout.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lblcheckout);
        
        Date date = new Date();
        lblcheckouttime = new JLabel("" + date);
        lblcheckouttime.setBounds(150, 230, 180, 30);
        lblcheckouttime.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblcheckouttime.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblcheckouttime.setOpaque(true);
        lblcheckouttime.setBackground(Color.WHITE);
        lblcheckouttime.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblcheckouttime);
        
        // Use AnimatedButton instead of regular JButton
        checkout = new AnimatedButton("CHECKOUT");
        checkout.setBounds(30, 300, 150, 40);
        checkout.addActionListener(this);
        add(checkout);
        
        back = new AnimatedButton("BACK");
        back.setBounds(200, 300, 150, 40);
        back.addActionListener(this);
        add(back);
        
        ccustomer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                try {
                    Conn c = new Conn();
                    String id = ccustomer.getSelectedItem();
                    // Query using new schema: using customer_id, and retrieving room_number and check_in_time
                    ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE customer_id = '" + id + "'");
                    if (rs.next()) {
                        lblroomnumber.setText(rs.getString("room_number"));
                        lblcheckintime.setText(rs.getString("check_in_time"));
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        // Improved image handling with better scaling
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/sixth.JPG"));
        Image i5 = i4.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image1 = new JLabel(i6);
        image1.setBounds(350, 80, 400, 250);
        add(image1);
        
        // Add status bar at bottom
        JPanel statusBar = new JPanel();
        statusBar.setBounds(0, 370, 800, 30);
        statusBar.setBackground(new Color(45, 62, 80));
        JLabel statusLabel = new JLabel("Hotel Management System | Checkout Panel");
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel);
        add(statusBar);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == checkout) {
            String id = ccustomer.getSelectedItem();
            String room = lblroomnumber.getText();
            // Updated queries using new schema column names
            String query1 = "DELETE FROM customer WHERE customer_id = '" + id + "'";
            String query2 = "UPDATE room SET availability = 'Available' WHERE room_number = '" + room + "'";
            try {
                Conn c = new Conn();
                c.s.executeUpdate(query1);
                c.s.executeUpdate(query2);
                JOptionPane.showMessageDialog(null, "Checkout Done");
                setVisible(false);
                new Reception();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Reception();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Checkout();
    }
}
