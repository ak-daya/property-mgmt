package backend;

import java.sql.*;

/* 
 * Class for Guest Elements
*/

public class Guest extends Person{
	int guestID;
	
	public Guest(String email, String title, String fname, String lname, int mobileNum) {
		super(email, title, fname, lname, mobileNum);
	}
 
}
