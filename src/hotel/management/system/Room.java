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
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        // Display image on right side
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/eight.jpg"));
        Image i2 = i1.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(500, 0, 600, 600);
        add(image);
        
        // Labels for table columns
        JLabel l1 = new JLabel("Room Number");
        l1.setBounds(10, 10, 100, 20);
        add(l1);
        
        JLabel l2 = new JLabel("Availability");
        l2.setBounds(120, 10, 100, 20);
        add(l2);
        
        // Updated label to reflect new schema column name
        JLabel l3 = new JLabel("Cleaning Status");
        l3.setBounds(230, 10, 120, 20);
        add(l3);
        
        JLabel l4 = new JLabel("Price");
        l4.setBounds(350, 10, 100, 20);
        add(l4);
        
        JLabel l5 = new JLabel("Bed Type");
        l5.setBounds(450, 10, 100, 20);
        add(l5);
        
        table = new JTable();
        table.setBounds(0, 40, 500, 400);
        add(table);
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM room");
            table.setModel(DbUtils.resultSetToTableModel(rs)); 
        } catch(Exception e){
            e.printStackTrace();
        }
        
        back = new JButton("BACK");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(200, 500, 120, 30);
        back.addActionListener(this);
        add(back);
        
        setBounds(300, 200, 1050, 600);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        setVisible(false);
        new Reception();
    }
    
    public static void main(String[] args){
        new Room();
    }
}
