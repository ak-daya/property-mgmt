package userInterface;

import backend.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.border.MatteBorder;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

public class Login extends JFrame {
	//session variable that is used across all SWING pages
	public Session session = null;
	private JPanel contentPane;
	private JTextField emailField;
	private JPasswordField passwordField;
	private final ButtonGroup toggleGuestHost = new ButtonGroup();
	
	
	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)	{
				JOptionPane.showMessageDialog(null, "Thanks for using HomeBreaks!", "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//SWING Window params init
		setBounds(100, 100, 1045, 629);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_main = new JPanel();
		panel_main.setFont(new Font("Dialog", Font.BOLD, 13));
		panel_main.setBackground(new Color(255, 204, 204));
		panel_main.setBounds(332, 0, 707, 600);
		contentPane.add(panel_main);
		panel_main.setLayout(null);
		
		JLabel lblUserType = new JLabel("Select user type:");
		lblUserType.setForeground(new Color(128, 0, 0));
		lblUserType.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUserType.setBounds(538, 224, 109, 27);
		panel_main.add(lblUserType);
		
		JRadioButton radioGuest = new JRadioButton("Guest", true);
		radioGuest.setForeground(new Color(128, 0, 0));
		radioGuest.setFont(new Font("Tahoma", Font.PLAIN, 14));
		radioGuest.setBackground(Color.PINK);
		toggleGuestHost.add(radioGuest);
		radioGuest.setBounds(538, 257, 109, 23);
		panel_main.add(radioGuest);
		
		JRadioButton radioHost = new JRadioButton("Host");
		radioHost.setForeground(new Color(128, 0, 0));
		radioHost.setFont(new Font("Tahoma", Font.PLAIN, 14));
		radioHost.setBackground(Color.PINK);
		toggleGuestHost.add(radioHost);
		radioHost.setBounds(538, 289, 109, 23);
		panel_main.add(radioHost);
		
		JLabel lblTitle = new JLabel("Login");
		lblTitle.setBounds(228, 115, 266, 74);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(102, 102, 102));
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 30));
		panel_main.add(lblTitle);
		
		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.LEFT);
		emailField.setIgnoreRepaint(true);
		emailField.setLocale(Locale.UK);
		emailField.setToolTipText("Enter email address");
		emailField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		emailField.setBounds(299, 230, 170, 37);
		panel_main.add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setToolTipText("Enter password");
		passwordField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		passwordField.setBounds(299, 282, 170, 37);
		panel_main.add(passwordField);
		
		JLabel lbl_emailID = new JLabel("Email ID");
		lbl_emailID.setForeground(new Color(128, 0, 0));
		lbl_emailID.setFont(new Font("Dialog", Font.BOLD, 16));
		lbl_emailID.setBorder(null);
		lbl_emailID.setBounds(221, 240, 66, 16);
		panel_main.add(lbl_emailID);
		
		JLabel lbl_password = new JLabel("Password");
		lbl_password.setForeground(new Color(128, 0, 0));
		lbl_password.setFont(new Font("Dialog", Font.BOLD, 16));
		lbl_password.setBorder(null);
		lbl_password.setBounds(206, 291, 81, 16);
		panel_main.add(lbl_password);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		loginBtn.setForeground(new Color(128, 0, 0));
		
		//Auth user
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				///start
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				char userType = 'n';
				
				String email = emailField.getText().replaceAll("\\s","");
				String password = new String(passwordField.getPassword()).replaceAll("\\s", "");
				if 		(radioGuest.isSelected()) 	userType = 'g';
				else if (radioHost.isSelected())	userType = 'h';
				
				
				//check for incomplete fields
				if (!email.isEmpty() && !password.isEmpty()) {
					
					String sqlQuery = "SELECT email, password FROM Account WHERE email = ?";
					
					try {					
						//establish connection
						con = SQL_Interface.getConnection();
						
						pstmt = con.prepareStatement(sqlQuery);
						pstmt.setString(1, email);
						rs = pstmt.executeQuery();
						
						if (!rs.isBeforeFirst()) {
							JOptionPane.showMessageDialog(null, "Email does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
						//create session
						while (rs.next()) {
							if (InputValidation.checkPassword(password, rs.getString("password"))) {

								if (validUser(email, userType)) {
									Session session = new Session(userType, email);
									session.startSession();
									JOptionPane.showMessageDialog(null, "You've logged in", "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(null, "You cannot log in as that user type", "Error", JOptionPane.ERROR_MESSAGE);
									new Login().setVisible(true);
								}
							}
							else	{
								JOptionPane.showMessageDialog(null, "Password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
								new Login().setVisible(true);
							}
				
						}
					}
					catch (Exception ex) {
							ex.printStackTrace();
					}
					finally {
						try {
							if(rs != null)  	rs.close();
			                if(pstmt != null) 	pstmt.close();
			                if(con != null) 	con.close();
			                
			                dispose();
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}
				else	JOptionPane.showMessageDialog(null, "Some fields may be incomplete. Please check again.", "Error", JOptionPane.ERROR_MESSAGE);
				///end
			}
		});
		
		loginBtn.setFont(new Font("Dialog", Font.BOLD, 17));
		loginBtn.setBounds(319, 355, 133, 42);
		panel_main.add(loginBtn);
		
		JButton contAsEnq = new JButton("Continue Without Login");
		contAsEnq.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		contAsEnq.setForeground(new Color(128, 0, 0));
		contAsEnq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Session session = new Session('e', null);
				session.startSession();
				dispose();
			}
		});
		contAsEnq.setFont(new Font("Dialog", Font.BOLD, 17));
		contAsEnq.setBounds(271, 407, 223, 42);
		panel_main.add(contAsEnq);
		
		JButton signUpBtn = new JButton("Sign Up");
		signUpBtn.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new signupPage().setVisible(true);
				dispose();
			}
		});
		signUpBtn.setForeground(new Color(128, 0, 0));
		signUpBtn.setFont(new Font("Dialog", Font.BOLD, 17));
		signUpBtn.setBounds(546, 25, 133, 42);
		panel_main.add(signUpBtn);
		
		JPanel panel_side = new JPanel();
		panel_side.setBackground(new Color(204, 51, 51));
		panel_side.setBounds(0, 0, 333, 600);
		contentPane.add(panel_side);
		panel_side.setLayout(null);
		
		JLabel lblWelcome = new JLabel("Welcome to");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 28));
		lblWelcome.setForeground(new Color(255, 255, 255));
		lblWelcome.setBounds(1, 181, 311, 120);
		panel_side.add(lblWelcome);
		
		JLabel lblHomebreaks = new JLabel("HomeBreaks PLC!");
		lblHomebreaks.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomebreaks.setForeground(Color.WHITE);
		lblHomebreaks.setFont(new Font("Dialog", Font.BOLD, 28));
		lblHomebreaks.setBounds(1, 244, 311, 87);
		panel_side.add(lblHomebreaks);
	}
	
	public boolean validUser(String email, char loginAttempt) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		boolean isHost = false;
		boolean isGuest = false;

		String sqlQueryH = "SELECT email FROM Host WHERE email = ?";
		String sqlQueryG = "SELECT email FROM Guest WHERE email = ?";
		
		try {					
			//establish connection
			con = SQL_Interface.getConnection();
			
			pstmt = con.prepareStatement(sqlQueryH);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			pstmt2 = con.prepareStatement(sqlQueryG);
			pstmt2.setString(1, email);
			rs2 = pstmt2.executeQuery();
			
			// if host account is not found
			if (!rs.isBeforeFirst()) {
				isHost = false;
			} else {
				// host is found
				isHost = true;
			}
			// if guest account is not found
			if (!rs2.isBeforeFirst()) {
				isGuest = false;
			} else {
				// guest is found
				isGuest = true;
				
			}
		}
		catch (Exception ex) {
				ex.printStackTrace();
		}
		finally {
			try {
				if(rs != null)  	rs.close();
                if(pstmt != null) 	pstmt.close();
                if(con != null) 	con.close();
                dispose();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		if ((loginAttempt == 'h' && isHost) || (loginAttempt == 'g' && isGuest)) {
			return true;
		} else {
			return false;
		}
		
		
	}
}
