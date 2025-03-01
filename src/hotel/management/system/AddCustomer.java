package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.*;

public class AddCustomer extends JFrame implements ActionListener {
    private JComboBox<String> comboid;
    private JTextField tfnumber, tfname, tfcountry, tfdeposit;
    private JRadioButton rmale, rfemale;
    private ButtonGroup genderGroup;
    private Choice croom;
    private JLabel checkintime;
    private JButton btnAdd, btnBack;

    public AddCustomer() {
        setTitle("Add New Customer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel header = new JLabel("Add Customer", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setForeground(new Color(33, 33, 33));
        header.setBounds(0, 10, 600, 40);
        add(header);

        JLabel lblid = createLabel("ID Type", 50, 70);
        comboid = new JComboBox<>(new String[]{"Aadhar Card", "Passport", "Driving License", "Voter-ID", "Ration Card"});
        comboid.setBounds(200, 70, 150, 30);
        add(lblid);
        add(comboid);

        JLabel lblnumber = createLabel("ID Number", 50, 120);
        tfnumber = createTextField(200, 120);
        add(lblnumber);
        add(tfnumber);

        JLabel lblname = createLabel("Full Name", 50, 170);
        tfname = createTextField(200, 170);
        add(lblname);
        add(tfname);

        JLabel lblgender = createLabel("Gender", 50, 220);
        rmale = new JRadioButton("Male");
        rfemale = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(rmale);
        genderGroup.add(rfemale);
        rmale.setBounds(200, 220, 70, 30);
        rfemale.setBounds(280, 220, 80, 30);
        add(lblgender);
        add(rmale);
        add(rfemale);

        JLabel lblcountry = createLabel("Country", 50, 270);
        tfcountry = createTextField(200, 270);
        add(lblcountry);
        add(tfcountry);

        JLabel lblroom = createLabel("Room Number", 50, 320);
        croom = new Choice();
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT room_number FROM room WHERE availability = 'Available'");
            while (rs.next()) {
                croom.add(rs.getString("room_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        croom.setBounds(200, 320, 150, 30);
        add(lblroom);
        add(croom);

        JLabel lbltime = createLabel("Check-In Time", 50, 370);
        checkintime = new JLabel(new Date().toString());
        checkintime.setBounds(200, 370, 250, 30);
        checkintime.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(lbltime);
        add(checkintime);

        JLabel lbldeposit = createLabel("Deposit", 50, 420);
        tfdeposit = createTextField(200, 420);
        add(lbldeposit);
        add(tfdeposit);

        btnAdd = createButton("Add", 120, 470);
        btnBack = createButton("Back", 300, 470);
        btnAdd.addActionListener(this);
        btnBack.addActionListener(this);
        add(btnAdd);
        add(btnBack);

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setBounds(x, y, 120, 30);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 150, 30);
        return textField;
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 30);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            String id = (String) comboid.getSelectedItem();
            String number = tfnumber.getText();
            String name = tfname.getText();
            String gender = rmale.isSelected() ? "Male" : "Female";
            String country = tfcountry.getText();
            String room = croom.getSelectedItem();
            String deposit = tfdeposit.getText();

            try {
                Conn conn = new Conn();
                String checkInTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                String query = "INSERT INTO customer (document_type, document_number, name, gender, country, room_number, check_in_time, deposit) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                
                PreparedStatement pstmt = conn.c.prepareStatement(query);
                pstmt.setString(1, id);
                pstmt.setString(2, number);
                pstmt.setString(3, name);
                pstmt.setString(4, gender);
                pstmt.setString(5, country);
                pstmt.setInt(6, Integer.parseInt(room));
                pstmt.setString(7, checkInTime);
                pstmt.setDouble(8, Double.parseDouble(deposit));
                
                pstmt.executeUpdate();

                PreparedStatement updateRoom = conn.c.prepareStatement("UPDATE room SET availability = 'Occupied' WHERE room_number = ?");
                updateRoom.setInt(1, Integer.parseInt(room));
                updateRoom.executeUpdate();

                JOptionPane.showMessageDialog(this, "Customer Added Successfully");
                dispose();
                new Reception();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == btnBack) {
            dispose();
            new Reception();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddCustomer());
    }
}
