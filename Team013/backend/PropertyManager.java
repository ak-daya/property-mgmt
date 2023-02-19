package backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import userInterface.Login;

/* 
 * Class for Property Manager which is used to control property and database classes
*/

public class PropertyManager {
	
	// Host info
	public Host host;
	public List<Integer> propertyIDs;
		
	public PropertyManager() {
	}
	
	// Get ID, shortName and areaName of all properties owned by a hostID
	public ArrayList<Object> getPropertiesOwnedBy(int hostID) {
		ArrayList<Object> properties = new ArrayList<Object>();

		//SQL initialisation
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sqlQuery = "SELECT propertyID, shortName, areaName FROM Property WHERE hostID = ?";
		
		try {					
			//establish connection
			con = SQL_Interface.getConnection();
			
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setInt(1, hostID);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				properties.add(rs.getInt("propertyID"));
				properties.add(rs.getString("shortName"));
				properties.add(rs.getInt("areaName"));
			}
		}
		catch(Exception ex) {
			try {
				if (con != null)	con.rollback();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			ex.printStackTrace();
		}
		finally {
			try {
				if(rs != null)  	rs.close();
                if(pstmt != null) 	pstmt.close();
                if(con != null) 	con.close();
                
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return properties;
	}
	
	// Host- Add new property into SQL database
	public void addProperty(Property propertyToAdd) {
		Connection con = null;
		PreparedStatement pstmtProperty = null;
		PreparedStatement pstmtChargeBands = null;
		
		String sql_newProp = "INSERT INTO Property (hostID, shortName, longDesc, "
				+ "doorNumber, streetName, areaName, postcode,"
				+ "breakfast, bedLinen, towels, bedrooms, bed1_type, bed2_type, "
				+ "hairDryer, shampoo, toiletPaper, bathrooms, toilet, bath, shower, ifShared,"
				+ "fridge, microwave, oven, dishwasher, tableware, cookware, provisions,"
				+ "wifi, television, satellite, streaming, dvdPlayer, boardGames,"
				+ "centralHeating, washingMachine, dryingMachine, fireExtinguisher, smokeAlarm, firstAidKit,"
				+ "sitePark, roadPark, paidPark, patio, barbeque) "
				+ "VALUES (?,?,?,"
				+ "?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?);";
		String sql_userType = "";
		
		try {					
			// Establish connection
			con = SQL_Interface.getConnection();
			con.setAutoCommit(false);
			// Insert variables into sql statement
			pstmtProperty = con.prepareStatement(sql_newProp);
			
			pstmtProperty.setInt(1, propertyToAdd.hostID);
			pstmtProperty.setString(2, propertyToAdd.shortName);
			pstmtProperty.setString(3, propertyToAdd.longName);
			
			pstmtProperty.setInt(4, propertyToAdd.doorNumber);
			pstmtProperty.setString(5, propertyToAdd.streetName);
			pstmtProperty.setString(6, propertyToAdd.areaName);
			pstmtProperty.setString(7, propertyToAdd.postcode);	
			pstmtProperty.setInt(8, boolToInt(propertyToAdd.breakfastAvailability));
			pstmtProperty.setInt(9, boolToInt(propertyToAdd.bedLinen));
			pstmtProperty.setInt(10, boolToInt(propertyToAdd.towels));
			
			pstmtProperty.setInt(11, propertyToAdd.bedrooms.size());
			pstmtProperty.setString(12, propertyToAdd.bedrooms.get(0).bed1);
			pstmtProperty.setString(13, propertyToAdd.bedrooms.get(0).bed2);
			
			pstmtProperty.setInt(14, boolToInt(propertyToAdd.hairDryer));
			pstmtProperty.setInt(15, boolToInt(propertyToAdd.shampoo));
			pstmtProperty.setInt(16, boolToInt(propertyToAdd.toiletPaper));
			pstmtProperty.setInt(17, propertyToAdd.bathrooms.size());
			pstmtProperty.setInt(18, boolToInt(propertyToAdd.bathrooms.get(0).toilet));
			pstmtProperty.setInt(19, boolToInt(propertyToAdd.bathrooms.get(0).bath));	
			pstmtProperty.setInt(20, boolToInt(propertyToAdd.bathrooms.get(0).shower));
			
			pstmtProperty.setInt(21, boolToInt(propertyToAdd.bathrooms.get(0).shared));
			pstmtProperty.setInt(22, boolToInt(propertyToAdd.fridge));
			pstmtProperty.setInt(23, boolToInt(propertyToAdd.microwave));
			pstmtProperty.setInt(24, boolToInt(propertyToAdd.oven));
			pstmtProperty.setInt(25, boolToInt(propertyToAdd.dishwasher));
			pstmtProperty.setInt(26, boolToInt(propertyToAdd.tableware));
			pstmtProperty.setInt(27, boolToInt(propertyToAdd.cookware));
			pstmtProperty.setInt(28, boolToInt(propertyToAdd.basicProvisions));
			pstmtProperty.setInt(29, boolToInt(propertyToAdd.wifi));
			pstmtProperty.setInt(30, boolToInt(propertyToAdd.television));
			
			pstmtProperty.setInt(31, boolToInt(propertyToAdd.satellite));
			pstmtProperty.setInt(32, boolToInt(propertyToAdd.streaming));
			pstmtProperty.setInt(33, boolToInt(propertyToAdd.dvdPlayer));
			pstmtProperty.setInt(34, boolToInt(propertyToAdd.boardGames));
			pstmtProperty.setInt(35, boolToInt(propertyToAdd.centralHeating));
			pstmtProperty.setInt(36, boolToInt(propertyToAdd.washingMachine));
			pstmtProperty.setInt(37, boolToInt(propertyToAdd.dryingMachine));	
			pstmtProperty.setInt(38, boolToInt(propertyToAdd.fireExtinguisher));
			pstmtProperty.setInt(39, boolToInt(propertyToAdd.smokeAlarm));
			pstmtProperty.setInt(40, boolToInt(propertyToAdd.firstAidKit));
			
			pstmtProperty.setInt(41, boolToInt(propertyToAdd.onsitePark));
			pstmtProperty.setInt(42, boolToInt(propertyToAdd.onroadPark));
			pstmtProperty.setInt(43, boolToInt(propertyToAdd.paidPark));
			pstmtProperty.setInt(44, boolToInt(propertyToAdd.patio));
			pstmtProperty.setInt(45, boolToInt(propertyToAdd.barbeque));
			
			// Execute statement
			int affectedRows_prop = pstmtProperty.executeUpdate();
			if(affectedRows_prop == 1) {
				// Success entering property
				con.commit();
			} else {
				// Failure entering property
				con.rollback(); 
				JOptionPane.showMessageDialog(null, "Failure making property", "Error", JOptionPane.ERROR_MESSAGE);
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
				// Close connections
                if(pstmtProperty != null) 	pstmtProperty.close();
                if(pstmtChargeBands != null)  pstmtChargeBands.close();
                if(con != null) 	con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	// Parse sql tinyint (1 or 0) into true or false
	public boolean toBool (int sqlint) {
		if (sqlint == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	// Parse boolean into sql tinyint (1 or 0)
	public int boolToInt(boolean query) {
		if (query == true) {
			return 1;
		} else {
			return 0;
		}
		
	}
	
	// Get Property from propID from database
	public Property getProperty(int propertyID) {
		Property propData = new Property();
		
		ArrayList<Bedroom> bedroomsToAdd = new ArrayList<Bedroom>();
	    ArrayList<Bathroom> bathroomsToAdd = new ArrayList<Bathroom>();
	    
		try {
			Connection con = null;
			ResultSet rs = null;
			ResultSet rs2= null;
			ResultSet rs3= null;
			
			// Make connection
		    con = SQL_Interface.getConnection();
		    // Select the right property from table
		    String sqlQuery = "SELECT * FROM Property "
					+ "WHERE propertyID = ?";
		    PreparedStatement pstmt = con.prepareStatement(sqlQuery);
			pstmt.setString(1, String.valueOf(propertyID));
			rs = pstmt.executeQuery();
			
			// If property not found for the propertyID
			if (!rs.isBeforeFirst()) {
				//JOptionPane.showMessageDialog(null, "Property does not exist", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			int hostID = 0;
			
			while (rs.next()) {
				// Add data to Property object from property table
				bathroomsToAdd.add(new Bathroom(toBool(rs.getInt("toilet")),toBool(rs.getInt("bath")),toBool(rs.getInt("shower")),toBool(rs.getInt("ifShared"))));
			    propData.hasBathrooms(bathroomsToAdd);
			    bedroomsToAdd.add(new Bedroom(rs.getString("bed1_type"),rs.getString("bed2_type")));
			    propData.hasBedrooms(bedroomsToAdd);
				
			    propData.hasLivingFacility(toBool(rs.getInt("wifi")), toBool(rs.getInt("television")), toBool(rs.getInt("satellite")), toBool(rs.getInt("streaming")), toBool(rs.getInt("dvdPlayer")), toBool(rs.getInt("boardGames")));
			    propData.hasBathingFacility(toBool(rs.getInt("hairDryer")), toBool(rs.getInt("shampoo")), toBool(rs.getInt("toiletPaper")));
			    propData.hasKitchenFacility(toBool(rs.getInt("fridge")), toBool(rs.getInt("microwave")), toBool(rs.getInt("oven")), toBool(rs.getInt("dishwasher")), toBool(rs.getInt("cookware")), toBool(rs.getInt("tableware")), toBool(rs.getInt("provisions")));
			    propData.hasUtilityFacility(toBool(rs.getInt("centralHeating")), toBool(rs.getInt("washingMachine")), toBool(rs.getInt("dryingMachine")), toBool(rs.getInt("fireExtinguisher")), toBool(rs.getInt("smokeAlarm")), toBool(rs.getInt("firstAidKit")));
			    propData.hasSleepingFacility(toBool(rs.getInt("bedLinen")), toBool(rs.getInt("towels")));
			    propData.hasOutdoorFacility(toBool(rs.getInt("sitePark")), toBool(rs.getInt("roadPark")), toBool(rs.getInt("paidPark")), toBool(rs.getInt("patio")), toBool(rs.getInt("barbeque")));
			    
			    propData.houseInfo(toBool(rs.getInt("breakfast")), rs.getString("shortName"), rs.getString("longDesc"), rs.getString("areaName"), rs.getInt("doorNumber"), rs.getString("streetName"), rs.getString("postcode"));
			    
			    hostID = rs.getInt("hostID");
			}
			// Get host email from Host table using hostID
			try {
			String sqlQuery2 = "SELECT * FROM Host "
					+ "WHERE hostID = ?";
		    PreparedStatement pstmt2 = con.prepareStatement(sqlQuery2);
			pstmt2.setString(1, String.valueOf(hostID));
			rs2 = pstmt2.executeQuery();
			
			String hostEmail = "";
			while (rs2.next()) {
				hostEmail = rs2.getString("email");
			}
			
			try {
				if(rs2 != null)  	rs.close();
                if(pstmt2 != null) 	pstmt.close();
			}
            catch (SQLException ex) {
				ex.printStackTrace();
    		}
			// Get host name from Account table using email
			try {
				String sqlQuery3 = "SELECT * FROM Account "
						+ "WHERE email = ?";
			    PreparedStatement pstmt3 = con.prepareStatement(sqlQuery3);
				pstmt3.setString(1, String.valueOf(hostEmail));
				rs3 = pstmt3.executeQuery();
				
				while (rs3.next()) {
					// Add host related info to the Property object
					propData.hostInfo(rs3.getString("fname"), rs3.getString("lname"), hostID); // fore sur ID
				}
				try {
					if(rs3 != null)  	rs.close();
	                if(pstmt3 != null) 	pstmt.close();
	                if(con != null) 	con.close();
				}
	            catch (SQLException ex) {
					ex.printStackTrace();
	    		}
			}
			catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
		catch (Exception ex) {
				ex.printStackTrace();
		}
	}
	catch (Exception ex) {
		ex.printStackTrace();
	}
	// Return completed Property object
	return propData;
	}
}
