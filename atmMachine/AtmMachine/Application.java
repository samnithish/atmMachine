package atmMachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Application {
	public static void main(String[] args) {
		atm();
	}

	public static void atm() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmDatabase", "root","password");
			Statement stmt = con.createStatement();
			Scanner s = new Scanner(System.in);
			while (true) {
				System.out.println("\n\n\tWelcome to Sam's Console Atm Application");
				System.out.println("Select Options :\n\n\t1.Registered User.\n\n\t2.New User");
				int usertype = s.nextInt();
				while (usertype != 1 && usertype != 2) {
					System.out.println("Select Options between the Options :\n\n\t1.Registered User.\n\n\t2.New User");
					usertype = s.nextInt();
				}
				switch (usertype) {
				case 1:
					System.out.println("Enter your phoneNo : ");
					String phoneNo = s.next();
					String welcome = "Select * from userdetails where phoneNo = " + phoneNo;
					ResultSet rs = stmt.executeQuery(welcome);
					if (rs.next()) {
						System.out.println("Welcome " + rs.getString("userName") + "\n\n Please Enter your Secret Pin Number :");
						int enteredPin = s.nextInt();
						if (enteredPin == rs.getInt("atmPin")) {
							System.out.println("\nyour Balance is " + rs.getInt("amountIn"));
							System.out.println("What do you want to Do : \n\t1.Deposit Amount\n\t2.Withdraw Amount\n\t3.Pin Change");
							int choice = s.nextInt();
							while (choice != 1 && choice != 2 && choice != 3) {
								System.out.println("What do you want to Do : \n\t1.Deposit Amount\n\t2.Withdraw Amount\n\t3.Pin Change\n\nChoose between the Options");
								choice = s.nextInt();
							}
							switch (choice) {
							case 1:
								System.out.println("Enter the Amount you want to Deposit : ");
								int depositingAmount = s.nextInt();
								int Curbalance = rs.getInt("amountIn") + depositingAmount;
								String deposit = "update userdetails set amountIn = " + Curbalance + " where phoneNo = "+ phoneNo;
								stmt.executeUpdate(deposit);
								System.out.println("Amount Deposited ! ... your Balance is " + Curbalance);
								break;
							case 2:
								System.out.println("Enter the Amount you Want to Withdraw : ");
								int withdrawAmt = s.nextInt();
								int CurrentBalance = rs.getInt("amountIn") - withdrawAmt;
								String withdraw = "update userdetails set amountIn = " + CurrentBalance + " where phoneNo = " + phoneNo;
								stmt.executeUpdate(withdraw);
								System.out.println("Amount Withdrawed ! ... your Balance is " + CurrentBalance);
								break;
							case 3:
								System.out.println("Please enter the Pin again for the Security Purposes : ");
								int ent = s.nextInt();
								while(ent!=rs.getInt("atmPin")){
									System.out.println("Please enter the Correct Pin again for the Security Purposes : ");
								 	ent = s.nextInt();
								}
								if (ent == rs.getInt("atmPin")) {
									System.out.println("Enter Your New Pin : ");
									int rePin = s.nextInt();
									String pinChange = "update userdetails set atmPin = " + rePin + " where phoneNo = "+ phoneNo;
									stmt.executeUpdate(pinChange);
									System.out.println("Pin Changed ! please remember Your Pin");
								}
							}
						} else {
							System.out.println("Wrong Pin Number");
						}
					}

					else {
						System.out.println("Sorry! you Are not a Registered User.. Please Register as a new User... ThankYou !");
					}
					break;
				case 2:
					System.out.println("Please Enter UserDetails: ");
					System.out.println("Enter your Name Sir/Mam : ");
					String userName = s.next();
					System.out.println("Enter your phone Number : ");
					String phoneNu = s.next();
					System.out.println("Enter your Pin Number : ");
					int fpin = s.nextInt();
					System.out.println("Enter your Pin Number Again : ");
					int spin = s.nextInt();
					while (fpin != spin) {
						System.out.println("Something went Wrong ... U should enter the Same pin !");
						spin = s.nextInt();
					}
					System.out.println("Initially u should Withdraw Rs.1000\nPlease withdraw minimum 1000 Rs.");
					System.out.println("Enter the Amount want to Withdraw : ");
					int amount = s.nextInt();
					while (amount < 1000) {
						System.out.println("Please withdrwaw mimimum Rs.1000");
						amount = s.nextInt();
					}
					int Pin = spin;
					String query2 = "insert into userdetails (userName, phoneNo, atmPin, amountIn) values ('" + userName+ "','" + phoneNu + "'," + Pin + "," + amount + ")";
					stmt.executeUpdate(query2);
					System.out.println("New User Registered !");
					System.out.println("You Should keep the mobile Number and Pin very carefully !");
					System.out.println("your Pin should be remembered Always !");
					break;
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Some error occured 404");
		}
	}
}
