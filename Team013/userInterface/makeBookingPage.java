package userInterface;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import backend.InputValidation;
import backend.SQL_Interface;
import backend.Session;
import javax.swing.border.BevelBorder;
import javax.swing.JOptionPane;

public class makeBookingPage extends JFrame {

	private JPanel contentPane;
	public  Session session;  
	public LocalDate checkInDate;
	public LocalDate checkOutDate;
	public int propertyID;
	
	//returns date in specified format
	public String getDateFormatted(JDateChooser dateChooser, SimpleDateFormat formatter) {
		return  formatter.format(dateChooser.getDate());
	}
	
	//returns number of days between two dates
	public long getNumberOfNights(LocalDate firstDate, LocalDate secondDate) {
		return ChronoUnit.DAYS.between(firstDate, secondDate);
	}
	
	//function called when user updates the dates in the entry box
	public void setDateDifference(JLabel lblnights, JDateChooser inDateChooser, JDateChooser outDateChooser, SimpleDateFormat mySqlDate) {
		String newCheckInDate = getDateFormatted(inDateChooser, mySqlDate);
		String newCheckOutDate = getDateFormatted(outDateChooser, mySqlDate);
		LocalDate inDate = LocalDate.parse(newCheckInDate);
		LocalDate outDate = LocalDate.parse(newCheckOutDate);
		if (InputValidation.checkInDateValid(inDate, outDate) && InputValidation.dateCheckNotPast(inDate, outDate)) {
			lblnights.setText(String.valueOf(getNumberOfNights(inDate, outDate)));
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Date", "Error", JOptionPane.ERROR_MESSAGE);
			inDateChooser.setDate(Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			outDateChooser.setDate(Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		};
	}
	
	//gets prices based off charge band
	public void getPrices(JDateChooser inDateChooser, JDateChooser outDateChooser, JLabel pricePerNight, JLabel priceForCleaning, JLabel priceForService, JLabel priceTotal) {
		SimpleDateFormat mySqlDate = new SimpleDateFormat("yyyy-MM-dd");
		String inDateToCheck = getDateFormatted(inDateChooser, mySqlDate);
		String outDateToCheck = getDateFormatted(outDateChooser, mySqlDate);
		double numNights = getNumberOfNights(LocalDate.parse(inDateToCheck), LocalDate.parse(outDateToCheck));
		//prop ID 5, two charge bands 01 Dec - 15 Dec, 16 Dec - 31 Dec
		ResultSet rs=null;
		PreparedStatement pstmt =null;
		Connection con = null;
		try {
			con = SQL_Interface.getConnection();
			String sqlQuery = "SELECT * FROM ChargeBands WHERE propertyID = ? AND ? between startDate and endDate";
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setInt(1, propertyID);
			pstmt.setDate(2, java.sql.Date.valueOf(inDateToCheck));
			rs = pstmt.executeQuery();	
			
			double priceNight = 40;
			double priceCleaning = 10;
			double priceService = 15;
			
			while (rs.next()) {
				priceNight = rs.getDouble("pricePerNight");
				priceCleaning = rs.getDouble("cleaningCharge");
				priceService = rs.getDouble("serviceCharge");
			}
			pricePerNight.setText(String.valueOf("£" + priceNight));
			priceForCleaning.setText(String.valueOf("£" + priceCleaning));
			priceForService.setText(String.valueOf("£" + priceService));
			priceTotal.setText(String.valueOf("£" + ((priceNight*numNights) + priceCleaning + priceService)));
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
				try {
					if(rs != null)  	rs.close();
		            if(pstmt != null) pstmt.close();
		            if(con != null) 	con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
           
		}
	}
	
	public void createBooking(String inDate, String outDate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {					
			//establish connection
			String sqlQuery = "INSERT INTO Booking (propertyID, guestID, startDate, endDate, acceptedByHost) "
					+ "VALUES ( ? , (SELECT guestID FROM Guest WHERE email = ? ), ?, ?, ?);";
			con = SQL_Interface.getConnection();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sqlQuery);			
			pstmt.setInt(1, propertyID);
			pstmt.setString(2, session.getEmail());
			pstmt.setDate(3, java.sql.Date.valueOf(inDate));
			pstmt.setDate(4, java.sql.Date.valueOf(outDate));
			pstmt.setInt(5, -1);
			int affectedRows_account = pstmt.executeUpdate();
			if(affectedRows_account == 1){	
				//commit changes to DB
				con.commit();	
			} else {	
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
                if(pstmt != null) 	pstmt.close();
                if(con != null) 	con.close();
                new homepageGuest(session).setVisible(true);
                dispose();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public makeBookingPage(Session session,  LocalDate inDate, LocalDate outDate, int propID, String maxNoGuests) throws ParseException {
	    
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		SimpleDateFormat mySqlDate = new SimpleDateFormat("yyyy-MM-dd");
		this.session = session;
		this.checkInDate = inDate;
		this.checkOutDate = outDate;
		this.propertyID = propID;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1045, 629);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.setFont(new Font("Dialog", Font.BOLD, 13));
		panel.setBackground(new Color(255, 204, 204));
		panel.setBounds(0, 0, 1045, 601);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_Property_1 = new JPanel();
		panel_Property_1.setLayout(null);
		panel_Property_1.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(153, 0, 0), null, null, null));
		panel_Property_1.setBackground(new Color(204, 102, 102));
		panel_Property_1.setBounds(608, 160, 357, 314);
		panel.add(panel_Property_1);
		
		JPanel panel_Property = new JPanel();
		panel_Property.setBackground(new Color(204, 102, 102));
		panel_Property.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(153, 0, 0), null, null, null));
		panel_Property.setBounds(76, 160, 357, 314);
		panel.add(panel_Property);
		panel_Property.setLayout(null);
		
        JLabel lblNights = new JLabel("No. of Nights:");
		lblNights.setHorizontalAlignment(SwingConstants.CENTER);
		lblNights.setForeground(Color.WHITE);
		lblNights.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNights.setBounds(104, 104, 115, 35);
		panel_Property_1.add(lblNights);
		
		JLabel lblnightsNo = new JLabel("");
		lblnightsNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblnightsNo.setForeground(Color.WHITE);
		lblnightsNo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblnightsNo.setBounds(215, 103, 54, 36);
		panel_Property_1.add(lblnightsNo);
        
		//defining date choosers
        JDateChooser checkInDateChooser = new JDateChooser();
        checkInDateChooser.setBounds(141, 147, 140, 26);
		checkInDateChooser.setDate(Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		panel_Property.add(checkInDateChooser);
		
        JDateChooser checkOutDateChooser = new JDateChooser();
        checkOutDateChooser.setBounds(143, 192, 138, 26);
		checkOutDateChooser.setDate(Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		panel_Property.add(checkOutDateChooser);
        
		//defining LHS gui elements
		JLabel lblcheckIn_adjust = new JLabel("Check In");
		lblcheckIn_adjust.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblcheckIn_adjust.setForeground(Color.WHITE);
		lblcheckIn_adjust.setBorder(null);
		lblcheckIn_adjust.setBounds(77, 151, 64, 16);
		panel_Property.add(lblcheckIn_adjust);
		
		JLabel lblcheckOut_adjust = new JLabel("Check Out");
		lblcheckOut_adjust.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblcheckOut_adjust.setForeground(Color.WHITE);
		lblcheckOut_adjust.setBorder(null);
		lblcheckOut_adjust.setBounds(71, 196, 74, 16);
		panel_Property.add(lblcheckOut_adjust);
		
		JLabel lblbookingDates = new JLabel("Dates");
		lblbookingDates.setToolTipText("Booking Dates");
		lblbookingDates.setForeground(Color.WHITE);
		lblbookingDates.setFont(new Font("Dialog", Font.BOLD, 17));
		lblbookingDates.setBorder(null);
		lblbookingDates.setBounds(77, 105, 64, 29);
		panel_Property.add(lblbookingDates);
		
		JLabel lblYourBookingSummary = new JLabel("Your Booking Summary");
		lblYourBookingSummary.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourBookingSummary.setForeground(Color.WHITE);
		lblYourBookingSummary.setFont(new Font("Dialog", Font.BOLD, 20));
		lblYourBookingSummary.setBounds(46, 36, 276, 47);
		panel_Property.add(lblYourBookingSummary);
		
		JLabel lblGuests = new JLabel("Max Guests:");
		lblGuests.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuests.setForeground(Color.WHITE);
		lblGuests.setFont(new Font("Dialog", Font.BOLD, 17));
		lblGuests.setBounds(46, 244, 121, 47);
		panel_Property.add(lblGuests);
		
		JLabel lblMaximumGuestsNo = new JLabel(maxNoGuests);
		lblMaximumGuestsNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaximumGuestsNo.setForeground(Color.WHITE);
		lblMaximumGuestsNo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMaximumGuestsNo.setBounds(177, 244, 54, 47);
		panel_Property.add(lblMaximumGuestsNo);
		
		JPanel panel_top = new JPanel();
		panel_top.setLayout(null);
		panel_top.setBackground(new Color(204, 51, 51));
		panel_top.setBounds(0, 0, 1044, 84);
		panel.add(panel_top);
		
		JLabel lblHomebreaks = new JLabel("HomeBreaks");
		lblHomebreaks.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomebreaks.setForeground(Color.WHITE);
		lblHomebreaks.setFont(new Font("Dialog", Font.BOLD, 28));
		lblHomebreaks.setBounds(6, 14, 176, 47);
		panel_top.add(lblHomebreaks);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login().setVisible(true);
				dispose();
			}
		});
		btnLogout.setForeground(new Color(128, 0, 0));
		btnLogout.setToolTipText("Click to Logout");
		btnLogout.setFont(new Font("Dialog", Font.BOLD, 14));
		btnLogout.setBounds(926, 24, 100, 36);
		panel_top.add(btnLogout);
		
