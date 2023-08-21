package atmGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	
	Main(){
		JFrame frame = new JFrame();
		JLabel welcome = new JLabel("Welcome to Sam's ATM Console Application");
		JButton Reg = new JButton("Registered User");
		JButton newU = new JButton("new User");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		welcome.setHorizontalAlignment(JLabel.CENTER);
		welcome.setVerticalAlignment(JLabel.CENTER);
		Reg.setBounds(100, 150, 100, 30);
		newU.setBounds(300, 150, 100, 30);
		frame.add(Reg);
		frame.add(newU);
		frame.add(welcome);
		frame.setVisible(true);

		Reg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Register();
				System.out.println("Registered User Selected ... ");
			}
		});
		
		newU.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new newUser();
				System.out.println("New User Selected...");
			}
		});
	}

	public static void main(String[] args) {
		new Main();
	}

}
