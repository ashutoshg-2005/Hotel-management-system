package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class UpdateCheck extends JFrame implements ActionListener {
    Choice ccustomer;
    JTextField tfroom, tfname, tfcheckin, tfpaid, tfpending; 
    JButton update, check, back;
    
    public UpdateCheck(){
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel text = new JLabel("Update Status");
        text.setBounds(90, 20, 200, 30);
        text.setFont(new Font("Tahoma", Font.PLAIN, 20));
        text.setForeground(Color.BLUE);
        add(text);
        
        JLabel lblid = new JLabel("Customer ID");
        lblid.setBounds(30, 80, 100, 20);
        add(lblid);
        
        ccustomer = new Choice();
        ccustomer.setBounds(200, 80, 150, 25);
        add(ccustomer);
        
        try {
            Conn c = new Conn();
            // Populate with customer_id from the new schema
            ResultSet rs = c.s.executeQuery("SELECT customer_id FROM customer");
            while (rs.next()){
                ccustomer.add(rs.getString("customer_id"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        JLabel lblroom = new JLabel("Room Number");
        lblroom.setBounds(30, 120, 100, 20);
        add(lblroom);
        
        tfroom = new JTextField();
        tfroom.setBounds(200, 120, 100, 25);
        add(tfroom);
        
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(30, 160, 100, 20);
        add(lblname);
        
        tfname = new JTextField();
        tfname.setBounds(200, 160, 100, 25);
        add(tfname);
        
        JLabel lblcheckin = new JLabel("Checkin Time");
        lblcheckin.setBounds(30, 200, 100, 20);
        add(lblcheckin);
        
        tfcheckin = new JTextField();
        tfcheckin.setBounds(200, 200, 100, 25);
        add(tfcheckin);
        
        JLabel lblpaid = new JLabel("Amount Paid");
        lblpaid.setBounds(30, 240, 100, 20);
        add(lblpaid);
        
        tfpaid = new JTextField();
        tfpaid.setBounds(200, 240, 150, 25);
        add(tfpaid);
        
        JLabel lblpending = new JLabel("Pending");
        lblpending.setBounds(30, 280, 100, 20);
        add(lblpending);
        
        tfpending = new JTextField();
        tfpending.setBounds(200, 280, 150, 25);
        add(tfpending);
        
        check = new JButton("CHECK");
        check.setBackground(Color.BLACK);
        check.setForeground(Color.WHITE);
        check.setBounds(30, 340, 100, 30);
        check.addActionListener(this);
        add(check);
        
        update = new JButton("UPDATE");
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        update.setBounds(150, 340, 100, 30);
        update.addActionListener(this);
        add(update); 
        
        back = new JButton("BACK");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(270, 340, 100, 30);
        back.addActionListener(this);
        add(back);
        
        ImageIcon i1  = new ImageIcon(ClassLoader.getSystemResource("icons/nine.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(400, 50, 500, 300);
        add(image);
        
        setBounds(300, 200, 900, 500);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == check){
            String id = ccustomer.getSelectedItem();
            // Query using new schema column: customer_id
            String query = "SELECT * FROM customer WHERE customer_id = '" + id + "'";
            try{
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                if(rs.next()){
                    // Use new column names: room_number, name, check_in_time, deposit
                    tfroom.setText(rs.getString("room_number"));
                    tfname.setText(rs.getString("name"));
                    tfcheckin.setText(rs.getString("check_in_time"));
                    tfpaid.setText(rs.getString("deposit"));
                }
                // Query the room table with new column name: room_number
                ResultSet rs2 = c.s.executeQuery("SELECT * FROM room WHERE room_number = '" + tfroom.getText() + "'");
                if(rs2.next()){
                    String priceStr = rs2.getString("price");
                    double price = Double.parseDouble(priceStr);
                    double paid = Double.parseDouble(tfpaid.getText());
                    double pendingAmount = price - paid;
                    tfpending.setText(String.valueOf(pendingAmount));
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        } else if(ae.getSource() == update){
            String customerId = ccustomer.getSelectedItem();
            String room = tfroom.getText();
            String name = tfname.getText();
            String checkin = tfcheckin.getText();
            String deposit = tfpaid.getText();
            try{
                Conn c = new Conn();
                // Update customer using new column names: room_number, check_in_time, and customer_id
                c.s.executeUpdate("UPDATE customer SET room_number = '" + room + "', name = '" + name + "', check_in_time = '" + checkin + "', deposit = '" + deposit + "' WHERE customer_id = '" + customerId + "'");
                JOptionPane.showMessageDialog(null, "Data updated successfully");
                setVisible(false);
                new Reception();
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Reception();
        }
    }
    
    public static void main(String[] args){
        new UpdateCheck();
    }
}
