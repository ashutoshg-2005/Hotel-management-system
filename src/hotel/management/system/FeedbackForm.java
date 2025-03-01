package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FeedbackForm extends JFrame implements ActionListener {
    JTextField tfCustomerID, tfRoomNumber;
    JTextArea taComments;
    JComboBox<String> cbRating;
    JButton submit, back;

    public FeedbackForm() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel title = new JLabel("FEEDBACK FORM");
        title.setBounds(150, 20, 300, 30);
        title.setFont(new Font("Raleway", Font.PLAIN, 22));
        add(title);

        // Label and text field for Customer ID (instead of Name)
        JLabel lblCustomerID = new JLabel("Customer ID:");
        lblCustomerID.setBounds(35, 80, 100, 20);
        lblCustomerID.setFont(new Font("Raleway", Font.PLAIN, 16));
        add(lblCustomerID);

        tfCustomerID = new JTextField();
        tfCustomerID.setBounds(150, 80, 150, 25);
        add(tfCustomerID);

        // Label and text field for Room Number
        JLabel lblRoomNumber = new JLabel("Room No:");
        lblRoomNumber.setBounds(35, 120, 100, 20);
        lblRoomNumber.setFont(new Font("Raleway", Font.PLAIN, 16));
        add(lblRoomNumber);

        tfRoomNumber = new JTextField();
        tfRoomNumber.setBounds(150, 120, 150, 25);
        add(tfRoomNumber);

        // Rating combobox with textual options
        JLabel lblRating = new JLabel("Rating:");
        lblRating.setBounds(35, 160, 100, 20);
        lblRating.setFont(new Font("Raleway", Font.PLAIN, 16));
        add(lblRating);

        String[] ratings = {"Excellent", "Good", "Average", "Poor"};
        cbRating = new JComboBox<>(ratings);
        cbRating.setBounds(150, 160, 150, 25);
        cbRating.setBackground(Color.WHITE);
        add(cbRating);

        // Comments text area
        JLabel lblComments = new JLabel("Comments:");
        lblComments.setBounds(35, 200, 100, 20);
        lblComments.setFont(new Font("Raleway", Font.PLAIN, 16));
        add(lblComments);

        taComments = new JTextArea();
        taComments.setBounds(150, 200, 250, 100);
        taComments.setLineWrap(true);
        taComments.setWrapStyleWord(true);
        add(taComments);

        // Submit button
        submit = new JButton("SUBMIT");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setBounds(50, 350, 120, 30);
        submit.addActionListener(this);
        add(submit);

        // Back button
        back = new JButton("BACK");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(200, 350, 120, 30);
        back.addActionListener(this);
        add(back);

        setBounds(400, 200, 500, 450);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            // Retrieve input values
            String customerIDStr = tfCustomerID.getText();
            String roomNumberStr = tfRoomNumber.getText();
            String ratingStr = (String) cbRating.getSelectedItem();
            String comments = taComments.getText();

            // Map rating text to a numeric value as a string (matching ENUM values)
            String numericRating;
            if (ratingStr.equals("Excellent")) {
                numericRating = "5";
            } else if (ratingStr.equals("Good")) {
                numericRating = "4";
            } else if (ratingStr.equals("Average")) {
                numericRating = "3";
            } else { // Poor
                numericRating = "2"; // You can choose "1" if desired
            }

            try {
                Conn conn = new Conn();
                // Insert into feedback table with new schema columns
                String query = "INSERT INTO feedback (customer_id, room_number, rating, comments) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setInt(1, Integer.parseInt(customerIDStr));
                pst.setInt(2, Integer.parseInt(roomNumberStr));
                pst.setString(3, numericRating);
                pst.setString(4, comments);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Feedback Submitted Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new FeedbackForm();
    }
}
