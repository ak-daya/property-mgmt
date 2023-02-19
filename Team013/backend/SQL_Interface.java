package backend;

import java.sql.*;
import java.util.*;

/* 
 * Class for establishing connection
*/


public class SQL_Interface {
	public Connection con = null;
	
	
	public static Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team013", "team013", "821b7157");
			return conn;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}