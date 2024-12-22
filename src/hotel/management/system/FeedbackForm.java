package hotel.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FeedbackForm extends JFrame implements ActionListener {
    JTextField tfName, tfRoomNumber;
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

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(35, 80, 100, 20);
        lblName.setFont(new Font("Raleway", Font.PLAIN, 16));
        add(lblName);

        tfName = new JTextField();
        tfName.setBounds(150, 80, 150, 25);
        add(tfName);

        JLabel lblRoomNumber = new JLabel("Room No:");
        lblRoomNumber.setBounds(35, 120, 100, 20);
        lblRoomNumber.setFont(new Font("Raleway", Font.PLAIN, 16));
        add(lblRoomNumber);

        tfRoomNumber = new JTextField();
        tfRoomNumber.setBounds(150, 120, 150, 25);
        add(tfRoomNumber);

        JLabel lblRating = new JLabel("Rating:");
        lblRating.setBounds(35, 160, 100, 20);
        lblRating.setFont(new Font("Raleway", Font.PLAIN, 16));
        add(lblRating);

        String[] ratings = {"Excellent", "Good", "Average", "Poor"};
        cbRating = new JComboBox<>(ratings);
        cbRating.setBounds(150, 160, 150, 25);
        cbRating.setBackground(Color.WHITE);
        add(cbRating);

        JLabel lblComments = new JLabel("Comments:");
        lblComments.setBounds(35, 200, 100, 20);
        lblComments.setFont(new Font("Raleway", Font.PLAIN, 16));
        add(lblComments);

        taComments = new JTextArea();
        taComments.setBounds(150, 200, 250, 100);
        taComments.setLineWrap(true);
        taComments.setWrapStyleWord(true);
        add(taComments);

        submit = new JButton("SUBMIT");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setBounds(50, 350, 120, 30);
        submit.addActionListener(this);
        add(submit);

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
            String name = tfName.getText();
            String roomNumber = tfRoomNumber.getText();
            String rating = (String) cbRating.getSelectedItem();
            String comments = taComments.getText();

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO feedback (name, roomNumber, rating, comments) VALUES ('" + name + "', '" + roomNumber + "', '" + rating + "', '" + comments + "')";
                conn.s.executeUpdate(query);
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
