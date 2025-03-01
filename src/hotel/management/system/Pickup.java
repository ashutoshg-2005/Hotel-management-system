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
    // Removed unused JCheckBox available

    public Pickup(){
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel text = new JLabel("Pickup Service");
        text.setFont(new Font("Tahoma", Font.PLAIN, 20));
        text.setBounds(400, 30, 200, 30);
        add(text);
        
        JLabel lblCar = new JLabel("Type of Car");
        lblCar.setBounds(50, 100, 100, 20);
        add(lblCar);
        
        typeofcar = new Choice();
        typeofcar.setBounds(150, 100, 200, 25);
        add(typeofcar);
        
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
        
        // Labels for the table columns
        JLabel l1 = new JLabel("Name");
        l1.setBounds(50, 160, 100, 20);
        add(l1);
        
        JLabel l2 = new JLabel("Age");
        l2.setBounds(200, 160, 100, 20);
        add(l2);
        
        JLabel l3 = new JLabel("Gender");
        l3.setBounds(330, 160, 100, 20);
        add(l3);
        
        JLabel l4 = new JLabel("Company");
        l4.setBounds(460, 160, 100, 20);
        add(l4);
        
        JLabel l5 = new JLabel("Brand");
        l5.setBounds(630, 160, 100, 20);
        add(l5);
        
        JLabel l6 = new JLabel("Availability");
        l6.setBounds(740, 160, 100, 20);
        add(l6);
        
        JLabel l7 = new JLabel("Location");
        l7.setBounds(890, 160, 100, 20);
        add(l7);
        
        table = new JTable();
        table.setBounds(0, 200, 1000, 300);
        add(table);
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM driver");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        submit = new JButton("SUBMIT");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setBounds(300, 520, 120, 30);
        submit.addActionListener(this);
        add(submit);
        
        back = new JButton("BACK");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(500, 520, 120, 30);
        back.addActionListener(this);
        add(back);
        
        setBounds(300, 200, 1000, 600);
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
        new Pickup();
    }
}
