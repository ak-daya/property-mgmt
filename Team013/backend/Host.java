package backend;

import java.sql.*;

import javax.swing.JOptionPane;

import userInterface.Login;

/* 
 * Class for Host Elements
*/

public class Host extends Person{
	boolean superHost;
	int hostID;
	
	public Host(String email, String title, String fname, String lname, int mobileNum) {
		super(email, title, fname, lname, mobileNum);
	}
	
	/* 
	 * Class for calculating and validating Superhost status
	*/
	
	public static boolean isOwnerSuperhost(int propertyID) {
		boolean isSuperhost = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sqlQuery_getHostID = "SELECT hostID FROM Property WHERE propertyID = ?";
		String sqlQuery_getRating = "SELECT Review.cleanlinessScore, Review.commsScore, Review.checkinScore, Review.accuracyScore, Review.locationScore, Review.valueScore "
									+ " FROM Review "
									+ "INNER JOIN Property ON Review.propertyID = Property.propertyID WHERE hostID = ?";
		int hostID = -1;
		float sumOverallScores = 0;
		int rowCount = 0;

		try {					
			//establish connection
			con = SQL_Interface.getConnection();
			pstmt = con.prepareStatement(sqlQuery_getHostID);
			pstmt.setInt(1, propertyID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				hostID = rs.getInt("hostID");
			}
			
			pstmt2 = con.prepareStatement(sqlQuery_getRating);
			pstmt2.setInt(1, hostID);
			rs2 = pstmt2.executeQuery();
				
			
			while(rs2.next()) {
				rowCount++;
				int cleanlinessScore = rs2.getInt("cleanlinessScore");
				int commsScore = rs2.getInt("commsScore");
				int checkinScore = rs2.getInt("checkinScore");
				int accuracyScore = rs2.getInt("accuracyScore");
				int locationScore = rs2.getInt("locationScore");
				int valueScore = rs2.getInt("valueScore");
				
				sumOverallScores += cleanlinessScore + commsScore + checkinScore + accuracyScore + locationScore + valueScore;			// Overall average score calculation
			}

			if(sumOverallScores/(6*rowCount) >= 4.7) isSuperhost = true;				// comparing overall average score with the threshold to enable Superhost status

		}
		catch (Exception ex) {ex.printStackTrace();}
		finally {
			try {
				if(rs != null)  	rs.close();
				if(rs2 != null)		rs2.close();
                if(pstmt != null) 	pstmt.close();
                if(pstmt2 != null) 	pstmt2.close();
                if(con != null) 	con.close();
			} catch (SQLException ex) {ex.printStackTrace();}
		}
		return isSuperhost;
	}
	
	public static float averageScore (int cleanlinessScore, int commsScore, int checkinScore, int accuracyScore, int locationScore, int valueScore) {
		return (cleanlinessScore + commsScore + checkinScore + accuracyScore + locationScore + valueScore) / 6;
	}
 
}
