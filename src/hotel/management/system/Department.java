package hotel.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.*;

public class Department extends JFrame implements ActionListener {
    JTable table;
    JButton back;
    
    public Department(){
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel l1 = new JLabel("Department");
        l1.setBounds(10, 10, 100, 20);
        add(l1);
 
        JLabel l3 = new JLabel("Budget");
        l3.setBounds(370, 10, 100, 20);
        add(l3);
        
        table = new JTable();
        table.setBounds(0, 50, 700, 350);
        add(table);
        
        try{
            Conn c = new Conn();
            // Select only the required columns: name and budget from the new department table schema
            ResultSet rs = c.s.executeQuery("SELECT name, budget FROM department");
            table.setModel(DbUtils.resultSetToTableModel(rs)); 
        } catch(Exception e){
            e.printStackTrace();
        }
        
        back = new JButton("BACK");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(280, 400, 120, 30);
        back.addActionListener(this);
        add(back);
        
        setBounds(400, 200, 700, 480);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        setVisible(false);
        new Reception();
    }
    
    public static void main(String[] args){
        new Department();
    }
}
