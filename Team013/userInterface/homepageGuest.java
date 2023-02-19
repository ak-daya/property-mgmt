package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
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
import java.sql.Statement;
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
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.CardLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class homepageGuest extends JFrame {
	private JPanel contentPane;
	private JLayeredPane layeredPane;
	private JTextField fName_input;
	private JTextField lName_input;
	private JTextField mobileNumber_input;
	private JTextField email_input;
	private JPasswordField password_input;
	private JTextField doorNumber_input;
	private JTextField streetName_input;
	private JTextField areaName_input;
	private JTextField postcode_input;
	private List<Integer> isAcceptedByHost = new ArrayList<>();
	private List<Integer> bookingIDs = new ArrayList<>();
	private List<Integer> propertyIDs = new ArrayList<>();
	public List<String> propertySearchResults = new ArrayList<String>();
	private JTable existingBookingTable;
	private DefaultTableModel resultModel;	
	public Session session;
	
	/**
	 * Launch the application.
	 */

	public void switchPanels(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.validate();
	}
	
public void generateResultsForTable(DefaultTableModel resultModel) {
		
		ResultSet rs=null;
		PreparedStatement pstmt =null;
		Connection con = null;
		try {
			con = SQL_Interface.getConnection();
			String sqlQuery = "SELECT Property.propertyID, Property.shortName, Booking.bookingID, Booking.acceptedByHost "
					+ "FROM Booking INNER JOIN Property ON Booking.propertyID = Property.propertyID "
					+ "INNER JOIN Guest ON Guest.guestID = Booking.guestID WHERE email = ?";		
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setString(1, session.getEmail());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int propertyID = rs.getInt("propertyID");
				int bookingID = rs.getInt("bookingID");
				String shortName = rs.getString("shortName");
				int acceptedByHost = rs.getInt("acceptedByHost");
				this.bookingIDs.add(bookingID);
				this.propertyIDs.add(propertyID);
				this.propertySearchResults.add(shortName);
				this.isAcceptedByHost.add(acceptedByHost);
				if(acceptedByHost == 1) {
					this.resultModel.addRow(new Object[] {shortName,"Confirmed"});
				}
				else if(acceptedByHost == 0) {
					this.resultModel.addRow(new Object[] {shortName,"Declined"});
				}
				else if (acceptedByHost == -1){
					this.resultModel.addRow(new Object[] {shortName,"Pending"});
				}
							
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
	
	/**
	 * Create the frame.
	 */
	public homepageGuest(Session session) {
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
		
		//main content pane that other panels fit into
		JPanel panel_main = new JPanel();
		panel_main.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		panel_main.setFont(new Font("Dialog", Font.BOLD, 13));
		panel_main.setBackground(new Color(255, 204, 204));
		panel_main.setBounds(0, 0, 1030, 601);
		contentPane.add(panel_main);
		panel_main.setLayout(null);
		
		//top search bar
		JPanel panel_top = new JPanel();
		panel_top.setLayout(null);
		panel_top.setBackground(new Color(204, 51, 51));
		panel_top.setBounds(0, 0, 1030, 84);
		panel_main.add(panel_top);
		
		JLabel lblHomebreaks = new JLabel("HomeBreaks");
		lblHomebreaks.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomebreaks.setForeground(Color.WHITE);
		lblHomebreaks.setFont(new Font("Dialog", Font.BOLD, 28));
		lblHomebreaks.setBounds(6, 15, 176, 47);
		panel_top.add(lblHomebreaks);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new searchPage(session).setVisible(true);
				dispose();
			}
		});
		btnSearch.setToolTipText("Click to Sign Up");
		btnSearch.setForeground(new Color(128, 0, 0));
		btnSearch.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSearch.setBounds(791, 26, 94, 36);
		panel_top.add(btnSearch);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login().setVisible(true);
				dispose();
								
			}
		});
		btnLogout.setToolTipText("Click to Logout");
		btnLogout.setForeground(new Color(128, 0, 0));
		btnLogout.setFont(new Font("Dialog", Font.BOLD, 14));
		btnLogout.setBounds(905, 26, 100, 36);
		panel_top.add(btnLogout);
		
		JLabel helloMessage = new JLabel("Hello,");
		helloMessage.setToolTipText("Your Bookings");
		helloMessage.setForeground(Color.WHITE);
		helloMessage.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		helloMessage.setBorder(null);
		helloMessage.setBounds(242, 28, 55, 29);
		panel_top.add(helloMessage);
		
		JLabel userEmail = new JLabel(session.getEmail()); // PERSON NAME		
		userEmail.setToolTipText("Your Bookings");
		userEmail.setForeground(Color.WHITE);
		userEmail.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		userEmail.setBorder(null);
		userEmail.setBounds(295, 28, 343, 29);
		panel_top.add(userEmail);
    
		//defining layered pane for panel switching
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(385, 105, 638, 478);
		panel_main.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		JPanel panel_introduction = new JPanel();
		panel_introduction.setLayout(null);
		panel_introduction.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_introduction.setBackground(new Color(205, 92, 92));
		layeredPane.add(panel_introduction, "name_173776782369354");
		
		
		JPanel panel_yourBookings = new JPanel();
		layeredPane.add(panel_yourBookings, "name_170703165265016");
		panel_yourBookings.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_yourBookings.setBackground(new Color(205, 92, 92));
		panel_yourBookings.setLayout(null);
		
		JPanel panel_viewEdit = new JPanel();
		panel_viewEdit.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_viewEdit.setBackground(new Color(205, 92, 92));
		layeredPane.add(panel_viewEdit, "name_171126336308239");
		panel_viewEdit.setLayout(null);
		
		//image that appears on the RHS of the dashboard	
		JLabel lblDashboardImg = new JLabel("Travel Image");
		lblDashboardImg.setIcon(new ImageIcon(homepageGuest.class.getResource("/imagesUI/travel2.jpg")));
		lblDashboardImg.setBounds(17, 64, 603, 352);
		panel_introduction.add(lblDashboardImg);	
		
		//dashboard panel, the options that appear for the guest on the LHS
		JPanel panel_dashboard = new JPanel();
		panel_dashboard.setBackground(new Color(204, 102, 102));
		panel_dashboard.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(153, 0, 0), null, null, null));
		panel_dashboard.setBounds(19, 105, 325, 478);
		panel_main.add(panel_dashboard);
		panel_dashboard.setLayout(null);
		
		JLabel lblYourDashboard = new JLabel("Your Dashboard");
		lblYourDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourDashboard.setForeground(Color.WHITE);
		lblYourDashboard.setFont(new Font("Dialog", Font.BOLD, 28));
		lblYourDashboard.setBounds(25, 28, 276, 47);
		panel_dashboard.add(lblYourDashboard);
		
		JLabel lblYourBookings = new JLabel("1. Your Bookings");
		lblYourBookings.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 17));
		lblYourBookings.setToolTipText("Your Bookings");
		lblYourBookings.setForeground(Color.WHITE);
		lblYourBookings.setBorder(null);
		lblYourBookings.setBounds(25, 126, 148, 29);
		panel_dashboard.add(lblYourBookings);
		
		JButton btnBookingView = new JButton("View Bookings");
		btnBookingView.setFont(new Font("Dialog", Font.BOLD, 12));
		btnBookingView.setForeground(new Color(128, 0, 0));
		btnBookingView.setBounds(189, 128, 123, 29);
		panel_dashboard.add(btnBookingView);
		btnBookingView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)	{
				switchPanels(panel_yourBookings);
			}	
		});
    
		JLabel lblGuestInfo = new JLabel("2. View Information");
		lblGuestInfo.setToolTipText("View");
		lblGuestInfo.setForeground(Color.WHITE);
		lblGuestInfo.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 17));
		lblGuestInfo.setBorder(null);
		lblGuestInfo.setBounds(25, 216, 165, 29);
		panel_dashboard.add(lblGuestInfo);
		
		JButton btnviewEditinfo = new JButton("Click Here");
		btnviewEditinfo.setForeground(new Color(128, 0, 0));
		btnviewEditinfo.setFont(new Font("Dialog", Font.BOLD, 12));
		btnviewEditinfo.setBounds(202, 218, 106, 29);
		panel_dashboard.add(btnviewEditinfo);
		btnviewEditinfo.addActionListener(new ActionListener() {
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
		
		JLabel lblSignupHost = new JLabel("3. Sign Up as Host");
		lblSignupHost.setToolTipText("Sign Up");
		lblSignupHost.setForeground(Color.WHITE);
		lblSignupHost.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 17));
		lblSignupHost.setBorder(null);
		lblSignupHost.setBounds(25, 299, 221, 29);
		panel_dashboard.add(lblSignupHost);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection con = null;
				PreparedStatement pstmt = null;
				
				String sql_accountType = "INSERT INTO Host(email)" + "VALUES (?)" + 
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
						
						JOptionPane.showMessageDialog(null, "You've now also signed up as a Host!", "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
					}
					else {	
						con.rollback(); 
						JOptionPane.showMessageDialog(null, "Sign-up failed! You may already be a Host.", "Error", JOptionPane.ERROR_MESSAGE);
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
		btnSignUp.setFont(new Font("Dialog", Font.BOLD, 12));
		btnSignUp.setForeground(new Color(128, 0, 0));
		btnSignUp.setToolTipText("Click here to Sign Up");
		btnSignUp.setBounds(197, 301, 111, 29);
		panel_dashboard.add(btnSignUp);
		
		//bookings panel content
		JLabel lblYourExisting = new JLabel("Your Existing / Upcoming Bookings");
		lblYourExisting.setBounds(121, 9, 413, 47);
		panel_yourBookings.add(lblYourExisting);
		lblYourExisting.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourExisting.setForeground(Color.WHITE);
		lblYourExisting.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JLabel lblheadingPropertiesname = new JLabel("Property Name");
		lblheadingPropertiesname.setOpaque(true);
		lblheadingPropertiesname.setHorizontalAlignment(SwingConstants.CENTER);
		lblheadingPropertiesname.setForeground(new Color(128, 0, 0));
		lblheadingPropertiesname.setFont(new Font("Dialog", Font.BOLD, 17));
		lblheadingPropertiesname.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		lblheadingPropertiesname.setBackground(new Color(255, 204, 204));
		lblheadingPropertiesname.setBounds(28, 58, 293, 40);
		panel_yourBookings.add(lblheadingPropertiesname);
		
		JLabel lblStatusHeader = new JLabel("Booking Status");
		lblStatusHeader.setOpaque(true);
		lblStatusHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatusHeader.setForeground(new Color(128, 0, 0));
		lblStatusHeader.setFont(new Font("Dialog", Font.BOLD, 17));
		lblStatusHeader.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		lblStatusHeader.setBackground(new Color(255, 204, 204));
		lblStatusHeader.setBounds(320, 58, 293, 40);
		panel_yourBookings.add(lblStatusHeader);
		
		String[] colName = new String[] {"Property Name","Booking Status"};
		resultModel = new DefaultTableModel(colName,0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};	
		
		generateResultsForTable(resultModel);
		
		existingBookingTable = new JTable(resultModel);
		existingBookingTable.setRowSelectionAllowed(false);
		existingBookingTable.setRowHeight(35);
		existingBookingTable.setGridColor(new Color(128, 0, 0));
		existingBookingTable.setForeground(new Color(128, 0, 0));
		existingBookingTable.setFont(new Font("Dialog", Font.PLAIN, 15));
		existingBookingTable.setEnabled(false);
		existingBookingTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		existingBookingTable.setBackground(new Color(255, 204, 204));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );	
		 for(int x=0;x<2;x++){
			 existingBookingTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	     }		
		existingBookingTable.setBounds(28, 96, 585, 272);
		panel_yourBookings.add(existingBookingTable);
		
		JLabel lblsearchviewProperty = new JLabel("Select Booking to View:");
		lblsearchviewProperty.setForeground(Color.WHITE);
		lblsearchviewProperty.setFont(new Font("Dialog", Font.BOLD, 16));
		lblsearchviewProperty.setBounds(57, 401, 198, 30);
		panel_yourBookings.add(lblsearchviewProperty);
		
		String propertyShortNames[]=propertySearchResults.toArray(new String[propertySearchResults.size()]);
		
		JComboBox comboBox_viewProperty = new JComboBox();
		comboBox_viewProperty.setToolTipText("Select");
		comboBox_viewProperty.setModel(new DefaultComboBoxModel(propertyShortNames));				
		comboBox_viewProperty.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBox_viewProperty.setBounds(275, 403, 125, 27);
		panel_yourBookings.add(comboBox_viewProperty);
		
		JButton btnviewProperty = new JButton("View Info");
		btnviewProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox_viewProperty.getSelectedIndex() >= 0) {
					int searchIndex = comboBox_viewProperty.getSelectedIndex();
					if (isAcceptedByHost.get(searchIndex)==1) {
						new guestViewConfidentialInfo(session, propertyIDs.get(searchIndex),bookingIDs.get(searchIndex)).setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "The Host hasn't accepted your booking!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnviewProperty.setToolTipText("Click to View");
		btnviewProperty.setForeground(new Color(128, 0, 0));
		btnviewProperty.setFont(new Font("Dialog", Font.BOLD, 14));
		btnviewProperty.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnviewProperty.setBounds(465, 397, 148, 42);
		panel_yourBookings.add(btnviewProperty);
    
		//Guest view/edit panel content
		JLabel lblViewEdit = new JLabel("View your Information");
		lblViewEdit.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewEdit.setForeground(Color.WHITE);
		lblViewEdit.setFont(new Font("Dialog", Font.BOLD, 28));
		lblViewEdit.setBounds(86, 11, 459, 47);
		panel_viewEdit.add(lblViewEdit);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblFirstName.setBorder(null);
		lblFirstName.setBounds(158, 77, 70, 16);
		panel_viewEdit.add(lblFirstName);
		
		fName_input = new JTextField();
		fName_input.setEditable(false);
		fName_input.setToolTipText("Enter Name");
		fName_input.setLocale(Locale.UK);
		fName_input.setIgnoreRepaint(true);
		fName_input.setHorizontalAlignment(SwingConstants.LEFT);
		fName_input.setForeground(new Color(128, 0, 0));
		fName_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		fName_input.setColumns(10);
		fName_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		fName_input.setBounds(247, 72, 201, 27);
		panel_viewEdit.add(fName_input);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setForeground(Color.WHITE);
		lblLastName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblLastName.setBorder(null);
		lblLastName.setBounds(158, 115, 70, 16);
		panel_viewEdit.add(lblLastName);
		
		lName_input = new JTextField();
		lName_input.setEditable(false);
		lName_input.setToolTipText("Enter Last Name");
		lName_input.setLocale(Locale.UK);
		lName_input.setIgnoreRepaint(true);
		lName_input.setHorizontalAlignment(SwingConstants.LEFT);
		lName_input.setForeground(new Color(128, 0, 0));
		lName_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		lName_input.setColumns(10);
		lName_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		lName_input.setBounds(247, 111, 201, 27);
		panel_viewEdit.add(lName_input);
		
		JLabel lblMobNo = new JLabel("Mobile Number");
		lblMobNo.setForeground(Color.WHITE);
		lblMobNo.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblMobNo.setBorder(null);
		lblMobNo.setBounds(132, 155, 99, 16);
		panel_viewEdit.add(lblMobNo);
		
		mobileNumber_input = new JTextField();
		mobileNumber_input.setEditable(false);
		mobileNumber_input.setToolTipText("Enter Mobile Number");
		mobileNumber_input.setLocale(Locale.UK);
		mobileNumber_input.setIgnoreRepaint(true);
		mobileNumber_input.setHorizontalAlignment(SwingConstants.LEFT);
		mobileNumber_input.setForeground(new Color(128, 0, 0));
		mobileNumber_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		mobileNumber_input.setColumns(10);
		mobileNumber_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		mobileNumber_input.setBounds(247, 150, 201, 27);
		panel_viewEdit.add(mobileNumber_input);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblEmail.setBorder(null);
		lblEmail.setBounds(186, 199, 41, 16);
		panel_viewEdit.add(lblEmail);
		
		email_input = new JTextField();
		email_input.setEditable(false);
		email_input.setToolTipText("Enter Email");
		email_input.setLocale(Locale.UK);
		email_input.setIgnoreRepaint(true);
		email_input.setHorizontalAlignment(SwingConstants.LEFT);
		email_input.setForeground(new Color(128, 0, 0));
		email_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		email_input.setColumns(10);
		email_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		email_input.setBounds(247, 194, 201, 27);
		panel_viewEdit.add(email_input);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblPassword.setBorder(null);
		lblPassword.setBounds(162, 238, 65, 16);
		panel_viewEdit.add(lblPassword);
		
		password_input = new JPasswordField();
		password_input.setEditable(false);
		password_input.setToolTipText("Enter Password");
		password_input.setForeground(new Color(128, 0, 0));
		password_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		password_input.setEchoChar('*');
		password_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		password_input.setBounds(247, 233, 201, 27);
		panel_viewEdit.add(password_input);
		
		JLabel lblDoorNumber = new JLabel("Door Number");
		lblDoorNumber.setToolTipText("Enter Door Number");
		lblDoorNumber.setForeground(Color.WHITE);
		lblDoorNumber.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblDoorNumber.setBorder(null);
		lblDoorNumber.setBounds(140, 276, 88, 16);
		panel_viewEdit.add(lblDoorNumber);
		
		doorNumber_input = new JTextField();
		doorNumber_input.setEditable(false);
		doorNumber_input.setToolTipText("Enter Door Number");
		doorNumber_input.setLocale(Locale.UK);
		doorNumber_input.setIgnoreRepaint(true);
		doorNumber_input.setHorizontalAlignment(SwingConstants.LEFT);
		doorNumber_input.setForeground(new Color(128, 0, 0));
		doorNumber_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		doorNumber_input.setColumns(10);
		doorNumber_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		doorNumber_input.setBounds(246, 271, 202, 27);
		panel_viewEdit.add(doorNumber_input);
		
		JLabel lblStreetName = new JLabel("Street Name");
		lblStreetName.setToolTipText("Enter Street Name");
		lblStreetName.setForeground(Color.WHITE);
		lblStreetName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblStreetName.setBorder(null);
		lblStreetName.setBounds(147, 315, 80, 16);
		panel_viewEdit.add(lblStreetName);
		
		streetName_input = new JTextField();
		streetName_input.setEditable(false);
		streetName_input.setToolTipText("Enter Surname");
		streetName_input.setLocale(Locale.UK);
		streetName_input.setIgnoreRepaint(true);
		streetName_input.setHorizontalAlignment(SwingConstants.LEFT);
		streetName_input.setForeground(new Color(128, 0, 0));
		streetName_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		streetName_input.setColumns(10);
		streetName_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		streetName_input.setBounds(246, 310, 202, 27);
		panel_viewEdit.add(streetName_input);
		
		JLabel lblAreaName = new JLabel("Area Name");
		lblAreaName.setToolTipText("Enter City / County / Area");
		lblAreaName.setForeground(Color.WHITE);
		lblAreaName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblAreaName.setBorder(null);
		lblAreaName.setBounds(154, 354, 72, 16);
		panel_viewEdit.add(lblAreaName);
		
		areaName_input = new JTextField();
		areaName_input.setEditable(false);
		areaName_input.setToolTipText("Enter Mobile Number");
		areaName_input.setLocale(Locale.UK);
		areaName_input.setIgnoreRepaint(true);
		areaName_input.setHorizontalAlignment(SwingConstants.LEFT);
		areaName_input.setForeground(new Color(128, 0, 0));
		areaName_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		areaName_input.setColumns(10);
		areaName_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		areaName_input.setBounds(246, 349, 202, 27);
		panel_viewEdit.add(areaName_input);
		
		JLabel lblPostCode = new JLabel("Postcode");
		lblPostCode.setToolTipText("Enter Postcode");
		lblPostCode.setForeground(Color.WHITE);
		lblPostCode.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblPostCode.setBorder(null);
		lblPostCode.setBounds(166, 398, 61, 16);
		panel_viewEdit.add(lblPostCode);
		
		postcode_input = new JTextField();
		postcode_input.setEditable(false);
		postcode_input.setToolTipText("Enter Post Code");
		postcode_input.setLocale(Locale.UK);
		postcode_input.setIgnoreRepaint(true);
		postcode_input.setHorizontalAlignment(SwingConstants.LEFT);
		postcode_input.setForeground(new Color(128, 0, 0));
		postcode_input.setFont(new Font("Dialog", Font.PLAIN, 14));
		postcode_input.setColumns(10);
		postcode_input.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		postcode_input.setBounds(246, 393, 202, 27);
		panel_viewEdit.add(postcode_input);
	}
}