		JLabel lblConfirmBookingAnd = new JLabel("Confirm Booking and Pay");
		lblConfirmBookingAnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmBookingAnd.setForeground(Color.WHITE);
		lblConfirmBookingAnd.setFont(new Font("Dialog", Font.BOLD, 20));
		lblConfirmBookingAnd.setBounds(333, 33, 380, 36);
		panel_top.add(lblConfirmBookingAnd);
		
		JButton btnConfirmBooking = new JButton("Confirm Booking");
		btnConfirmBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookingStartDate = getDateFormatted(checkInDateChooser, mySqlDate);
				String bookingEndDate = getDateFormatted(checkOutDateChooser, mySqlDate);
				ResultSet rs=null;
				PreparedStatement pstmt=null;
				Connection con = null;
				try {
					con = SQL_Interface.getConnection();
					String sqlQueryStartDate = "SELECT propertyID, bookingID FROM Booking "
							+ "WHERE ((startDate <= ? AND ? <= endDate ) OR  (startDate <= ? AND ? <= endDate )"
							+ "OR  ( ? <= startDate AND startDate <= ? ) OR ( ? <= endDate AND endDate <= ? )) "
							+ "AND propertyID = ? AND acceptedByHost = 1 ";
					
					pstmt = con.prepareStatement(sqlQueryStartDate);
					pstmt.setDate(1, java.sql.Date.valueOf(bookingStartDate));
					pstmt.setDate(2, java.sql.Date.valueOf(bookingStartDate));
					pstmt.setDate(3, java.sql.Date.valueOf(bookingEndDate));
					pstmt.setDate(4, java.sql.Date.valueOf(bookingEndDate));
					pstmt.setDate(5, java.sql.Date.valueOf(bookingStartDate));
					pstmt.setDate(6, java.sql.Date.valueOf(bookingEndDate));
					pstmt.setDate(7, java.sql.Date.valueOf(bookingStartDate));
					pstmt.setDate(8, java.sql.Date.valueOf(bookingEndDate));
					pstmt.setInt(9, propertyID);
					rs = pstmt.executeQuery();	
					if (rs.next() == false) {
						createBooking(bookingStartDate, bookingEndDate);
						JOptionPane.showMessageDialog(null, "Your reservation request has been sent to the host!", "Reservation Requested", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Sorry, the property is now unavailable for these dates", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} 
				catch (SQLException f) {
					f.printStackTrace();
				}
				finally {
					
						try {
							if(rs != null)  	rs.close();
				            if(pstmt != null) pstmt.close();
				            if(con != null) 	con.close();
						} catch (SQLException f) {
							f.printStackTrace();
						}
		           
				}
				
				//new searchPage(session).setVisible(true);
				//dispose();
			}
		});
		
