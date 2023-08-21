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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register {

	JFrame frame = new JFrame();
	JPanel Reg = new JPanel();
	JLabel user = new JLabel("Mobile No : ");
	JTextField userField = new JTextField(10);
	JLabel pin = new JLabel("Atm Pin : ");
	JTextField pinField = new JTextField(10);
	JButton submit = new JButton("Submit");
	JButton Back;
	Connection con;
	Statement stmt;

	Register() {

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmDatabase", "root", "Sam*16052001");
			stmt = con.createStatement();
		} catch (Exception e) {
			System.out.println("Server not connected...");
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setBackground(Color.white);

		user.setBounds(100, 200, 100, 50);
		userField.setBounds(300, 200, 100, 50);
		pin.setBounds(100, 300, 100, 50);
		pinField.setBounds(300, 300, 100, 50);
		submit.setBounds(200, 350, 100, 50);

		Reg.setBackground(Color.white);
		Reg.add(user);
		Reg.add(userField);
		Reg.add(pin);
		Reg.add(pinField);
		Reg.add(submit);
		Reg.add(Back = new JButton("Back"));

		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}

		});

		Back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Main();
				System.out.println("Back to Main");
			}
		});

		frame.add(Reg);
		frame.setVisible(true);

	}

	private void login() {
		String phoneNo = userField.getText();
		String atmPin = pinField.getText();
		if(phoneNo.isEmpty()||atmPin.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please fill Every Fields");
		} else {
			try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM userdetails WHERE phoneNo = '" + phoneNo + "'");
			if (rs.next()) {
				if (atmPin.equals(rs.getString("atmPin"))) {
					int balance = rs.getInt("amountIn");
					JOptionPane.showMessageDialog(frame,
							"Welcome " + rs.getString("userName") + "\nYour Balance: " + balance);
					System.out.println("User Check in ... Moving on to Operations");
					Operations.Window(phoneNo, atmPin);
					userField.setText("");
					pinField.setText("");
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "Wrong PIN Number");
				}
			} else {
				JOptionPane.showMessageDialog(frame, "User not found");
			}
			} catch (SQLException e) {
				System.out.println("Something Wrong...");
			}
		}
	}
}
