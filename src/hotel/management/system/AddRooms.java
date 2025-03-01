package hotel.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AddRooms extends JFrame implements ActionListener {

    JPanel contentPane;
    JTextField tRoomNumber, tPrice;
    JComboBox<String> cbAvailability, cbCleaningStatus, cbBedType;
    JButton bAdd, bCancel;

    public static void main(String[] args) {
        new AddRooms().setVisible(true);
    }

    public AddRooms() {
        setBounds(450, 200, 1000, 450);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Image on the right side
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/twelve.jpg"));
        Image i3 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i3);
        JLabel imageLabel = new JLabel(i2);
        imageLabel.setBounds(400, 30, 500, 370);
        add(imageLabel);

        JLabel header = new JLabel("Add Rooms");
        header.setFont(new Font("Tahoma", Font.BOLD, 18));
        header.setBounds(194, 10, 120, 22);
        contentPane.add(header);

        // Room Number
        JLabel lblRoomNumber = new JLabel("Room Number");
        lblRoomNumber.setForeground(new Color(25, 25, 112));
        lblRoomNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblRoomNumber.setBounds(64, 70, 102, 22);
        contentPane.add(lblRoomNumber);

        tRoomNumber = new JTextField();
        tRoomNumber.setBounds(174, 70, 156, 20);
        contentPane.add(tRoomNumber);

        // Availability
        JLabel lblAvailability = new JLabel("Availability");
        lblAvailability.setForeground(new Color(25, 25, 112));
        lblAvailability.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblAvailability.setBounds(64, 110, 102, 22);
        contentPane.add(lblAvailability);

        cbAvailability = new JComboBox<>(new String[] { "Available", "Occupied" });
        cbAvailability.setBounds(176, 110, 154, 20);
        contentPane.add(cbAvailability);

        // Cleaning Status
        JLabel lblCleaningStatus = new JLabel("Cleaning Status");
        lblCleaningStatus.setForeground(new Color(25, 25, 112));
        lblCleaningStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCleaningStatus.setBounds(64, 150, 102, 22);
        contentPane.add(lblCleaningStatus);

        // Use "Clean" (instead of "Cleaned") to match new schema
        cbCleaningStatus = new JComboBox<>(new String[] { "Clean", "Dirty" });
        cbCleaningStatus.setBounds(176, 150, 154, 20);
        contentPane.add(cbCleaningStatus);

        // Price
        JLabel lblPrice = new JLabel("Price");
        lblPrice.setForeground(new Color(25, 25, 112));
        lblPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPrice.setBounds(64, 190, 102, 22);
        contentPane.add(lblPrice);

        tPrice = new JTextField();
        tPrice.setBounds(174, 190, 156, 20);
        contentPane.add(tPrice);

        // Bed Type
        JLabel lblBedType = new JLabel("Bed Type");
        lblBedType.setForeground(new Color(25, 25, 112));
        lblBedType.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBedType.setBounds(64, 230, 102, 22);
        contentPane.add(lblBedType);

        // Use "Single" and "Double" to match new schema (you can add "Queen" and "King" if needed)
        cbBedType = new JComboBox<>(new String[] { "Single", "Double" });
        cbBedType.setBounds(176, 230, 154, 20);
        contentPane.add(cbBedType);

        // Add Button
        bAdd = new JButton("Add");
        bAdd.addActionListener(this);
        bAdd.setBounds(64, 321, 111, 33);
        bAdd.setBackground(Color.BLACK);
        bAdd.setForeground(Color.WHITE);
        contentPane.add(bAdd);

        // Cancel Button
        bCancel = new JButton("Cancel");
        bCancel.addActionListener(this);
        bCancel.setBounds(198, 321, 111, 33);
        bCancel.setBackground(Color.BLACK);
        bCancel.setForeground(Color.WHITE);
        contentPane.add(bCancel);

        contentPane.setBackground(Color.WHITE);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == bAdd) {
            try {
                // Retrieve and convert input values
                int roomNumber = Integer.parseInt(tRoomNumber.getText());
                String availability = (String) cbAvailability.getSelectedItem();
                String cleaningStatus = (String) cbCleaningStatus.getSelectedItem();
                double price = Double.parseDouble(tPrice.getText());
                String bedType = (String) cbBedType.getSelectedItem();
                
                // Use a prepared statement to insert into the room table
                Conn c = new Conn();
                String query = "INSERT INTO room (room_number, availability, cleaning_status, price, bed_type) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setInt(1, roomNumber);
                pst.setString(2, availability);
                pst.setString(3, cleaningStatus);
                pst.setDouble(4, price);
                pst.setString(5, bedType);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Room Successfully Added");
                this.dispose();
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        } else if(ae.getSource() == bCancel) {
            this.dispose();
        }
    }
}
