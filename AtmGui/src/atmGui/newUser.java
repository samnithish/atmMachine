package atmGui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class newUser {

	JFrame frame = new JFrame("New User");
	JPanel panel = new JPanel();
	JTextField nameField, mobField, Amount;
	JPasswordField pinField, confirmPinField;
	JButton submit, Back;
	Connection con;
	Statement stmt;

	newUser() {

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmDatabase", "root", "password");
			stmt = con.createStatement();
			System.out.println("Server Connected");
		} catch (Exception e) {
			System.out.println("Server not connected...");
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setBackground(Color.white);
		frame.setVisible(true);

		panel.setBackground(Color.white);
		panel.add(new JLabel("Name : "));
		panel.add(nameField = new JTextField(10));
		panel.add(new JLabel("Enter Atm Pin :"));
		panel.add(pinField = new JPasswordField(10));
		panel.add(new JLabel("Re-Enter Atm Pin :"));
		panel.add(confirmPinField = new JPasswordField(10));
		panel.add(new JLabel("Mobile No: "));
		panel.add(mobField = new JTextField(10));
		panel.add(new JLabel("Initial Amount : "));
		panel.add(Amount = new JTextField(10));
		panel.add(submit = new JButton("Submit"));
		panel.add(Back = new JButton("Back"));

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String pin = pinField.getText();
				String Cpin = confirmPinField.getText();
				String mob = mobField.getText();
				String amnt = Amount.getText();

				if(name.isEmpty()){
					JOptionPane.showMessageDialog(frame, "Enter the Name Please");
				} else if (pin.isEmpty()){
					JOptionPane.showMessageDialog(frame, "Enter the pin Please");
				} else if (Cpin.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Re-Enter the pin Please");
				} else if (mob.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Enter the Mobile No Please. \n its a Key to U");
				} else if (amnt.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Enter the Amount please\n MInimum ");
				} else {
					if (pin.equals(Cpin)) {
						int bal = Integer.parseInt(amnt);
						if (bal >= 1000) {
							addUser();
						} else {
							JOptionPane.showMessageDialog(frame, "Minimun Amount Should be 1000");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Please enter the Same pin Both Boxes");
					}
				}
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

		frame.add(panel);

	}

	private void addUser() {
		String name = nameField.getText();
		int pin = Integer.parseInt(pinField.getText());
		String phoneNo = mobField.getText();
		int amountIn = Integer.parseInt(Amount.getText());

		String query2 = "insert into userdetails (userName, phoneNo, atmPin, amountIn) values ('" + name + "','"
				+ phoneNo + "'," + pin + "," + amountIn + ")";

		try {
			stmt.executeUpdate(query2);
			System.out.println("new User Registered");
			System.out.println("Back to Main");
			JOptionPane.showMessageDialog(frame,
					"New User Registered !\nYou Should keep the mobile Number and Pin very carefully !\nyour Pin should be remembered Always !");
			frame.dispose();
			new Main();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Something Wrong in our Side");
		}

	}

}
