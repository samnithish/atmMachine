package atmGui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Operations {

	static String phoneNo;
	static String atmPin;
	static Connection con;
	static Statement stmt;

	public static void Window(String phoneNo, String atmPin){
		Operations.atmPin=atmPin;
		Operations.phoneNo=phoneNo;


		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmDatabase", "root", "Sam*16052001");
			stmt = con.createStatement();
		} catch (SQLException e) {
			System.out.println("Database Not Connected");
		}


		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setBackground(Color.white);
		frame.setVisible(true);
		JButton button1 = new JButton("Deposit");
		JButton button2 = new JButton("Withdraw");
		JButton button3 = new JButton("Pin Change");
		JButton Back = new JButton("Back");

		JPanel panel = new JPanel();
		frame.add(panel);

		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(Back);
		

		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Deposit(phoneNo, atmPin);
				} catch (SQLException e1) {
					System.out.println("Error Alert...");
					JOptionPane.showMessageDialog(frame, "Sorry ! we cannot Change the pin Right now !.. Try Later...");
				}
			}
			
		});

		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Withdraw(phoneNo, atmPin);
				} catch (SQLException e1) {
					System.out.println("Error Alert...");
					JOptionPane.showMessageDialog(frame, "Sorry ! we cannot Change the pin Right now !.. Try Later...");
				}
			}

		});

		button3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pinChange(phoneNo, atmPin);
				} catch (SQLException e1) {
					System.out.println("Error Alert...");
					JOptionPane.showMessageDialog(frame, "Sorry ! we cannot Change the pin Right now !.. Try Later...");
				}
			}
	
		});

		Back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Register();
				System.out.println("Back to Exited User Page...");
			}
		});

	}

	public static void Deposit(String phoneNo, String atmPin) throws SQLException{

		String toDep = "Select * from userdetails where phoneNo = " + phoneNo;
		ResultSet rs = stmt.executeQuery(toDep);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setBackground(Color.white);
		frame.setVisible(true);

		JPanel panel = new JPanel();
		frame.add(panel);

		JTextField Amount = new JTextField(10);
		JButton submit = new JButton("Deposit");
		JButton Back = new JButton("Back");
		panel.add(Amount);
		panel.add(submit);
		panel.add(Back);



		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.next()){
						int depAmount = Integer.parseInt(Amount.getText());
						int Curbalance = depAmount+rs.getInt("amountIn");
						String deposit = "update userdetails set amountIn = " + Curbalance + " where phoneNo = "+ phoneNo;
						stmt.executeUpdate(deposit);
						JOptionPane.showMessageDialog(frame,"Deposited !"+
						"\nYour Acc. Balance is "+Curbalance);
						frame.dispose();
						new Main();
						System.out.println("Back to Main");
					}
				} catch (NumberFormatException | SQLException e1) {
					
				}
			}
			
		});

		Back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Operations.Window(phoneNo, atmPin);
				System.out.println("Back to Operations");
			}
		});

	}

	private static void Withdraw(String phoneNo, String atmPin) throws SQLException {

		String withD = "Select * from userdetails where phoneNo = " + phoneNo;
		ResultSet rs = stmt.executeQuery(withD);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setBackground(Color.white);
		frame.setVisible(true);

		JPanel panel = new JPanel();
		frame.add(panel);

		JTextField Amount = new JTextField(10);
		JButton submit = new JButton("Withdraw");
		JButton Back = new JButton("Back");
		panel.add(Amount);
		panel.add(submit);
		panel.add(Back);

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(rs.next()){
						int WithdrawAm = Integer.parseInt(Amount.getText());
						int Curbalance = rs.getInt("amountIn")-WithdrawAm;
						if(Curbalance<1000){
							JOptionPane.showMessageDialog(frame,"The withDrael amount Should not be Less than 1000");
						} else {
							String wthd = "update userdetails set amountIn = " + Curbalance + " where phoneNo = "+ phoneNo;
							stmt.executeUpdate(wthd);
							JOptionPane.showMessageDialog(frame,"WithDrawed !"+
							"\nYour Acc. Balance is "+Curbalance);
							frame.dispose();
							new Main();
							System.out.println("Back to Main");
						}
					}
				} catch (NumberFormatException | SQLException e1) {
					
				}
			}
			
		});

		Back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Operations.Window(phoneNo, atmPin);
				System.out.println("Back to Operations");
			}
		});

	}

	private static void pinChange(String phoneNo, String atmPin) throws SQLException {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setBackground(Color.white);
		frame.setVisible(true);

		JPanel panel = new JPanel();
		frame.add(panel);

		JTextField newPin  = new JTextField(10);
		JTextField RnewPin  = new JTextField(10);
		JButton submit = new JButton("Submit");
		JButton Back = new JButton("Back");
		panel.add(newPin);
		panel.add(RnewPin);
		panel.add(submit);
		panel.add(Back);

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(newPin.getText().equals(RnewPin.getText())){
					try {
						stmt.executeUpdate("update userdetails set atmPin = "+ newPin.getText() + " where phoneNo = "+ phoneNo);
						JOptionPane.showMessageDialog(frame, "pin Changed");
						new Main();
						System.out.println("Back to Main");
					} catch (SQLException e1) {
						System.out.println("Error Alert...");
						JOptionPane.showMessageDialog(frame, "Sorry ! we cannot Change the pin Right now !.. Try Later...");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please enter Same new pin Both the Boxes");
				}
			}
			
		});
		
		Back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Operations.Window(phoneNo, atmPin);
				System.out.println("Back to Operations");
			}
		});

	}

}
