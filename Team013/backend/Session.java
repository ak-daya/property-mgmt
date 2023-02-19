package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import userInterface.searchPage;
import userInterface.homepageGuest;
import userInterface.homepageHost;
import userInterface.signupPage;

/* 
 * Class for Session store and calling
*/

public class Session {
	private char userType = 'n';		//can be 'g' for guest or 'h' for host
	private String email = "no email";
	
	public Session(char userType, String email) {
		this.userType = userType;
		this.email = email;
	}
	
	public char getUserType() {	return this.userType; }
	public String getEmail() { return this.email; }
	
	// What to do for each user upon login
	public void startSession() {
		if(this.userType == 'h') {
			// Open host homepage
			new homepageHost(this).setVisible(true);
		}
		else if (this.userType == 'g') {
			// Open guest homepage
			new homepageGuest(this).setVisible(true);
		}
		else if (this.userType == 'e') {
			// Open search page
			new searchPage(this).setVisible(true);
		}
		else {	JOptionPane.showMessageDialog(null, "User type error", "Error", JOptionPane.ERROR_MESSAGE);	}
	}
}
