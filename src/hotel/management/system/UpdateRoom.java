package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class UpdateRoom extends JFrame implements ActionListener {
    Choice ccustomer;
    JTextField tfroom, tfavailable, tfstatus;
    JButton check, update, back;

    public static void main(String[] args) {
        new UpdateRoom();
    }

    public UpdateRoom() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel text = new JLabel("Update Room Status");
        text.setBounds(30, 20, 250, 30);
        text.setFont(new Font("Tahoma", Font.PLAIN, 25));
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
            // Populate the choice with customer_id from the new schema
            ResultSet rs = c.s.executeQuery("SELECT customer_id FROM customer");
            while (rs.next()) {
                ccustomer.add(rs.getString("customer_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblroom = new JLabel("Room Number");
        lblroom.setBounds(30, 130, 100, 20);
        add(lblroom);

        tfroom = new JTextField();
        tfroom.setBounds(200, 130, 100, 25);
        add(tfroom);

        JLabel lblavail = new JLabel("Availability");
        lblavail.setBounds(30, 180, 100, 20);
        add(lblavail);

        tfavailable = new JTextField();
        tfavailable.setBounds(200, 180, 100, 25);
        add(tfavailable);

        JLabel lblstatus = new JLabel("Cleaning Status");
        lblstatus.setBounds(30, 230, 100, 20);
        add(lblstatus);

        tfstatus = new JTextField();
        tfstatus.setBounds(200, 230, 100, 25);
        add(tfstatus);

        check = new JButton("CHECK");
        check.setBackground(Color.BLACK);
        check.setForeground(Color.WHITE);
        check.setBounds(30, 300, 100, 30);
        check.addActionListener(this);
        add(check);

        update = new JButton("UPDATE");
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        update.setBounds(150, 300, 100, 30);
        update.addActionListener(this);
        add(update);

        back = new JButton("BACK");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(270, 300, 100, 30);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/seventh.jpg"));
        Image i2 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 50, 500, 300);
        add(image);

        setBounds(300, 200, 900, 450);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == check) {
            String id = ccustomer.getSelectedItem();
            // Query using new schema: customer_id and room_number
            String query = "SELECT * FROM customer WHERE customer_id = '" + id + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    // Set room number from customer table (new schema column name: room_number)
                    tfroom.setText(rs.getString("room_number"));
                }
                // Now lookup the room details using new column names
                ResultSet rs2 = c.s.executeQuery("SELECT * FROM room WHERE room_number = '" + tfroom.getText() + "'");
                if (rs2.next()) {
                    tfavailable.setText(rs2.getString("availability"));
                    tfstatus.setText(rs2.getString("cleaning_status"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            String id = ccustomer.getSelectedItem();
            String room = tfroom.getText();
            String available = tfavailable.getText();
            String status = tfstatus.getText();
            try {
                Conn c = new Conn();
                // Update using new column names
                c.s.executeUpdate("UPDATE room SET availability = '" + available + "', cleaning_status = '" + status + "' WHERE room_number = '" + room + "'");
                JOptionPane.showMessageDialog(null, "Data updated successfully");
                setVisible(false);
                new Reception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { // back button
            setVisible(false);
            new Reception();
        }
    }

    
}