		btnConfirmBooking.setForeground(new Color(128, 0, 0));
		btnConfirmBooking.setFont(new Font("Dialog", Font.BOLD, 14));
		btnConfirmBooking.setBounds(580, 521, 155, 38);
		panel.add(btnConfirmBooking);
		btnConfirmBooking.setToolTipText("Click here to Confirm Booking");
		
		JLabel lblPriceDetails = new JLabel("Price Details");
		lblPriceDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblPriceDetails.setForeground(Color.WHITE);
		lblPriceDetails.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPriceDetails.setBounds(46, 36, 276, 47);
		panel_Property_1.add(lblPriceDetails);
		
		JLabel lblPricePerNight = new JLabel("Price per night:");
		lblPricePerNight.setHorizontalAlignment(SwingConstants.CENTER);
		lblPricePerNight.setForeground(Color.WHITE);
		lblPricePerNight.setFont(new Font("Dialog", Font.BOLD, 15));
		lblPricePerNight.setBounds(98, 134, 115, 35);
		panel_Property_1.add(lblPricePerNight);
		
		JLabel lblpriceNight = new JLabel("");
		lblpriceNight.setHorizontalAlignment(SwingConstants.CENTER);
		lblpriceNight.setForeground(Color.WHITE);
		lblpriceNight.setFont(new Font("Dialog", Font.BOLD, 14));
		lblpriceNight.setBounds(215, 133, 54, 36);
		panel_Property_1.add(lblpriceNight);
		
