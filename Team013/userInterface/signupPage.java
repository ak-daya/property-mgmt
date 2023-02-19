package userInterface;

import backend.*;
import java.sql.*;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.mindrot.jbcrypt.BCrypt;		//https://jar-download.com/artifacts/org.mindrot/jbcrypt

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class signupPage extends JFrame {

	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private JTextField txtDoorNumber;
	private JTextField txtStreetName;
	private JTextField txtAreaName;
	private JTextField txtPostcode;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtMobileNumber;
	private JTextField txtEmail;
	private JPasswordField passwordField;
	private JPasswordField confirmPassword;
	private final ButtonGroup toggleGuest = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	public signupPage() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)	{
				JOptionPane.showMessageDialog(null, "Thanks for using HomeBreaks!", "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		setBounds(100, 100, 1045, 629);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 1045, 600);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 1033, 588);
		panel_3.add(scrollPane);
		scrollPane.setViewportView(panel);
		panel.setFont(new Font("Dialog", Font.BOLD, 13));
		panel.setBackground(new Color(255, 204, 204));
		panel.setLayout(null);
		
		JLabel lblSignUp = new JLabel("Sign Up");
		lblSignUp.setBounds(599, 19, 170, 33);
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setForeground(new Color(102, 102, 102));
		lblSignUp.setFont(new Font("Dialog", Font.BOLD, 30));
		panel.add(lblSignUp);
		
		txtDoorNumber = new JTextField();
		txtDoorNumber.setBounds(619, 367, 171, 27);
		txtDoorNumber.setToolTipText("Enter Door Number");
		txtDoorNumber.setLocale(Locale.UK);
		txtDoorNumber.setIgnoreRepaint(true);
		txtDoorNumber.setHorizontalAlignment(SwingConstants.LEFT);
		txtDoorNumber.setColumns(10);
		txtDoorNumber.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(txtDoorNumber);
		
		JLabel lblDoorNumber = new JLabel("Door Number");
		lblDoorNumber.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblDoorNumber.setBounds(527, 369, 91, 16);
		lblDoorNumber.setToolTipText("Enter Door Number");
		lblDoorNumber.setBorder(null);
		panel.add(lblDoorNumber);
		
		JComboBox titleSelect = new JComboBox();
		titleSelect.setModel(new DefaultComboBoxModel(new String[] {"Mr", "Mrs", "Ms", "Dr"}));
		titleSelect.setBounds(620, 91, 79, 27);
		titleSelect.setToolTipText("Select Title");
		panel.add(titleSelect);

		JRadioButton rdbtnGuest = new JRadioButton("Guest", true);
		rdbtnGuest.setForeground(new Color(128, 0, 0));
		rdbtnGuest.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnGuest.setBackground(Color.PINK);
		toggleGuest.add(rdbtnGuest);
		rdbtnGuest.setBounds(871, 289, 109, 23);
		panel.add(rdbtnGuest);
		
		JRadioButton rdbtnHost = new JRadioButton("Host");
		rdbtnHost.setForeground(new Color(128, 0, 0));
		rdbtnHost.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnHost.setBackground(Color.PINK);
		toggleGuest.add(rdbtnHost);
		rdbtnHost.setBounds(871, 315, 109, 23);
		panel.add(rdbtnHost);

		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setForeground(new Color(128, 0, 0));
		btnSignUp.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				
				//initialize variables
				String email_input = txtEmail.getText().trim();
				String email = "";		
				String password_input = new String(passwordField.getPassword()).replaceAll("\\s","");
				String passwordConfirm_input = new String(confirmPassword.getPassword()).replaceAll("\\s","");
				String password_final = "";
				String title = titleSelect.getSelectedItem().toString();		
				String fname_input = txtFirstName.getText().trim();
				String fname = "";												
				String lname_input = txtLastName.getText().trim();
				String lname = "";												
				String mobileNumber_input = txtMobileNumber.getText();								
				String mobileNumber_final = "";
				//int mobileNumber = 0;
				Long mobileNumber = null;
				String doorNumber_input = txtDoorNumber.getText().trim();
				String doorNumber_final = "";
				int doorNumber = 0;
				String streetName_input = txtStreetName.getText().trim();
				String streetName = "";
				String areaName_input = txtAreaName.getText().trim();
				String areaName = "";
				String postcode_input = txtPostcode.getText().trim();
				String postcode = "";
				char userType = 'n';
				int primary_key_ID = -1;
				
				//SQL variables
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				//PreparedStatement pstmtH = null;
				PreparedStatement pstmt3 = null;
				ResultSet keys = null;
				
				
				//check for incomplete fields
				if (email_input.isEmpty() || password_input.isEmpty() || passwordConfirm_input.isEmpty()
						|| fname_input.isEmpty() || lname_input.isEmpty()	|| mobileNumber_input.isEmpty() 
						|| doorNumber_input.isEmpty() || streetName_input.isEmpty() || areaName_input.isEmpty() || postcode_input.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Some fields may be incomplete. Please check again.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
				
					//input validation (length and format)
					//email
					
					if(InputValidation.patternMatches(email_input, "email")) {	
						if(email_input.length() <= 100) {
							email = txtEmail.getText().trim();
						}
						else JOptionPane.showMessageDialog(null, "Email field must not exceed 100 characters.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else JOptionPane.showMessageDialog(null, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
					//password
					
					password_input = new String(passwordField.getPassword()).replaceAll("\\s","");
					passwordConfirm_input = new String(confirmPassword.getPassword()).replaceAll("\\s","");
          
					if(password_input.equals(passwordConfirm_input))	{
						if(InputValidation.patternMatches(password_input, "password")) {
							password_final = BCrypt.hashpw(password_input, BCrypt.gensalt()); 
						}
						else	JOptionPane.showMessageDialog(null, "Password must contain at least one number, capital letter, "
								+ "special character (e.g. !, @, #, &) and must be 8-32 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else	JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
					//first name and last name
					
					if(fname_input.length() <= 45 && lname_input.length() <=45) {
						fname = fname_input;
						lname = lname_input;
					}
					else	JOptionPane.showMessageDialog(null, "Name fields may not be longer than 45 characters.", "Error", JOptionPane.ERROR_MESSAGE);
					//mobile number
					
					if(InputValidation.patternMatches(mobileNumber_input, "mobileNumber")) {
						if (mobileNumber_input.length() <= 11 && mobileNumber_input.length() >=10) {
							mobileNumber_final = mobileNumber_input;
							//mobileNumber = Integer.parseInt(mobileNumber_final);
							mobileNumber = Long.parseLong(mobileNumber_final);
						} 
						else JOptionPane.showMessageDialog(null, "Mobile number must be between 9 and 11 digits.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else JOptionPane.showMessageDialog(null, "Mobile number format invalid.", "Error", JOptionPane.ERROR_MESSAGE);
					//door number					
					
					if (doorNumber_input.length() <= 11) {
						doorNumber_final = doorNumber_input;
						doorNumber = Integer.parseInt(doorNumber_final);
					}
					else JOptionPane.showMessageDialog(null, "Door number field may not be longer than 11 digits .", "Error", JOptionPane.ERROR_MESSAGE);
					
					//street name and area name
					if(streetName_input.length() <= 45) {
						streetName = streetName_input;
					}
					else	JOptionPane.showMessageDialog(null, "Street name field may not be longer than 45 characters.", "Error", JOptionPane.ERROR_MESSAGE);
					
					if(areaName_input.length() <=45) {
						areaName = areaName_input;
					}
					else	JOptionPane.showMessageDialog(null, "Area name fields may not be longer than 45 characters.", "Error", JOptionPane.ERROR_MESSAGE);
					
					//postcode
					if(postcode_input.length() <= 8) {
						postcode = postcode_input;
					}
					else	JOptionPane.showMessageDialog(null, "Postcode field may not be longer than 8 characters.", "Error", JOptionPane.ERROR_MESSAGE);
					//user type
					if 		(rdbtnGuest.isSelected()) 	userType = 'g';
					else if (rdbtnHost.isSelected())	userType = 'h';
					
					
					if(!email.isEmpty() && !password_final.isEmpty() && !fname.isEmpty() && !lname.isEmpty()
							&& !mobileNumber_final.isEmpty() && !doorNumber_final.isEmpty() && !streetName.isEmpty()
							&& !areaName.isEmpty() && !postcode.isEmpty()) {
						//SQL queries
						String sqlQuery = "INSERT INTO Account(email, password, title, fname, lname, mobileNo, doorNumber, streetName, areaName, postcode)" + "VALUES(?,?,?,?,?,?,?,?,?,?)";
						String sql_accountType = "";
						//String sql_hostSignup = "";
						
						if (userType == 'g') {
							sql_accountType = "INSERT INTO Guest(email)" + "VALUES(?)";
							//sql_hostSignup = null;
						}
						else if (userType == 'h') {
							sql_accountType = "INSERT INTO Host(email)" + "VALUES(?)";
							//sql_guestSignup = "INSERT INTO Guest(email)" + "VALUES(?)";
						}
						
						try {					
							//establish connection
							con = SQL_Interface.getConnection();
							con.setAutoCommit(false);
							
							pstmt = con.prepareStatement(sqlQuery);			//insert new row into Account
							pstmt.setString(1, email);
							pstmt.setString(2, password_final);
							pstmt.setString(3, title);
							pstmt.setString(4, fname);
							pstmt.setString(5, lname);
							pstmt.setLong(6, mobileNumber);
							pstmt.setInt(7, doorNumber);	
							pstmt.setString(8, streetName);
							pstmt.setString(9, areaName);
							pstmt.setString(10, postcode);
							
							pstmt2 = con.prepareStatement(sql_accountType, Statement.RETURN_GENERATED_KEYS);							//insert new row into Guest or Host
							pstmt2.setString(1, email);	
							
							int affectedRows_account = pstmt.executeUpdate();
							int affectedRows_userType = pstmt2.executeUpdate();
							
							if(affectedRows_account == 1 && affectedRows_userType == 1){
								
								keys = pstmt2.getGeneratedKeys();													//returns email
								
								if(keys.next()) {
									primary_key_ID = keys.getInt(1);
									//commit changes to DB
									con.commit();
									
									if (userType == 'g') {
										JOptionPane.showMessageDialog(null, "You've signed up as a Guest!", "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
									}
									else if (userType == 'h') {
										JOptionPane.showMessageDialog(null, "You've signed up as a Host!", "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
									}
									
								}
							}
							else {	
								con.rollback(); 
								JOptionPane.showMessageDialog(null, "Sign-up failed!", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						catch (Exception ex) {
							try {
								if (con != null)	con.rollback();
							}
							catch(Exception ex1) {
								ex1.printStackTrace();
							}
							ex.printStackTrace();	
						}
						finally {
							try {
								if(keys != null)  	keys.close();
				                if(pstmt != null) 	pstmt.close();
				                if(pstmt2 != null)	pstmt2.close();
				                if(pstmt3 != null)  pstmt3.close();
				                if(con != null) 	con.close();
				                
				                //create a new session when commit is OK
				                new Login().setVisible(true);
				                dispose();
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
						}
					}	
				}//end of incomplete field check if block
			}
		});
		
		btnSignUp.setBounds(647, 526, 99, 33);
		btnSignUp.setToolTipText("Click to Sign Up");
		btnSignUp.setFont(new Font("Dialog", Font.BOLD, 14));
		panel.add(btnSignUp);
		
		JLabel lblSignUpIntro = new JLabel("Sign Up to your personalised HomeBreaks account");
		lblSignUpIntro.setBounds(490, 54, 386, 33);
		lblSignUpIntro.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUpIntro.setForeground(new Color(128, 0, 0));
		lblSignUpIntro.setFont(new Font("Dialog", Font.BOLD, 15));
		panel.add(lblSignUpIntro);
		
		txtStreetName = new JTextField();
		txtStreetName.setBounds(619, 406, 171, 27);
		txtStreetName.setToolTipText("Enter Street Name");
		txtStreetName.setLocale(Locale.UK);
		txtStreetName.setIgnoreRepaint(true);
		txtStreetName.setHorizontalAlignment(SwingConstants.LEFT);
		txtStreetName.setColumns(10);
		txtStreetName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(txtStreetName);
		
		JLabel lblStreetName = new JLabel("Street Name");
		lblStreetName.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblStreetName.setBounds(529, 408, 89, 16);
		lblStreetName.setToolTipText("Enter Street Name");
		lblStreetName.setBorder(null);
		panel.add(lblStreetName);
		
		txtAreaName = new JTextField();
		txtAreaName.setBounds(619, 445, 171, 27);
		txtAreaName.setToolTipText("Enter Area Name");
		txtAreaName.setLocale(Locale.UK);
		txtAreaName.setIgnoreRepaint(true);
		txtAreaName.setHorizontalAlignment(SwingConstants.LEFT);
		txtAreaName.setColumns(10);
		txtAreaName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(txtAreaName);
		
		JLabel lblAreaName = new JLabel("Area Name");
		lblAreaName.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblAreaName.setBounds(539, 447, 79, 16);
		lblAreaName.setToolTipText("Enter City / County / Area");
		lblAreaName.setBorder(null);
		panel.add(lblAreaName);
		
		JLabel lblPostCode = new JLabel("Postcode");
		lblPostCode.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPostCode.setBounds(548, 490, 70, 16);
		lblPostCode.setToolTipText("Enter Postcode");
		lblPostCode.setBorder(null);
		panel.add(lblPostCode);
		
		txtPostcode = new JTextField();
		txtPostcode.setBounds(619, 489, 171, 27);
		txtPostcode.setToolTipText("Enter Postcode");
		txtPostcode.setLocale(Locale.UK);
		txtPostcode.setIgnoreRepaint(true);
		txtPostcode.setHorizontalAlignment(SwingConstants.LEFT);
		txtPostcode.setColumns(10);
		txtPostcode.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(txtPostcode);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 333, 584);
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(204, 51, 51));
		panel.add(panel_1);
		
		JLabel lblWelcome = new JLabel("Welcome to");
		lblWelcome.setBounds(43, 220, 253, 74);
		panel_1.add(lblWelcome);
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setForeground(Color.WHITE);
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 28));
		
		JLabel lblHomebreaks = new JLabel("HomeBreaks PLC!");
		lblHomebreaks.setBounds(16, 270, 311, 87);
		panel_1.add(lblHomebreaks);
		lblHomebreaks.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomebreaks.setForeground(Color.WHITE);
		lblHomebreaks.setFont(new Font("Dialog", Font.BOLD, 28));
		
		txtFirstName = new JTextField();
		txtFirstName.setForeground(Color.BLACK);
		txtFirstName.setBounds(620, 130, 171, 27);
		txtFirstName.setToolTipText("Enter Name");
		txtFirstName.setLocale(Locale.UK);
		txtFirstName.setIgnoreRepaint(true);
		txtFirstName.setHorizontalAlignment(SwingConstants.LEFT);
		txtFirstName.setColumns(10);
		txtFirstName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(txtFirstName);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblFirstName.setBounds(536, 133, 82, 16);
		lblFirstName.setBorder(null);
		panel.add(lblFirstName);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblTitle.setBounds(566, 96, 40, 16);
		lblTitle.setBorder(null);
		panel.add(lblTitle);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblLastName.setBounds(537, 172, 81, 16);
		lblLastName.setBorder(null);
		panel.add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(620, 169, 171, 27);
		txtLastName.setToolTipText("Enter Last Name");
		txtLastName.setLocale(Locale.UK);
		txtLastName.setIgnoreRepaint(true);
		txtLastName.setHorizontalAlignment(SwingConstants.LEFT);
		txtLastName.setColumns(10);
		txtLastName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(txtLastName);
		
		JLabel lblMobNo = new JLabel("Mobile Number");
		lblMobNo.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblMobNo.setBounds(520, 210, 98, 16);
		lblMobNo.setBorder(null);
		panel.add(lblMobNo);
		
		txtMobileNumber = new JTextField();
		txtMobileNumber.setBounds(620, 208, 171, 27);
		txtMobileNumber.setToolTipText("Enter Mobile Number");
		txtMobileNumber.setLocale(Locale.UK);
		txtMobileNumber.setIgnoreRepaint(true);
		txtMobileNumber.setHorizontalAlignment(SwingConstants.LEFT);
		txtMobileNumber.setColumns(10);
		txtMobileNumber.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(txtMobileNumber);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblEmail.setBounds(566, 255, 52, 16);
		lblEmail.setBorder(null);
		panel.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(620, 252, 171, 27);
		txtEmail.setToolTipText("Enter Email");
		txtEmail.setLocale(Locale.UK);
		txtEmail.setIgnoreRepaint(true);
		txtEmail.setHorizontalAlignment(SwingConstants.LEFT);
		txtEmail.setColumns(10);
		txtEmail.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(txtEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPassword.setBounds(545, 294, 73, 16);
		lblPassword.setBorder(null);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(620, 291, 171, 27);
		passwordField.setToolTipText("Enter Password");
		passwordField.setEchoChar('*');
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(passwordField);
		
		JLabel lblCfPassword = new JLabel("Confirm Password");
		lblCfPassword.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblCfPassword.setBounds(503, 335, 115, 16);
		lblCfPassword.setBorder(null);
		panel.add(lblCfPassword);
		
		confirmPassword = new JPasswordField();
		confirmPassword.setBounds(620, 330, 171, 27);
		confirmPassword.setToolTipText("Confirm Password");
		confirmPassword.setEchoChar('*');
		confirmPassword.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.add(confirmPassword);
		
		JLabel lblUserType = new JLabel("Select user type:");
		lblUserType.setForeground(new Color(128, 0, 0));
		lblUserType.setFont(new Font("Dialog", Font.BOLD, 13));
		lblUserType.setBounds(871, 255, 109, 27);
		panel.add(lblUserType);
		
		JButton btnLogin = new JButton("Back to Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login().setVisible(true);
				dispose();
			}
		});
		btnLogin.setToolTipText("Click to go back to Login");
		btnLogin.setForeground(new Color(128, 0, 0));
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 14));
		btnLogin.setBounds(343, 526, 141, 33);
		panel.add(btnLogin);
		
	}
}
