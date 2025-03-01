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
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel text = new JLabel("CheckOut");
        text.setBounds(100, 20, 100, 30);
        text.setForeground(Color.BLUE);
        text.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(text);
        
        JLabel lblid = new JLabel("Customer ID");
        lblid.setBounds(30, 80, 100, 30);
        add(lblid);
        
        ccustomer = new Choice();
        ccustomer.setBounds(150, 80, 120, 25);
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
        lblroom.setBounds(30, 130, 100, 30);
        add(lblroom);
        
        lblroomnumber = new JLabel();
        lblroomnumber.setBounds(150, 130, 100, 30);
        add(lblroomnumber);
        
        JLabel lblcheckin = new JLabel("Checkin Time");
        lblcheckin.setBounds(30, 180, 100, 30);
        add(lblcheckin);
        
        lblcheckintime = new JLabel();
        lblcheckintime.setBounds(150, 180, 100, 30);
        add(lblcheckintime);
        
        JLabel lblcheckout = new JLabel("Checkout Time");
        lblcheckout.setBounds(30, 230, 100, 30);
        add(lblcheckout);
        
        Date date = new Date();
        lblcheckouttime = new JLabel("" + date);
        lblcheckouttime.setBounds(150, 230, 170, 30);
        add(lblcheckouttime);
        
        checkout = new JButton("CHECKOUT");
        checkout.setBackground(Color.BLACK);
        checkout.setForeground(Color.WHITE);
        checkout.setBounds(30, 280, 120, 30);
        checkout.addActionListener(this);
        add(checkout);
        
        back = new JButton("BACK");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(170, 280, 120, 30);
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
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/sixth.JPG"));
        Image i5 = i4.getImage().getScaledInstance(400, 250, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image1 = new JLabel(i6);
        image1.setBounds(350, 50, 400, 250);
        add(image1);
        
        setBounds(300, 200, 800, 400);
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
        new Checkout();
    }
}
