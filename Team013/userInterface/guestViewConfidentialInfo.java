package userInterface;

import java.awt.BorderLayout;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.MatteBorder;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;

import backend.SQL_Interface;
import backend.Session;

import javax.swing.border.BevelBorder;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.CardLayout;
import javax.swing.DebugGraphics;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSlider;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JScrollBar;



public class guestViewConfidentialInfo extends JFrame {

	private JPanel contentPane;
	public Session session;

	
	/**
	 * Create the frame.
	 */
	public guestViewConfidentialInfo(Session session, int propertyID, int bookingID) {
		this.session = session;
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
		
		JPanel panel_top = new JPanel();
		panel_top.setBounds(0, 0, 1044, 56);
		panel.add(panel_top);
		panel_top.setBackground(new Color(204, 51, 51));
		panel_top.setLayout(null);
		
		JLabel lblHomebreaks = new JLabel("HomeBreaks");
		lblHomebreaks.setBounds(6, 6, 176, 47);
		panel_top.add(lblHomebreaks);
		lblHomebreaks.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomebreaks.setForeground(Color.WHITE);
		lblHomebreaks.setFont(new Font("Dialog", Font.BOLD, 28));
		
		JLabel lblheadingReviews = new JLabel("Property / Stay Details");
		lblheadingReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblheadingReviews.setForeground(Color.WHITE);
		lblheadingReviews.setFont(new Font("Dialog", Font.BOLD, 20));
		lblheadingReviews.setBounds(291, 6, 461, 47);
		panel_top.add(lblheadingReviews);
		
		JPanel panel_hostDetails = new JPanel();
		panel_hostDetails.setLayout(null);
		panel_hostDetails.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_hostDetails.setBackground(new Color(205, 92, 92));
		panel_hostDetails.setBounds(34, 78, 455, 256);
		panel.add(panel_hostDetails);
		
		JLabel lblHostDetails = new JLabel("");
		lblHostDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblHostDetails.setForeground(Color.WHITE);
		lblHostDetails.setFont(new Font("Dialog", Font.BOLD, 20));
		lblHostDetails.setBounds(114, 19, 236, 47);
		panel_hostDetails.add(lblHostDetails);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setToolTipText("");
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblEmail.setBorder(null);
		lblEmail.setBounds(145, 76, 62, 29);
		panel_hostDetails.add(lblEmail);
		
		JLabel lblFullname = new JLabel("Full Name:");
		lblFullname.setToolTipText("");
		lblFullname.setForeground(Color.WHITE);
		lblFullname.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblFullname.setBorder(null);
		lblFullname.setBounds(110, 128, 81, 29);
		panel_hostDetails.add(lblFullname);
		
		JLabel lblmobNo = new JLabel("Mobile Number:");
		lblmobNo.setToolTipText("");
		lblmobNo.setForeground(Color.WHITE);
		lblmobNo.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblmobNo.setBorder(null);
		lblmobNo.setBounds(67, 186, 126, 29);
		panel_hostDetails.add(lblmobNo);
		
		JLabel lblshowEmail = new JLabel("");
		lblshowEmail.setToolTipText("");
		lblshowEmail.setForeground(Color.WHITE);
		lblshowEmail.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblshowEmail.setBorder(null);
		lblshowEmail.setBounds(211, 76, 214, 29);
		panel_hostDetails.add(lblshowEmail);
		
		JLabel lblshowName = new JLabel("");
		lblshowName.setToolTipText("");
		lblshowName.setForeground(Color.WHITE);
		lblshowName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblshowName.setBorder(null);
		lblshowName.setBounds(201, 128, 224, 29);
		panel_hostDetails.add(lblshowName);
		
		JLabel lblshowMobNo = new JLabel("");
		lblshowMobNo.setToolTipText("");
		lblshowMobNo.setForeground(Color.WHITE);
		lblshowMobNo.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblshowMobNo.setBorder(null);
		lblshowMobNo.setBounds(203, 186, 222, 29);
		panel_hostDetails.add(lblshowMobNo);
		
		JPanel panel_propertyDetails = new JPanel();
		panel_propertyDetails.setLayout(null);
		panel_propertyDetails.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_propertyDetails.setBackground(new Color(205, 92, 92));
		panel_propertyDetails.setBounds(531, 78, 455, 256);
		panel.add(panel_propertyDetails);
		
		JLabel lblPropertyDetails = new JLabel("Property Details");
		lblPropertyDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropertyDetails.setForeground(Color.WHITE);
		lblPropertyDetails.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPropertyDetails.setBounds(114, 19, 236, 47);
		panel_propertyDetails.add(lblPropertyDetails);
		
		JLabel lblStreetName = new JLabel("Street Name:");
		lblStreetName.setToolTipText("");
		lblStreetName.setForeground(Color.WHITE);
		lblStreetName.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblStreetName.setBorder(null);
		lblStreetName.setBounds(93, 76, 114, 29);
		panel_propertyDetails.add(lblStreetName);
		
		JLabel lblDoorNumber = new JLabel("Door Number:");
		lblDoorNumber.setToolTipText("");
		lblDoorNumber.setForeground(Color.WHITE);
		lblDoorNumber.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblDoorNumber.setBorder(null);
		lblDoorNumber.setBounds(86, 112, 107, 29);
		panel_propertyDetails.add(lblDoorNumber);
		
		JLabel lblPostCode = new JLabel("Post Code:");
		lblPostCode.setToolTipText("");
		lblPostCode.setForeground(Color.WHITE);
		lblPostCode.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblPostCode.setBorder(null);
		lblPostCode.setBounds(110, 147, 84, 29);
		panel_propertyDetails.add(lblPostCode);
		
		JLabel lblShowStreet = new JLabel("");
		lblShowStreet.setToolTipText("");
		lblShowStreet.setForeground(Color.WHITE);
		lblShowStreet.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblShowStreet.setBorder(null);
		lblShowStreet.setBounds(209, 77, 213, 29);
		panel_propertyDetails.add(lblShowStreet);
		
		JLabel lblShowDoor = new JLabel("");
		lblShowDoor.setToolTipText("");
		lblShowDoor.setForeground(Color.WHITE);
		lblShowDoor.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblShowDoor.setBorder(null);
		lblShowDoor.setBounds(210, 111, 161, 29);
		panel_propertyDetails.add(lblShowDoor);
		
		JLabel lblShowPostCode = new JLabel("");
		lblShowPostCode.setToolTipText("");
		lblShowPostCode.setForeground(Color.WHITE);
		lblShowPostCode.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblShowPostCode.setBorder(null);
		lblShowPostCode.setBounds(209, 147, 188, 29);
		panel_propertyDetails.add(lblShowPostCode);
		
		JLabel lblPeriodStay = new JLabel("Period of Stay");
		lblPeriodStay.setToolTipText("");
		lblPeriodStay.setForeground(Color.WHITE);
		lblPeriodStay.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblPeriodStay.setBorder(null);
		lblPeriodStay.setBounds(169, 184, 116, 29);
		panel_propertyDetails.add(lblPeriodStay);
		
		JLabel lblshowPeriodFrom = new JLabel("From");
		lblshowPeriodFrom.setToolTipText("");
		lblshowPeriodFrom.setForeground(Color.WHITE);
		lblshowPeriodFrom.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblshowPeriodFrom.setBorder(null);
		lblshowPeriodFrom.setBounds(21, 218, 55, 29);
		panel_propertyDetails.add(lblshowPeriodFrom);
		
		JLabel lblshowPeriodFromDate = new JLabel("");
		lblshowPeriodFromDate.setToolTipText("");
		lblshowPeriodFromDate.setForeground(Color.WHITE);
		lblshowPeriodFromDate.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblshowPeriodFromDate.setBorder(null);
		lblshowPeriodFromDate.setBounds(70, 218, 148, 29);
		panel_propertyDetails.add(lblshowPeriodFromDate);
		
		JLabel lblshowPeriodTo = new JLabel("To");
		lblshowPeriodTo.setToolTipText("");
		lblshowPeriodTo.setForeground(Color.WHITE);
		lblshowPeriodTo.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblshowPeriodTo.setBorder(null);
		lblshowPeriodTo.setBounds(228, 217, 62, 29);
		panel_propertyDetails.add(lblshowPeriodTo);
		
		JLabel lblshowPeriodToDate = new JLabel("");
		lblshowPeriodToDate.setToolTipText("");
		lblshowPeriodToDate.setForeground(Color.WHITE);
		lblshowPeriodToDate.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblshowPeriodToDate.setBorder(null);
		lblshowPeriodToDate.setBounds(284, 217, 161, 29);
		panel_propertyDetails.add(lblshowPeriodToDate);
		
		JPanel panel_guestReview = new JPanel();
		panel_guestReview.setLayout(null);
		panel_guestReview.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_guestReview.setBackground(new Color(205, 92, 92));
		panel_guestReview.setBounds(323, 361, 395, 212);
		panel.add(panel_guestReview);
		
		JLabel lblreviewHeading = new JLabel("Do you want to write a review?");
		lblreviewHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblreviewHeading.setForeground(Color.WHITE);
		lblreviewHeading.setFont(new Font("Dialog", Font.BOLD, 20));
		lblreviewHeading.setBounds(24, 42, 343, 47);
		panel_guestReview.add(lblreviewHeading);
		
		JButton btnWriteReview = new JButton("Write a Review");
		btnWriteReview.setFont(new Font("Dialog", Font.BOLD, 13));
		btnWriteReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new writeGuestReviews(session, bookingID).setVisible(true);
				dispose();
			}
		});
		btnWriteReview.setBounds(133, 123, 131, 47);
		panel_guestReview.add(btnWriteReview);
		btnWriteReview.setToolTipText("Click here to Write a Review");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlQuery = "";
		if(session.getUserType() == 'h') {
			sqlQuery = "SELECT Property.doorNumber, Property.streetName, Property.postcode, Account.fname, Account.lname, Account.mobileNo, Account.email, Booking.startDate, Booking.endDate "
					+ "FROM Property "
					+ "INNER JOIN Host ON Property.hostID = Host.hostID "
					+ "INNER JOIN Booking ON Property.propertyID = Booking.propertyID "
					+ "INNER JOIN Guest ON Booking.guestID = Guest.guestID "
					+ "INNER JOIN Account ON Guest.email = Account.email "
					+ "WHERE Property.propertyID = ?  AND Host.email = ? AND bookingID = ?" ;
			
			lblHostDetails.setText("Guest Details");
			panel_guestReview.setVisible(false);
		}
		else if(session.getUserType() == 'g') {
			sqlQuery = 	"SELECT Property.doorNumber, Property.streetName, Property.postcode, "
			            + "Account.fname, Account.lname, Account.mobileNo, Account.email, "
			            + "Booking.startDate, Booking.endDate "
			            + "FROM Property "
			            + "INNER JOIN Host ON Property.hostID = Host.hostID "
			            + "INNER JOIN Account ON Host.email = Account.email "
			            + "INNER JOIN Booking ON Property.propertyID "
			            + "INNER JOIN Guest ON Booking.guestID = Guest.guestID "
			            + "WHERE Property.propertyID = ? AND Guest.email = ? AND bookingID = ? ";
			
			lblHostDetails.setText("Host Details");
		}
		else JOptionPane.showMessageDialog(null, "User type authentication error. Please log in again.", "Error", JOptionPane.ERROR_MESSAGE);
		
		
		try {					
			//establish connection
			con = SQL_Interface.getConnection();
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setInt(1, propertyID);
			pstmt.setString(2, session.getEmail());
			pstmt.setInt(3, bookingID);
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				lblshowName.setText(rs.getString("Account.fname") + " " + rs.getString("Account.lname"));
				lblshowMobNo.setText(String.valueOf(rs.getLong("Account.mobileNo")));
				lblshowEmail.setText(rs.getString("Account.email"));
				lblShowDoor.setText(String.valueOf(rs.getInt("Property.doorNumber")));
				lblShowStreet.setText(rs.getString("Property.streetName"));
				lblShowPostCode.setText(rs.getString("Property.postcode"));
				lblshowPeriodFromDate.setText(String.valueOf(rs.getDate("Booking.startDate")));
				lblshowPeriodToDate.setText(String.valueOf(rs.getDate("Booking.endDate")));
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
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		
	}
}
