package hotel.management.system;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Dashboard extends JFrame{

    public static void main(String[] args) {
        new Dashboard().setVisible(true);
    }
    
    public Dashboard() {
        super("HOTEL MANAGEMENT SYSTEM");
	
        setForeground(Color.CYAN);
        setLayout(null); 

        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/third.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1950, 1000,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2); 
	JLabel NewLabel = new JLabel(i3);
	NewLabel.setBounds(0, 0, 1950, 1000); 
        add(NewLabel);
        
        JLabel Dashboard = new JLabel("THE ROYAL ORCHID WELCOMES YOU");
	Dashboard.setForeground(Color.WHITE);
        Dashboard.setFont(new Font("Tahoma", Font.PLAIN, 46));
	Dashboard.setBounds(600, 60, 1000, 85);
	NewLabel.add(Dashboard);
        
		
		
        JMenuBar menuBar = new JMenuBar();
	setJMenuBar(menuBar);
		
        JMenu Management = new JMenu("HOTEL MANAGEMENT");
        Management.setForeground(Color.BLUE);
	menuBar.add(Management);
		
        JMenuItem Reception = new JMenuItem("RECEPTION");
	Management.add(Reception);
        Reception.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    new Reception().setVisible(true);
                }catch(Exception e ){}
            }
	});
		
	JMenu Admin = new JMenu("ADMIN");
        Admin.setForeground(Color.RED);
	menuBar.add(Admin);
        
        JMenuItem Employee = new JMenuItem("ADD EMPLOYEE");
	Admin.add(Employee);
        Employee.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    new AddEmployee().setVisible(true);
                }catch(Exception e ){}
            }
	});
    

        JMenuItem Add_rooms = new JMenuItem("ADD ROOMS");
	Admin.add(Add_rooms);
        Add_rooms.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    new AddRooms().setVisible(true);
                }catch(Exception e ){}
            }
	});
     
        
        JMenuItem Add_drivers = new JMenuItem("ADD DRIVERS");
	Admin.add(Add_drivers);
        Add_drivers.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    new AddDrivers().setVisible(true);
                }catch(Exception e ){}
            }
	});
        JMenu Feedback = new JMenu("FEEDBACK");
        Feedback.setForeground(Color.MAGENTA);
	menuBar.add(Feedback);
        
        JMenuItem FeedbackForm = new JMenuItem("OPEN FEEDBACK FORM");
        Feedback.add(FeedbackForm);
        FeedbackForm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    new FeedbackForm().setVisible(true);
                } catch(Exception e) {}
            }
        });
        
        
		
        setSize(1950,1090);
	setVisible(true);
        getContentPane().setBackground(Color.WHITE);
    }
}
