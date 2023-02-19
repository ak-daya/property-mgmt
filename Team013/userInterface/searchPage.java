package userInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Container;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.MatteBorder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;

import backend.InputValidation;
import backend.Property;
import backend.SQL_Interface;
import backend.Session;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;

public class searchPage extends JFrame {

	private JPanel contentPane;
	private JLayeredPane layeredPane;	
	private JTextField txtLocation;
	public List<String> propertySearchResults = new ArrayList<String>();
	public JComboBox comboBox_viewProperty;
	private JTable search_results_table;
	private DefaultTableModel resultModel;

	/**
	 * Create the frame.
	 */
	public searchPage(Session session) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		String[] colName = new String[] {"Property Name","Long Desc"};
		resultModel = new DefaultTableModel(colName,0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};	
		
		search_results_table = new JTable(resultModel);
		search_results_table.setRowSelectionAllowed(false);
		search_results_table.setEnabled(false);
		search_results_table.setGridColor(Color.WHITE);
		search_results_table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(255, 255, 255)));
		search_results_table.setForeground(Color.WHITE);
		search_results_table.setFont(new Font("Dialog", Font.PLAIN, 15));
		search_results_table.setRowHeight(35);				
		search_results_table.setBounds(58, 173, 932, 350);
		panel.setLayout(null);
		search_results_table.setBackground(new Color(205, 92, 92));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );	
		 for(int x=0;x<2;x++){
			 search_results_table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
	     }
		
		 
		panel.add(search_results_table, BorderLayout.CENTER);
		panel.add(search_results_table.getTableHeader(), BorderLayout.NORTH);		

		
		txtLocation = new JTextField();
		txtLocation.setHorizontalAlignment(SwingConstants.LEFT);
		txtLocation.setIgnoreRepaint(true);
		txtLocation.setLocale(Locale.UK);
		txtLocation.setToolTipText("Where are you going?");
		txtLocation.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtLocation.setBounds(118, 89, 148, 27);
		panel.add(txtLocation);
		txtLocation.setColumns(10);
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Dialog", Font.BOLD, 13));
		lblLocation.setToolTipText("Enter Location");
		lblLocation.setBorder(null);
		lblLocation.setBounds(55, 94, 74, 16);
		panel.add(lblLocation);
		
		JLabel lblcheckIn = new JLabel("Check In");
		lblcheckIn.setFont(new Font("Dialog", Font.BOLD, 13));
		lblcheckIn.setBorder(null);
		lblcheckIn.setBounds(362, 93, 64, 16);
		panel.add(lblcheckIn);
		
		JLabel lblcheckOut = new JLabel("Check Out");
		lblcheckOut.setFont(new Font("Dialog", Font.BOLD, 13));
		lblcheckOut.setBorder(null);
		lblcheckOut.setBounds(629, 94, 74, 16);
		panel.add(lblcheckOut);
		
		JPanel panel_top = new JPanel();
		panel_top.setBounds(0, 0, 1041, 56);
		panel.add(panel_top);
		panel_top.setBackground(new Color(204, 51, 51));
		panel_top.setLayout(null);
		
		JLabel lblHomebreaks = new JLabel("HomeBreaks");
		lblHomebreaks.setBounds(6, 3, 176, 60);
		panel_top.add(lblHomebreaks);
		lblHomebreaks.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomebreaks.setForeground(Color.WHITE);
		lblHomebreaks.setFont(new Font("Dialog", Font.BOLD, 28));
		
		JLabel helloMessage = new JLabel(userTypeText(session.getUserType()));
		helloMessage.setForeground(Color.WHITE);
		helloMessage.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		helloMessage.setBorder(null);
		helloMessage.setBounds(458, 23, 114, 29);
		panel_top.add(helloMessage);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnLogin.setForeground(new Color(128, 0, 0));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login().setVisible(true);
				dispose();
			}
		});
		btnLogin.setBounds(794, 13, 97, 34);
		btnLogin.setToolTipText("Click to Login");
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_top.add(btnLogin);
		btnLogin.setVisible(false);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnSignUp.setForeground(new Color(128, 0, 0));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new signupPage().setVisible(true);
				dispose();
			}
		});
		btnSignUp.setBounds(900, 13, 88, 33);
		btnSignUp.setToolTipText("Click to Sign Up");
		btnSignUp.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_top.add(btnSignUp);
		btnSignUp.setVisible(false);
		
		
		if(session.getUserType() == 'e') {
			btnLogin.setVisible(true);
			btnSignUp.setVisible(true);
		}
		
		
		JLabel lblPlacesToStay = new JLabel("Places to Stay");
		lblPlacesToStay.setBounds(121, 8, 222, 61);
		panel_top.add(lblPlacesToStay);
		lblPlacesToStay.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlacesToStay.setFont(new Font("Dialog", Font.PLAIN, 14));
		lblPlacesToStay.setForeground(new Color(255, 255, 255));
		
		JButton btnbackToHomepage = new JButton("Back");
		btnbackToHomepage.setBounds(678, 13, 106, 34);
		panel_top.add(btnbackToHomepage);
		btnbackToHomepage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				session.startSession();
				dispose();
			}
		});
		btnbackToHomepage.setToolTipText("Click to go back");
		btnbackToHomepage.setForeground(new Color(128, 0, 0));
		btnbackToHomepage.setFont(new Font("Dialog", Font.BOLD, 14));
		btnbackToHomepage.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		
		//Creating calendar and date objects in order to get the current date
		SimpleDateFormat mySqlDate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar myCal = Calendar.getInstance();
		Date todayDate = myCal.getTime();
		//Getting date for preceding day
		myCal.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrowDate = myCal.getTime();
		
		JDateChooser checkInDate = new JDateChooser();
		checkInDate.setBounds(426, 89, 148, 26);
		checkInDate.setDate(todayDate);
		panel.add(checkInDate);
		
		JDateChooser checkOutDate = new JDateChooser();
		checkOutDate.setBounds(702, 89, 142, 26);
		checkOutDate.setDate(tomorrowDate);
		panel.add(checkOutDate);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnSearch.setForeground(new Color(128, 0, 0));
		btnSearch.addActionListener(new ActionListener() {
				 
		public void actionPerformed(ActionEvent e) {

				//Capturing information from the entry boxes
				String locationToSearch = txtLocation.getText().replaceAll("\\s","");
				//Date formatting for mySql format and checking
				String inDate = mySqlDate.format(checkInDate.getDate());
				String outDate = mySqlDate.format(checkOutDate.getDate());
				//Converting to date objects to allow error checking
				LocalDate inDateToCheck = LocalDate.parse(inDate);
				LocalDate outDateToCheck = LocalDate.parse(outDate);
				
				//error checking for null values and checking dates are valid
				
				if (!locationToSearch.isEmpty() && (!inDate.isEmpty() && !outDate.isEmpty())) {
					try {
						if (!InputValidation.dateCheckNotPast(inDateToCheck, outDateToCheck)) {
							JOptionPane.showMessageDialog(null, "Can't plan a stay in the past!.", "Error", JOptionPane.ERROR_MESSAGE);
						} else if (!InputValidation.checkInDateValid(inDateToCheck, outDateToCheck)) {
							JOptionPane.showMessageDialog(null, "Check out date must be after check in date.", "Error", JOptionPane.ERROR_MESSAGE);
						} 
						else {
							resultModel.setRowCount(0);
							generateResultsForTable(resultModel, locationToSearch, checkInDate, checkOutDate);
						}
					}
					catch(Exception ex) {
						ex.printStackTrace();
					}
				} else	{
					JOptionPane.showMessageDialog(null, "Some fields may be incomplete. Please check again.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnSearch.setToolTipText("Click to Search");
		btnSearch.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSearch.setBounds(881, 79, 109, 42);
		panel.add(btnSearch);
		
		JLabel lblsearchviewProperty = new JLabel("Select Property to View:");
		lblsearchviewProperty.setForeground(new Color(128, 0, 0));
		lblsearchviewProperty.setFont(new Font("Dialog", Font.BOLD, 16));
		lblsearchviewProperty.setBounds(324, 545, 198, 30);
		panel.add(lblsearchviewProperty);
		
		List<String> propertySearchResults = new ArrayList<String>();
		String propertyShortNames[]=propertySearchResults.toArray(new String[propertySearchResults.size()]);
		
		comboBox_viewProperty = new JComboBox();
		comboBox_viewProperty.setToolTipText("Select");
		comboBox_viewProperty.setModel(new DefaultComboBoxModel(propertyShortNames));		
		comboBox_viewProperty.setFont(new Font("Dialog", Font.BOLD, 14));
		comboBox_viewProperty.setBounds(542, 547, 125, 27);
		panel.add(comboBox_viewProperty);

		
		JLabel lblheadingPropertiesname = new JLabel("Property Name");
		lblheadingPropertiesname.setOpaque(true);
		lblheadingPropertiesname.setHorizontalAlignment(SwingConstants.CENTER);
		lblheadingPropertiesname.setForeground(Color.WHITE);
		lblheadingPropertiesname.setFont(new Font("Dialog", Font.BOLD, 17));
		lblheadingPropertiesname.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(255, 255, 255)));
		lblheadingPropertiesname.setBackground(new Color(205, 92, 92));
		lblheadingPropertiesname.setBounds(58, 136, 465, 40);
		panel.add(lblheadingPropertiesname);
		
		
		JLabel lblLongDescriptionHeader = new JLabel("Long Description");
		lblLongDescriptionHeader.setOpaque(true);
		lblLongDescriptionHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblLongDescriptionHeader.setForeground(Color.WHITE);
		lblLongDescriptionHeader.setFont(new Font("Dialog", Font.BOLD, 17));
		lblLongDescriptionHeader.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(255, 255, 255)));
		lblLongDescriptionHeader.setBackground(new Color(205, 92, 92));
		lblLongDescriptionHeader.setBounds(521, 136, 469, 40);
		panel.add(lblLongDescriptionHeader);
		
		JButton btnviewProperty = new JButton("View Property");
		btnviewProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs=null;
				PreparedStatement pstmt =null;
				Connection con = null;
				
				String propertyName = "";
				int propertyID = -1;
				
				try {
					
					con = SQL_Interface.getConnection();
					String sqlQuery = "SELECT PropertyID FROM Property "
									+ "WHERE Property.shortName = ? ";
					
					if(comboBox_viewProperty.getItemCount() != 0) {
						propertyName = comboBox_viewProperty.getSelectedItem().toString();

						pstmt = con.prepareStatement(sqlQuery);
						pstmt.setString(1, propertyName);
						
						rs = pstmt.executeQuery();	
											
						while (rs.next()) {
							propertyID = rs.getInt("propertyID");
						}
						new viewProperty(session, propertyID, checkInDate.getDate(), checkOutDate.getDate()).setVisible(true);	//add dates selected
					}
				} 
				catch (/*SQL*/Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				finally {
					
						try {
							if(rs != null)  	rs.close();
				            if(pstmt != null) pstmt.close();
				            if(con != null) 	con.close();
						} catch (SQLException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
		           
				}
			}
		});
		
		btnviewProperty.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnviewProperty.setToolTipText("Click to View");
		btnviewProperty.setForeground(new Color(128, 0, 0));
		btnviewProperty.setFont(new Font("Dialog", Font.BOLD, 14));
		btnviewProperty.setBounds(732, 541, 148, 42);
		panel.add(btnviewProperty);
	}
	
	private String userTypeText(char userType) {
		String text = "";
		switch (userType) {
			case 'e':
				text = "Enquirer";
				break;
			case 'h':
				text = "Host";
				break;
			case 'g':
				text = "Guest";
				break;
			default:
				text = "Error";
		};
		return text;
	}

	
