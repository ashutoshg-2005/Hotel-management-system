package hotel.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AddEmployee extends JFrame { // Third Frame

    JTextField textField, textField_1, textField_3, textField_4, textField_5, textField_6;
    JComboBox<String> c1; // for job
    JRadioButton maleRadio, femaleRadio;
    
    public AddEmployee() {
        getContentPane().setForeground(Color.BLUE);
        getContentPane().setBackground(Color.WHITE);
        setTitle("ADD EMPLOYEE DETAILS");
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(778, 486);
        getContentPane().setLayout(null);
        
        JLabel lblname = new JLabel("NAME");
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblname.setBounds(60, 30, 120, 30);
        add(lblname);
        
        textField = new JTextField();
        textField.setBounds(200, 30, 150, 27);
        add(textField);
        
        JButton Next = new JButton("SAVE");
        Next.setBounds(200, 420, 150, 30);
        Next.setBackground(Color.BLACK);
        Next.setForeground(Color.WHITE);
        add(Next);
        
        JLabel ageLabel = new JLabel("AGE");
        ageLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        ageLabel.setBounds(60, 80, 150, 27);
        add(ageLabel);
        
        textField_1 = new JTextField();
        textField_1.setBounds(200, 80, 150, 27);
        add(textField_1);
        
        JLabel genderLabel = new JLabel("GENDER");
        genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        genderLabel.setBounds(60, 120, 150, 27);
        add(genderLabel);
        
        maleRadio = new JRadioButton("MALE");
        maleRadio.setBackground(Color.WHITE);
        maleRadio.setBounds(200, 120, 70, 27);
        add(maleRadio);
        
        femaleRadio = new JRadioButton("FEMALE");
        femaleRadio.setBackground(Color.WHITE);
        femaleRadio.setBounds(280, 120, 70, 27);
        add(femaleRadio);
        
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        
        JLabel jobLabel = new JLabel("JOB");
        jobLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        jobLabel.setBounds(60, 170, 150, 27);
        add(jobLabel);
        
        // Job options remain as before.
        String jobs[] = {"Front Desk Clerks", "Porters", "Housekeeping", "Kitchen Staff", "Room Service", "Waiter/Waitress", "Manager", "Accountant", "Chef"};
        c1 = new JComboBox<>(jobs);
        c1.setBackground(Color.WHITE);
        c1.setBounds(200, 170, 150, 30);
        add(c1);
        
        JLabel salaryLabel = new JLabel("SALARY");
        salaryLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        salaryLabel.setBounds(60, 220, 150, 27);
        add(salaryLabel);
        
        textField_3 = new JTextField();
        textField_3.setBounds(200, 220, 150, 27);
        add(textField_3);
        
        JLabel phnumLabel = new JLabel("PHONE");
        phnumLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        phnumLabel.setBounds(60, 270, 150, 27);
        add(phnumLabel);
        
        textField_4 = new JTextField();
        textField_4.setBounds(200, 270, 150, 27);
        add(textField_4);
        
        JLabel adharnumLabel = new JLabel("AADHAR");
        adharnumLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        adharnumLabel.setBounds(60, 320, 150, 27);
        add(adharnumLabel);
        
        textField_5 = new JTextField();
        textField_5.setBounds(200, 320, 150, 27);
        add(textField_5);
        
        JLabel emailLabel = new JLabel("EMAIL");
        emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        emailLabel.setBounds(60, 370, 150, 27);
        add(emailLabel);
        
        textField_6 = new JTextField();
        textField_6.setBounds(200, 370, 150, 27);
        add(textField_6);
        
        setVisible(true);
        
        JLabel header = new JLabel("ADD EMPLOYEE DETAILS");
        header.setForeground(Color.BLUE);
        header.setFont(new Font("Tahoma", Font.PLAIN, 31));
        header.setBounds(450, 24, 442, 35);
        add(header);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/tenth.jpg"));
        Image i3 = i1.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i3);
        JLabel image = new JLabel(i2);
        image.setBounds(410, 80, 480, 410);
        add(image);
        
        Next.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                // Retrieve values from fields
                String name = textField.getText();
                String ageStr = textField_1.getText();
                String salaryStr = textField_3.getText();
                String phone = textField_4.getText();
                String aadhar = textField_5.getText();
                String email = textField_6.getText();
                
                String gender = null;
                if(maleRadio.isSelected()){
                    gender = "Male";
                } else if(femaleRadio.isSelected()){
                    gender = "Female";
                }
                
                String job = (String)c1.getSelectedItem();
                
                try {
                    // Convert age and salary to appropriate types
                    int age = Integer.parseInt(ageStr);
                    double salary = Double.parseDouble(salaryStr);
                    
                    Conn c = new Conn();
                    // Insert into employee using new schema. Note: employee_id auto-generates.
                    // department_id is omitted (assumed NULL).
                    String query = "INSERT INTO employee (name, age, gender, job, salary, phone, email, aadhar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = c.c.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setInt(2, age);
                    pst.setString(3, gender);
                    pst.setString(4, job);
                    pst.setDouble(5, salary);
                    pst.setString(6, phone);
                    pst.setString(7, email);
                    pst.setString(8, aadhar);
                    
                    pst.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null,"Employee Added");
                    setVisible(false);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        setSize(900,600);
        setVisible(true);
        setLocation(530,200);
    }
    
    public static void main(String[] args){
        new AddEmployee();
    }
}
