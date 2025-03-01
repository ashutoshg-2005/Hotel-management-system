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
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
      
        // Updated labels to reflect new schema column names:
        JLabel l1 = new JLabel("Customer ID");
        l1.setBounds(40, 10, 100, 20);
        add(l1);
        
        JLabel l2 = new JLabel("Document No");
        l2.setBounds(170, 10, 100, 20);
        add(l2);
        
        JLabel l3 = new JLabel("Name");
        l3.setBounds(290, 10, 100, 20);
        add(l3);
        
        JLabel l4 = new JLabel("Gender");
        l4.setBounds(400, 10, 100, 20);
        add(l4);
        
        JLabel l5 = new JLabel("Country");
        l5.setBounds(540, 10, 100, 20);
        add(l5);
        
        JLabel l6 = new JLabel("Room No");
        l6.setBounds(670, 10, 100, 20);
        add(l6);
        
        JLabel l7 = new JLabel("Checkin Time");
        l7.setBounds(790, 10, 100, 20);
        add(l7);
        
        JLabel l8 = new JLabel("Deposit");
        l8.setBounds(910, 10, 100, 20);
        add(l8);
        
        table = new JTable();
        table.setBounds(0, 40, 1000, 400);
        add(table);
        
        try {
            Conn c = new Conn();
            // Explicitly select the columns from the new schema:
            String query = "SELECT customer_id, document_number, name, gender, country, room_number, check_in_time, deposit FROM customer";
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        back = new JButton("BACK");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(420, 500, 120, 30);
        back.addActionListener(this);
        add(back);
        
        setBounds(300, 200, 1000, 600);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        setVisible(false);
        new Reception();
    }
    
    public static void main(String[] args){
        new CustomerInfo();
    }
}