		JLabel lblCleaningPrice = new JLabel("Cleaning Price:");
		lblCleaningPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblCleaningPrice.setForeground(Color.WHITE);
		lblCleaningPrice.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCleaningPrice.setBounds(100, 166, 115, 33);
		panel_Property_1.add(lblCleaningPrice);
		
		JLabel lblpriceCleaning = new JLabel("");
		lblpriceCleaning.setHorizontalAlignment(SwingConstants.CENTER);
		lblpriceCleaning.setForeground(Color.WHITE);
		lblpriceCleaning.setFont(new Font("Dialog", Font.BOLD, 14));
		lblpriceCleaning.setBounds(215, 165, 54, 34);
		panel_Property_1.add(lblpriceCleaning);
		
		JLabel lblServiceCharges = new JLabel("Service Charges:");
		lblServiceCharges.setHorizontalAlignment(SwingConstants.CENTER);
		lblServiceCharges.setForeground(Color.WHITE);
		lblServiceCharges.setFont(new Font("Dialog", Font.BOLD, 15));
		lblServiceCharges.setBounds(81, 196, 138, 33);
		panel_Property_1.add(lblServiceCharges);
		
		JLabel lblpriceService = new JLabel("");
		lblpriceService.setHorizontalAlignment(SwingConstants.CENTER);
		lblpriceService.setForeground(Color.WHITE);
		lblpriceService.setFont(new Font("Dialog", Font.BOLD, 14));
		lblpriceService.setBounds(215, 195, 54, 34);
		panel_Property_1.add(lblpriceService);
		
		JLabel lblTotal = new JLabel("Total (GBP):");
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setFont(new Font("Dialog", Font.BOLD, 19));
		lblTotal.setBounds(77, 248, 155, 33);
		panel_Property_1.add(lblTotal);
		
		JLabel lbltotalPrice = new JLabel("");
		lbltotalPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lbltotalPrice.setForeground(Color.WHITE);
		lbltotalPrice.setFont(new Font("Dialog", Font.BOLD, 19));
		lbltotalPrice.setBounds(219, 247, 84, 34);
		panel_Property_1.add(lbltotalPrice);
		
		/**
		JLabel lblchargeBandHeader = new JLabel("Lower price:\nYour dates are 12% less than the median nightly rate of the last 60 days.");
		lblchargeBandHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblchargeBandHeader.setForeground(new Color(128, 0, 0));
		lblchargeBandHeader.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblchargeBandHeader.setBounds(202, 96, 612, 47);
		panel.add(lblchargeBandHeader);
		**/
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: dynamic
				// new viewProperty(1, "Placeholder Street", "S1").setVisible(true);
				dispose();
			}
		});
		btnBack.setToolTipText("Click here to go back");
		btnBack.setForeground(new Color(128, 0, 0));
		btnBack.setFont(new Font("Dialog", Font.BOLD, 16));
		btnBack.setBounds(348, 521, 138, 38);
		panel.add(btnBack);
		
		//update event when user changes check in date
        checkInDateChooser.addPropertyChangeListener(new PropertyChangeListener() {
        	public void propertyChange(PropertyChangeEvent e) {
        		setDateDifference(lblnightsNo, checkInDateChooser, checkOutDateChooser, mySqlDate);
        		getPrices(checkInDateChooser, checkOutDateChooser, lblpriceNight, lblpriceCleaning, lblpriceService, lbltotalPrice);
        		
        	}
        });
        
        //update event when user changes check out date
        checkOutDateChooser.addPropertyChangeListener(new PropertyChangeListener() {
        	public void propertyChange(PropertyChangeEvent e) {
        		setDateDifference(lblnightsNo, checkInDateChooser, checkOutDateChooser, mySqlDate);
        		getPrices(checkInDateChooser, checkOutDateChooser, lblpriceNight, lblpriceCleaning, lblpriceService, lbltotalPrice);
        	}
        });
	}
}

