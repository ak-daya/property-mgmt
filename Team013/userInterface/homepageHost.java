package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import backend.SQL_Interface;
import backend.Session;

import javax.swing.border.BevelBorder;
import javax.swing.JToggleButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.CardLayout;
import javax.swing.DebugGraphics;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class homepageHost extends JFrame {

	private JPanel contentPane;
	private JLayeredPane layeredPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField fName_input;
	private JTextField lName_input;
	private JTextField mobileNumber_input;
	private JTextField email_input;
	private JPasswordField password_input;
	private JTextField doorNumber_input;
	private JTextField streetName_input;
	private JTextField areaName_input;
	private JTextField postcode_input;
	private JTable hostpropertyTable;
	private DefaultTableModel resultModel;	
	private JTable hostReviewBookingTable;
	private List<Integer> isBookingAccepted = new ArrayList<>();
	private List<Integer> bookingToAcceptIDs = new ArrayList<>();
	private List<Integer> propertyViewEditIDs = new ArrayList<>();
	private List<Integer> propertyIDs = new ArrayList<>();
	private List<String> hostPropertyResults = new ArrayList<String>();
	private List<String> hostBookingResults = new ArrayList<String>();
	private Session session;
	
	/**
	 * Launch the application.
	 */
	

	public void switchPanels (JPanel panel) {
			
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.validate();
	}
	
public void generateYourPropertiesTable(DefaultTableModel resultModel) {
		
		ResultSet rs=null;
		PreparedStatement pstmt =null;
		Connection con = null;
		try {
			con = SQL_Interface.getConnection();
			String sqlQuery = "SELECT Property.propertyID, Property.shortName, Property.longDesc, Property.postcode "
					+ "FROM Property "
					+ "INNER JOIN Host ON Property.hostID = Host.hostID "
					+ "WHERE Host.email = ? ";
			pstmt = con.prepareStatement(sqlQuery);			
			pstmt.setString(1, session.getEmail());
			rs = pstmt.executeQuery();
			int counter = 1;
			while (rs.next()) {
				String shortName = rs.getString("shortName");
				String longDesc = rs.getString("longDesc");		
				String postcode = rs.getString("postcode");
				int propID = rs.getInt("propertyID");
				this.propertyViewEditIDs.add(propID);
				this.hostPropertyResults.add(String.valueOf(counter) + ". " + shortName);
				this.resultModel.addRow(new Object[] {shortName,longDesc,postcode});
				counter += 1;
			}
			
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
	
	public void generateReviewBookingsTable(DefaultTableModel resultModel) {
		
		ResultSet rs=null;		
		PreparedStatement pstmt =null;
		Connection con = null;
		try {
			con = SQL_Interface.getConnection();
			String sqlQuery = "SELECT Property.propertyID, Property.shortName, "
					+ "Booking.bookingID, Booking.startDate, Booking.endDate, Booking.acceptedByHost "
					+ "FROM Property "
					+ "INNER JOIN Booking ON Property.propertyID=Booking.propertyID "
					+ "INNER JOIN Host ON Property.hostID = Host.hostID "
					+ "WHERE Host.email = ? ";
			pstmt = con.prepareStatement(sqlQuery);		
			pstmt.setString(1, session.getEmail());
			rs = pstmt.executeQuery();
			int counter = 1;
			while (rs.next()) {
				int propertyID = rs.getInt("propertyID");
				int bookID = rs.getInt("bookingID");
				String startDate = rs.getString("startDate");
				String endDate = rs.getString("endDate");
				int accepted = rs.getInt("acceptedByHost");
				String shortName = rs.getString("shortName");
				this.propertyIDs.add(propertyID);
				this.bookingToAcceptIDs.add(bookID);
				this.hostBookingResults.add(String.valueOf(counter) + ". " + shortName);
				this.resultModel.addRow(new Object[] {(String.valueOf(counter) + ". " + shortName),startDate, endDate});	
				this.isBookingAccepted.add(accepted);
				counter += 1;
			}
			
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
	
	public void updateBooking(int bookingID, int updateValue) {
		PreparedStatement pstmt =null;
		
		Connection con = null;
		try {
			con = SQL_Interface.getConnection();
			con.setAutoCommit(false);
			String sqlQuery = "UPDATE Booking "
					+ "SET acceptedByHost = ? "
					+ "WHERE (bookingID = ? AND (IF(acceptedByHost = -1, 1, 0) = 1))";
			pstmt = con.prepareStatement(sqlQuery);			
			pstmt.setInt(1, updateValue);
			pstmt.setInt(2, bookingID);
			int update = pstmt.executeUpdate();
			if (update == 1) {
				con.commit();
				JOptionPane.showMessageDialog(null, "Updated Booking", "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
			} else {
				con.rollback();
				JOptionPane.showMessageDialog(null, "Update Failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (SQLException er) {
			
			er.printStackTrace();
		}
		finally {
			
				try {
		            if(pstmt != null) pstmt.close();
		            if(con != null) 	con.close();
				} catch (SQLException er) {
					er.printStackTrace();
				}
		}
		if (updateValue == 1) {updateConflicts(bookingID);}
	}
	
	public void updateConflicts(int bookID) {
		ResultSet rs=null;
		PreparedStatement pstmtSelect =null;
		Connection con = null;
		try {
			con = SQL_Interface.getConnection();
			String sqlQueryStartDate = "SELECT * FROM Booking "
					+ "WHERE bookingID = ? ";
			pstmtSelect = con.prepareStatement(sqlQueryStartDate);
			pstmtSelect.setInt(1, bookID);
			rs = pstmtSelect.executeQuery();
			
			while (rs.next()) {
				int propertyID = rs.getInt("propertyID");
				java.sql.Date bookingStartDate = rs.getDate("startDate");
				java.sql.Date bookingEndDate = rs.getDate("endDate");
				setOtherBookingsFalse(propertyID, bookID, bookingStartDate, bookingEndDate);
			}
		}
		catch (SQLException er) {
			
			er.printStackTrace();
		}
		finally {
			
				try {
					if (rs != null) rs.close();
		            if(pstmtSelect != null) pstmtSelect.close();
		            if(con != null) 	con.close();
				} catch (SQLException er) {
					er.printStackTrace();
				}
		}
	}
	
	public void setOtherBookingsFalse(int propertyID, int bookID, java.sql.Date bookingStartDate, java.sql.Date bookingEndDate) {
		PreparedStatement pstmt =null;
		Connection con = null;
		try {
			con = SQL_Interface.getConnection();
			con.setAutoCommit(false);
			String sqlQuery = "UPDATE Booking "
					+ "SET acceptedByHost = 0 "
					+ "WHERE (((startDate <= ? AND ? <= endDate ) OR  (startDate <= ? AND ? <= endDate ) "
					+ "OR  ( ? <= startDate AND startDate <= ? ) OR ( ? <= endDate AND endDate <= ? )) "
					+ "AND propertyID = ?) AND acceptedByHost = -1 ";
			pstmt = con.prepareStatement(sqlQuery);			
			pstmt.setDate(1, bookingStartDate);
			pstmt.setDate(2, bookingStartDate);
			pstmt.setDate(3, bookingEndDate);
			pstmt.setDate(4, bookingEndDate);
			pstmt.setDate(5, bookingStartDate);
			pstmt.setDate(6, bookingEndDate);
			pstmt.setDate(7, bookingStartDate);
			pstmt.setDate(8, bookingEndDate);
			pstmt.setInt(9, propertyID);
			int update = pstmt.executeUpdate();
			if (update == 1) {
				con.commit();
			} else {
				con.rollback();
			}
		}
		catch (SQLException er) {
			
			er.printStackTrace();
		}
		finally {
			
				try {
		            if(pstmt != null) pstmt.close();
		            if(con != null) 	con.close();
				} catch (SQLException er) {
					er.printStackTrace();
				}
		}
		
		
	}
	
	
	/**
	 * Create the frame.
	 */
	public homepageHost(Session session) {
		this.session = session;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)	{
				JOptionPane.showMessageDialog(null, "Thanks for using HomeBreaks!", "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
			}
		});		
		
		setResizable(false);
		setBounds(100, 100, 1045, 629);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel.setFont(new Font("Dialog", Font.BOLD, 13));
		panel.setBackground(new Color(255, 204, 204));
		panel.setBounds(0, 0, 1039, 601);
		contentPane.add(panel);
		panel.setLayout(null);
		
		//top search bar
		JPanel panel_top = new JPanel();

		panel_top.setBounds(0, 0, 1045, 84);
		panel.add(panel_top);
		panel_top.setBackground(new Color(204, 51, 51));
		panel_top.setLayout(null);
		
		JLabel lblHomebreaks = new JLabel("HomeBreaks");
		lblHomebreaks.setBounds(6, 19, 176, 47);
		panel_top.add(lblHomebreaks);
		lblHomebreaks.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomebreaks.setForeground(Color.WHITE);
		lblHomebreaks.setFont(new Font("Dialog", Font.BOLD, 28));
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(new Color(128, 0, 0));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new searchPage(session).setVisible(true);
			}
		});
		btnSearch.setToolTipText("Click to Search For Properties");
		btnSearch.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSearch.setBounds(814, 28, 100, 36);
		panel_top.add(btnSearch);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setForeground(new Color(128, 0, 0));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login().setVisible(true);
			}
		});
		btnLogout.setToolTipText("Click to Logout");
		btnLogout.setFont(new Font("Dialog", Font.BOLD, 14));
		btnLogout.setBounds(921, 28, 100, 36);
		panel_top.add(btnLogout);
		
		JLabel helloMessage = new JLabel("Hello,");
		helloMessage.setToolTipText("Your Bookings");
		helloMessage.setForeground(Color.WHITE);
		helloMessage.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		helloMessage.setBorder(null);
		helloMessage.setBounds(242, 28, 50, 29);
		panel_top.add(helloMessage);
		

		JLabel userEmail = new JLabel(session.getEmail());		
		userEmail.setToolTipText("Your Bookings");
		userEmail.setForeground(Color.WHITE);
		userEmail.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		userEmail.setBorder(null);
		userEmail.setBounds(293, 28, 287, 29);
		panel_top.add(userEmail);
		
		//defining layered pane for panel switching
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(380, 104, 638, 478);
		panel.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel_intro = new JPanel();
		layeredPane.add(panel_intro, "name_173645167317227");
		panel_intro.setLayout(null);
		panel_intro.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_intro.setBackground(new Color(205, 92, 92));
		
		JPanel panel_viewEdit = new JPanel();
		layeredPane.add(panel_viewEdit, "name_170703165265016");
		panel_viewEdit.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_viewEdit.setBackground(new Color(205, 92, 92));
		panel_viewEdit.setLayout(null);

		JPanel panel_yourProperties = new JPanel();
		panel_yourProperties.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_yourProperties.setBackground(new Color(205, 92, 92));
		layeredPane.add(panel_yourProperties, "name_171126336308239");
		panel_yourProperties.setLayout(null);

		JPanel panel_reviewBookings = new JPanel();
		panel_reviewBookings.setLayout(null);
		panel_reviewBookings.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_reviewBookings.setBackground(new Color(205, 92, 92));
		layeredPane.add(panel_reviewBookings, "name_172984484957798");
		
		//Image for RHS of homepage
		JLabel lblhostDashboardimage = new JLabel("Travel Image");
		lblhostDashboardimage.setIcon(new ImageIcon(homepageHost.class.getResource("/imagesUI/travel.jpg")));
		lblhostDashboardimage.setBounds(18, 69, 603, 352);
		panel_intro.add(lblhostDashboardimage);
		
		//Defining the contents of the dashboard on the LHS
		JPanel panel_dashboard = new JPanel();
		panel_dashboard.setBackground(new Color(204, 102, 102));
		panel_dashboard.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(153, 0, 0), null, null, null));
		panel_dashboard.setBounds(14, 104, 325, 480);
		panel.add(panel_dashboard);
		panel_dashboard.setLayout(null);
		
		JLabel lblYourDashboard = new JLabel("Your Dashboard");
		lblYourDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourDashboard.setForeground(Color.WHITE);
		lblYourDashboard.setFont(new Font("Dialog", Font.BOLD, 28));
		lblYourDashboard.setBounds(25, 28, 276, 47);
		panel_dashboard.add(lblYourDashboard);
		
		JLabel lblViewEditInfo = new JLabel("1. Personal Information");
		lblViewEditInfo.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		lblViewEditInfo.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 17));
		lblViewEditInfo.setToolTipText("View");
		lblViewEditInfo.setForeground(Color.WHITE);
		lblViewEditInfo.setBorder(null);
		lblViewEditInfo.setBounds(25, 125, 199, 29);
		panel_dashboard.add(lblViewEditInfo);
		
		JButton btnbookingView = new JButton("View");
		btnbookingView.setForeground(new Color(128, 0, 0));
		btnbookingView.setFont(new Font("Dialog", Font.BOLD, 13));
		btnbookingView.setToolTipText("Click here to View/Edit Info");
		btnbookingView.setBounds(231, 124, 82, 29);
		panel_dashboard.add(btnbookingView);
		btnbookingView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)	{
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sqlQuery = "SELECT * FROM Account WHERE email = ?";
				
				try {					
					//establish connection
					con = SQL_Interface.getConnection();
					pstmt = con.prepareStatement(sqlQuery);
					pstmt.setString(1, session.getEmail());
					rs = pstmt.executeQuery();
					while (rs.next()) {
						switchPanels(panel_viewEdit);
						fName_input.setText(rs.getString("fname"));
						lName_input.setText(rs.getString("lname"));
						mobileNumber_input.setText(rs.getString("mobileNo"));
						email_input.setText(rs.getString("email"));
						password_input.setText("***********");
						doorNumber_input.setText(rs.getString("doorNumber"));
						streetName_input.setText(rs.getString("streetName"));
						areaName_input.setText(rs.getString("areaName"));
						postcode_input.setText(rs.getString("postcode"));
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
		});

		JLabel lblyourProperties = new JLabel("2. Your Properties");
		lblyourProperties.setToolTipText("Your Properties");
		lblyourProperties.setForeground(Color.WHITE);
		lblyourProperties.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 17));
		lblyourProperties.setBorder(null);
		lblyourProperties.setBounds(25, 215, 165, 29);
		panel_dashboard.add(lblyourProperties);
		
		JButton btnyourProperties = new JButton("View");
		btnyourProperties.setForeground(new Color(128, 0, 0));
		btnyourProperties.setFont(new Font("Dialog", Font.BOLD, 13));
		btnyourProperties.setToolTipText("Click here to View your Properties");
		btnyourProperties.setBounds(208, 217, 106, 29);
		panel_dashboard.add(btnyourProperties);
		btnyourProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)	{
				switchPanels(panel_yourProperties);
			}
		});

		JLabel lblReviewBookings = new JLabel("3. Review Bookings");
		lblReviewBookings.setToolTipText("Review Bookings");
		lblReviewBookings.setForeground(Color.WHITE);
		lblReviewBookings.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 17));
		lblReviewBookings.setBorder(null);
		lblReviewBookings.setBounds(25, 299, 173, 29);
		panel_dashboard.add(lblReviewBookings);
		
	    JButton btnReviewBookings = new JButton("Click Here");
	    btnReviewBookings.setForeground(new Color(128, 0, 0));
		btnReviewBookings.setFont(new Font("Dialog", Font.BOLD, 13));
		btnReviewBookings.setToolTipText("Click here to Review");
		btnReviewBookings.setBounds(201, 302, 111, 29);
		panel_dashboard.add(btnReviewBookings);
		
		JLabel lblSignUp = new JLabel("4. Sign Up as Guest");
		lblSignUp.setToolTipText("Sign Up");
		lblSignUp.setForeground(Color.WHITE);
		lblSignUp.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		lblSignUp.setBorder(null);
		lblSignUp.setBounds(25, 369, 165, 29);
		panel_dashboard.add(lblSignUp);
		// Sign up host as guest also
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				PreparedStatement pstmt = null;

				String sql_accountType = "INSERT INTO Guest(email)" + "VALUES (?)" + 
											"ON DUPLICATE KEY UPDATE email = email";
				
				try {					
					//establish connection
					con = SQL_Interface.getConnection();
					con.setAutoCommit(false);
					
					pstmt = con.prepareStatement(sql_accountType);			//insert new row into Account
					pstmt.setString(1, session.getEmail());		
					
					int affectedRows_userType = pstmt.executeUpdate();
					
					if(affectedRows_userType == 1){
						//commit changes to DB
						con.commit();
						
						JOptionPane.showMessageDialog(null, "You've now also signed up as a Guest!", "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
					}
					else {	
						con.rollback(); 
						JOptionPane.showMessageDialog(null, "Sign-up failed! You may already be a Guest.", "Error", JOptionPane.ERROR_MESSAGE);
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

					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}

			}

		});
		btnSignUp.setToolTipText("Click here to Sign Up");
		btnSignUp.setForeground(new Color(128, 0, 0));
		btnSignUp.setFont(new Font("Dialog", Font.BOLD, 13));
		btnSignUp.setBounds(199, 370, 111, 29);
		panel_dashboard.add(btnSignUp);
		
		btnReviewBookings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)	{
				switchPanels(panel_reviewBookings);
			}
		});
		
		//Host view/edit panel content
		JLabel lblViewEdit = new JLabel("View your Information");
		lblViewEdit.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewEdit.setForeground(Color.WHITE);
		lblViewEdit.setFont(new Font("Dialog", Font.BOLD, 28));
		lblViewEdit.setBounds(102, 17, 459, 47);
		panel_viewEdit.add(lblViewEdit);
		
		fName_input = new JTextField();
		fName_input.setEditable(false);
		fName_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		fName_input.setToolTipText("Enter Name");
		fName_input.setLocale(Locale.UK);
		fName_input.setIgnoreRepaint(true);
		fName_input.setHorizontalAlignment(SwingConstants.LEFT);
		fName_input.setForeground(new Color(128, 0, 0));
		fName_input.setColumns(10);
		fName_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		fName_input.setBounds(263, 78, 201, 27);
		panel_viewEdit.add(fName_input);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblFirstName.setForeground(new Color(255, 255, 255));
		lblFirstName.setBorder(null);
		lblFirstName.setBounds(174, 83, 70, 16);
		panel_viewEdit.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblLastName.setForeground(new Color(255, 255, 255));
		lblLastName.setBorder(null);
		lblLastName.setBounds(174, 121, 70, 16);
		panel_viewEdit.add(lblLastName);
		
		lName_input = new JTextField();
		lName_input.setEditable(false);
		lName_input.setForeground(new Color(128, 0, 0));
		lName_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		lName_input.setToolTipText("Enter Last Name");
		lName_input.setLocale(Locale.UK);
		lName_input.setIgnoreRepaint(true);
		lName_input.setHorizontalAlignment(SwingConstants.LEFT);
		lName_input.setColumns(10);
		lName_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		lName_input.setBounds(263, 117, 201, 27);
		panel_viewEdit.add(lName_input);
		
		JLabel lblMobNo = new JLabel("Mobile Number");
		lblMobNo.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblMobNo.setForeground(new Color(255, 255, 255));
		lblMobNo.setBorder(null);
		lblMobNo.setBounds(148, 161, 99, 16);
		panel_viewEdit.add(lblMobNo);
		
		mobileNumber_input = new JTextField();
		mobileNumber_input.setEditable(false);
		mobileNumber_input.setForeground(new Color(128, 0, 0));
		mobileNumber_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		mobileNumber_input.setToolTipText("Enter Mobile Number");
		mobileNumber_input.setLocale(Locale.UK);
		mobileNumber_input.setIgnoreRepaint(true);
		mobileNumber_input.setHorizontalAlignment(SwingConstants.LEFT);
		mobileNumber_input.setColumns(10);
		mobileNumber_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		mobileNumber_input.setBounds(263, 156, 201, 27);
		panel_viewEdit.add(mobileNumber_input);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setBorder(null);
		lblEmail.setBounds(202, 205, 41, 16);
		panel_viewEdit.add(lblEmail);
		
		email_input = new JTextField();
		email_input.setEditable(false);
		email_input.setForeground(new Color(128, 0, 0));
		email_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		email_input.setToolTipText("Enter Email");
		email_input.setLocale(Locale.UK);
		email_input.setIgnoreRepaint(true);
		email_input.setHorizontalAlignment(SwingConstants.LEFT);
		email_input.setColumns(10);
		email_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		email_input.setBounds(263, 200, 201, 27);
		panel_viewEdit.add(email_input);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBorder(null);
		lblPassword.setBounds(178, 244, 65, 16);
		panel_viewEdit.add(lblPassword);
		
		password_input = new JPasswordField();
		password_input.setEditable(false);
		password_input.setForeground(new Color(128, 0, 0));
		password_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		password_input.setToolTipText("Enter Password");
		password_input.setEchoChar('*');
		password_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		password_input.setBounds(263, 239, 201, 27);
		panel_viewEdit.add(password_input);
		
		JLabel lblDoorNumber = new JLabel("Door Number");
		lblDoorNumber.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblDoorNumber.setForeground(new Color(255, 255, 255));
		lblDoorNumber.setToolTipText("Enter Door Number");
		lblDoorNumber.setBorder(null);
		lblDoorNumber.setBounds(156, 282, 88, 16);
		panel_viewEdit.add(lblDoorNumber);
		
		doorNumber_input = new JTextField();
		doorNumber_input.setEditable(false);
		doorNumber_input.setForeground(new Color(128, 0, 0));
		doorNumber_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		doorNumber_input.setToolTipText("Enter Door Number");
		doorNumber_input.setLocale(Locale.UK);
		doorNumber_input.setIgnoreRepaint(true);
		doorNumber_input.setHorizontalAlignment(SwingConstants.LEFT);
		doorNumber_input.setColumns(10);
		doorNumber_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		doorNumber_input.setBounds(262, 277, 202, 27);
		panel_viewEdit.add(doorNumber_input);
		
		JLabel lblStreetName = new JLabel("Street Name");
		lblStreetName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblStreetName.setForeground(new Color(255, 255, 255));
		lblStreetName.setToolTipText("Enter Street Name");
		lblStreetName.setBorder(null);
		lblStreetName.setBounds(163, 321, 80, 16);
		panel_viewEdit.add(lblStreetName);
		
		streetName_input = new JTextField();
		streetName_input.setEditable(false);
		streetName_input.setForeground(new Color(128, 0, 0));
		streetName_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		streetName_input.setToolTipText("Enter Surname");
		streetName_input.setLocale(Locale.UK);
		streetName_input.setIgnoreRepaint(true);
		streetName_input.setHorizontalAlignment(SwingConstants.LEFT);
		streetName_input.setColumns(10);
		streetName_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		streetName_input.setBounds(262, 316, 202, 27);
		panel_viewEdit.add(streetName_input);
		
		JLabel lblAreaName = new JLabel("Area Name");
		lblAreaName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblAreaName.setForeground(new Color(255, 255, 255));
		lblAreaName.setToolTipText("Enter City / County / Area");
		lblAreaName.setBorder(null);
		lblAreaName.setBounds(170, 360, 72, 16);
		panel_viewEdit.add(lblAreaName);
		
		areaName_input = new JTextField();
		areaName_input.setEditable(false);
		areaName_input.setForeground(new Color(128, 0, 0));
		areaName_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		areaName_input.setToolTipText("Enter Mobile Number");
		areaName_input.setLocale(Locale.UK);
		areaName_input.setIgnoreRepaint(true);
		areaName_input.setHorizontalAlignment(SwingConstants.LEFT);
		areaName_input.setColumns(10);
		areaName_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		areaName_input.setBounds(262, 355, 202, 27);
		panel_viewEdit.add(areaName_input);
		
		JLabel lblPostCode = new JLabel("Postcode");
		lblPostCode.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblPostCode.setForeground(new Color(255, 255, 255));
		lblPostCode.setToolTipText("Enter Postcode");
		lblPostCode.setBorder(null);
		lblPostCode.setBounds(182, 404, 61, 16);
		panel_viewEdit.add(lblPostCode);
		
		postcode_input = new JTextField();
		postcode_input.setEditable(false);
		postcode_input.setForeground(new Color(128, 0, 0));
		postcode_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		postcode_input.setToolTipText("Enter Post Code");
		postcode_input.setLocale(Locale.UK);
		postcode_input.setIgnoreRepaint(true);
		postcode_input.setHorizontalAlignment(SwingConstants.LEFT);
		postcode_input.setColumns(10);
		postcode_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		postcode_input.setBounds(262, 399, 202, 27);
		panel_viewEdit.add(postcode_input);
		
		JButton btnAddProperty = new JButton("Add New");
		btnAddProperty.setForeground(new Color(128, 0, 0));
		btnAddProperty.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAddProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new hostAddProperty(session).setVisible(true);
			}
		});
		btnAddProperty.setToolTipText("Click here to Add new property");
		btnAddProperty.setBounds(28, 414, 131, 38);
		panel_yourProperties.add(btnAddProperty);
		
		
		
		String[] colName = new String[] {"shortName","longDesc","postcode"};
		resultModel = new DefaultTableModel(colName,0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};	
		
		generateYourPropertiesTable(resultModel);
		
		hostpropertyTable = new JTable(resultModel);
		hostpropertyTable.setEnabled(false);
		hostpropertyTable.setRowSelectionAllowed(false);
		hostpropertyTable.setGridColor(new Color(128, 0, 0));
		hostpropertyTable.setForeground(new Color(128, 0, 0));
		hostpropertyTable.setFont(new Font("Dialog", Font.PLAIN, 15));
		hostpropertyTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		hostpropertyTable.setBackground(new Color(255, 204, 204));
		hostpropertyTable.setBounds(28, 74, 585, 306);
		hostpropertyTable.setRowHeight(35);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );	
		 for(int x=0;x<3;x++){
			 hostpropertyTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	     }
				
		panel_yourProperties.add(hostpropertyTable);
		
		
		String propertyShortNames[]=hostPropertyResults.toArray(new String[hostPropertyResults.size()]);
		
		JLabel lblhostviewProperty = new JLabel("Select Property to View:");
		lblhostviewProperty.setForeground(new Color(255, 240, 245));
		lblhostviewProperty.setFont(new Font("Dialog", Font.BOLD, 16));
		lblhostviewProperty.setBounds(195, 421, 198, 30);

		panel_yourProperties.add(lblhostviewProperty);
		
		JComboBox comboBox_viewProperty = new JComboBox();
		comboBox_viewProperty.setToolTipText("Select");
		comboBox_viewProperty.setModel(new DefaultComboBoxModel(propertyShortNames));		
		comboBox_viewProperty.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBox_viewProperty.setBounds(391, 422, 125, 27);
		panel_yourProperties.add(comboBox_viewProperty);
		
		JLabel lblheadingPropertiesname = new JLabel("Property Name");
		lblheadingPropertiesname.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		lblheadingPropertiesname.setOpaque(true);
		lblheadingPropertiesname.setBackground(new Color(255, 204, 204));
		lblheadingPropertiesname.setHorizontalAlignment(SwingConstants.CENTER);
		lblheadingPropertiesname.setForeground(new Color(128, 0, 0));
		lblheadingPropertiesname.setFont(new Font("Dialog", Font.BOLD, 17));
		lblheadingPropertiesname.setBounds(28, 34, 196, 40);
		panel_yourProperties.add(lblheadingPropertiesname);
		
		JLabel lblLongDescriptionHeader = new JLabel("Long Description");
		lblLongDescriptionHeader.setOpaque(true);
		lblLongDescriptionHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblLongDescriptionHeader.setForeground(new Color(128, 0, 0));
		lblLongDescriptionHeader.setFont(new Font("Dialog", Font.BOLD, 17));
		lblLongDescriptionHeader.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		lblLongDescriptionHeader.setBackground(new Color(255, 204, 204));
		lblLongDescriptionHeader.setBounds(220, 34, 197, 40);
		panel_yourProperties.add(lblLongDescriptionHeader);
		
		JLabel lblheadingPostCode = new JLabel("Post Code");
		lblheadingPostCode.setOpaque(true);
		lblheadingPostCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblheadingPostCode.setForeground(new Color(128, 0, 0));
		lblheadingPostCode.setFont(new Font("Dialog", Font.BOLD, 17));
		lblheadingPostCode.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		lblheadingPostCode.setBackground(new Color(255, 204, 204));
		lblheadingPostCode.setBounds(414, 34, 199, 40);
		panel_yourProperties.add(lblheadingPostCode);
		// View selected property host owns
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox_viewProperty.getSelectedIndex() >= 0) {
					new viewProperty(session, propertyViewEditIDs.get(comboBox_viewProperty.getSelectedIndex()), null, null).setVisible(true);
				}
			}
		});
		btnView.setToolTipText("Click to View");
		btnView.setForeground(new Color(128, 0, 0));
		btnView.setFont(new Font("Dialog", Font.BOLD, 14));
		btnView.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnView.setBounds(539, 422, 74, 30);
		panel_yourProperties.add(btnView);
		
		JLabel lblheadingProperties = new JLabel("Your Properties");
		lblheadingProperties.setBounds(197, 7, 222, 24);
		panel_yourProperties.add(lblheadingProperties);
		lblheadingProperties.setHorizontalAlignment(SwingConstants.CENTER);
		lblheadingProperties.setForeground(Color.WHITE);
		lblheadingProperties.setFont(new Font("Dialog", Font.BOLD, 21));
		
		JLabel lblReviewYourBookings = new JLabel("Review your Bookings");
		lblReviewYourBookings.setHorizontalAlignment(SwingConstants.CENTER);
		lblReviewYourBookings.setForeground(Color.WHITE);
		lblReviewYourBookings.setFont(new Font("Dialog", Font.BOLD, 20));
		lblReviewYourBookings.setBounds(179, 11, 291, 31);
		panel_reviewBookings.add(lblReviewYourBookings);
		
		String[] colName2 = new String[] {"Property Name","Booking From", "Booking To"};
		resultModel = new DefaultTableModel(colName2,0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};	
		
		generateReviewBookingsTable(resultModel);
		
		hostReviewBookingTable = new JTable(resultModel);
		hostReviewBookingTable.setRowSelectionAllowed(false);
		hostReviewBookingTable.setRowHeight(35);
		hostReviewBookingTable.setGridColor(new Color(128, 0, 0));
		hostReviewBookingTable.setForeground(new Color(128, 0, 0));
		hostReviewBookingTable.setFont(new Font("Dialog", Font.PLAIN, 15));
		hostReviewBookingTable.setEnabled(false);
		hostReviewBookingTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		hostReviewBookingTable.setBackground(new Color(255, 204, 204));
		hostReviewBookingTable.setBounds(25, 90, 585, 282);
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );	
		 for(int x=0;x<3;x++){
			 hostReviewBookingTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	     }		
		panel_reviewBookings.add(hostReviewBookingTable);
		
		JLabel lblreviewProperty = new JLabel("Select Property:");
		lblreviewProperty.setForeground(Color.WHITE);
		lblreviewProperty.setFont(new Font("Dialog", Font.BOLD, 16));
		lblreviewProperty.setBounds(18, 405, 125, 30);
		panel_reviewBookings.add(lblreviewProperty);
		
		String propertyShortNamesBooking[]=hostBookingResults.toArray(new String[hostBookingResults.size()]);
		
		JComboBox comboBox_selectBooking = new JComboBox();
		
		
		
		comboBox_selectBooking.setToolTipText("Select");
		comboBox_selectBooking.setModel(new DefaultComboBoxModel(propertyShortNamesBooking));				
		comboBox_selectBooking.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBox_selectBooking.setBounds(149, 408, 125, 27);
		panel_reviewBookings.add(comboBox_selectBooking);
		
		JLabel lblheadingPropertiesname_1 = new JLabel("Property Name");
		lblheadingPropertiesname_1.setOpaque(true);
		lblheadingPropertiesname_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblheadingPropertiesname_1.setForeground(new Color(128, 0, 0));
		lblheadingPropertiesname_1.setFont(new Font("Dialog", Font.BOLD, 17));
		lblheadingPropertiesname_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		lblheadingPropertiesname_1.setBackground(new Color(255, 204, 204));
		lblheadingPropertiesname_1.setBounds(25, 51, 194, 40);
		panel_reviewBookings.add(lblheadingPropertiesname_1);
		
		JLabel lblBookingDateFrom = new JLabel("Booking From");
		lblBookingDateFrom.setOpaque(true);
		lblBookingDateFrom.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookingDateFrom.setForeground(new Color(128, 0, 0));
		lblBookingDateFrom.setFont(new Font("Dialog", Font.BOLD, 17));
		lblBookingDateFrom.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		lblBookingDateFrom.setBackground(new Color(255, 204, 204));
		lblBookingDateFrom.setBounds(215, 51, 199, 40);
		panel_reviewBookings.add(lblBookingDateFrom);
		
		JToggleButton toggleBookingAccepted = new JToggleButton("Accept");
		
		buttonGroup.add(toggleBookingAccepted);
		toggleBookingAccepted.setForeground(new Color(128, 0, 0));
		toggleBookingAccepted.setToolTipText("Accept");
		toggleBookingAccepted.setFont(new Font("Dialog", Font.BOLD, 12));
		toggleBookingAccepted.setBounds(500, 388, 109, 29);
		panel_reviewBookings.add(toggleBookingAccepted);
		
		JToggleButton toggleBookingRefused = new JToggleButton("Refuse");
		
		buttonGroup.add(toggleBookingRefused);
		toggleBookingRefused.setForeground(new Color(128, 0, 0));
		toggleBookingRefused.setFont(new Font("Dialog", Font.BOLD, 12));
		toggleBookingRefused.setBounds(500, 432, 109, 29);
		panel_reviewBookings.add(toggleBookingRefused);
		
		comboBox_selectBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox_selectBooking.getSelectedIndex() >= 0) {
					int isAccepted = isBookingAccepted.get(comboBox_selectBooking.getSelectedIndex());
					if (isAccepted == -1) {
						toggleBookingAccepted.setEnabled(true);
						toggleBookingRefused.setEnabled(true);
					} else {
						toggleBookingAccepted.setEnabled(false);
						toggleBookingRefused.setEnabled(false);
					}
				}
			}
		});
		
		toggleBookingAccepted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox_selectBooking.getSelectedIndex() >= 0) {
					updateBooking((bookingToAcceptIDs.get(comboBox_selectBooking.getSelectedIndex())), 1);
					toggleBookingAccepted.setEnabled(false);
					toggleBookingRefused.setEnabled(false);
					new homepageHost(session).setVisible(true);
					switchPanels(panel_reviewBookings);
					dispose();
					
				}		
			}
		});
		
		toggleBookingRefused.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox_selectBooking.getSelectedIndex() >= 0) {
					updateBooking((bookingToAcceptIDs.get(comboBox_selectBooking.getSelectedIndex())), 2);
					toggleBookingAccepted.setEnabled(false);
					toggleBookingRefused.setEnabled(false);
				}
			}
		});
		
		JLabel lblBookingTo = new JLabel("Booking To");
		lblBookingTo.setOpaque(true);
		lblBookingTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookingTo.setForeground(new Color(128, 0, 0));
		lblBookingTo.setFont(new Font("Dialog", Font.BOLD, 17));
		lblBookingTo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		lblBookingTo.setBackground(new Color(255, 204, 204));
		lblBookingTo.setBounds(411, 51, 199, 40);
		panel_reviewBookings.add(lblBookingTo);
		
		JButton btnView_info = new JButton("View Info");
		btnView_info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox_selectBooking.getSelectedIndex() >= 0) {
					int searchIndex = comboBox_selectBooking.getSelectedIndex();
						new guestViewConfidentialInfo(session, propertyIDs.get(searchIndex),bookingToAcceptIDs.get(searchIndex)).setVisible(true);
	
				}
			}
		});
		btnView_info.setToolTipText("Click to View");
		btnView_info.setForeground(new Color(128, 0, 0));
		btnView_info.setFont(new Font("Dialog", Font.BOLD, 14));
		btnView_info.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnView_info.setBounds(326, 401, 109, 40);
		panel_reviewBookings.add(btnView_info);
	}
}