public void generateResultsForTable(DefaultTableModel resultModel, String locationToSearch, JDateChooser checkInDate, JDateChooser checkOutDate) {
		SimpleDateFormat mySqlDate = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs=null;
		PreparedStatement pstmt =null;
		Connection con = null;
		
		String checkIn = mySqlDate.format(checkInDate.getDate());
		String checkOut = mySqlDate.format(checkOutDate.getDate());
		
		java.sql.Date requestStart = java.sql.Date.valueOf(checkIn);
		java.sql.Date requestEnd = java.sql.Date.valueOf(checkOut);
		
		String location = locationToSearch + "%";
		
		this.propertySearchResults.clear();
		
		
		try {
			
			con = SQL_Interface.getConnection();
			String sqlQuery = "SELECT PropertyID, shortName, longDesc FROM Property "
							+ "WHERE Property.areaName LIKE ? " 
							+ "AND Property.propertyID NOT IN ( "
							+ "SELECT * FROM ( "
							+ "SELECT Booking.propertyID FROM Booking "
							+ "WHERE ("
							+ " ((? >= Booking.startDate AND ? <= Booking.endDate)"
							+ " OR (? >= Booking.startDate AND ? <= Booking.endDate)"
							+ " OR (Booking.startDate >= ? AND Booking.endDate <= ?))"
							+ " AND Booking.acceptedByHost = 1"
							+ ")) AS subquery );";
			
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setString(1, location);
			pstmt.setDate(2, requestStart);
			pstmt.setDate(3, requestStart );
			pstmt.setDate(4, requestEnd);
			pstmt.setDate(5, requestEnd);
			pstmt.setDate(6, requestStart);
			pstmt.setDate(7, requestEnd);
			
			rs = pstmt.executeQuery();	
			int PropertyID = -1;
			String shortName="";
			String longDesc="";	
			
			while (rs.next()) {
				PropertyID = rs.getInt("propertyID");
				shortName = rs.getString("shortName");
				longDesc = rs.getString("longDesc");
				this.propertySearchResults.add(shortName);
				this.resultModel.addRow(new Object[] {shortName,longDesc});	
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
				try {
					if(rs != null)  	rs.close();
		            if(pstmt != null) pstmt.close();
		            if(con != null) 	con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.comboBox_viewProperty.removeAllItems();
				this.comboBox_viewProperty.setModel(new DefaultComboBoxModel(this.propertySearchResults.toArray(new String[this.propertySearchResults.size()])));
		}
	}
}
