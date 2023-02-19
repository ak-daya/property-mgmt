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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import com.toedter.calendar.JDateChooser;

import backend.Bathroom;
import backend.Bedroom;
import backend.ChargeBand;
import backend.InputValidation;
import backend.Property;
import backend.PropertyManager;
import backend.SQL_Interface;
import backend.Session;

import javax.swing.border.BevelBorder;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.CardLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;

public class hostAddProperty extends JFrame {

	private JPanel contentPane;
	private JLayeredPane layeredPane;
	public Session session; // = new Session('g',"a@am.com");
	private JTextField txtmaxGuests;
	private JTextField txtAreaName;
	private JTextField txtPostCode;
	private JTextField txtDoorNumber;
	private JTextField txtStreetName;
	private JTextField txtpropertyName;
	private JTextField txtpricePerNight;
	private JTextField txtCleaningPrice;
	private JTextField txtserviceCharges;
	private JTextField textBathrooms;
	private JTextField txtBedrooms;
	
	public ArrayList<ChargeBand> chargeBandsToAdd = new ArrayList<ChargeBand>();

	public void switchPanels(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.validate();
	}
	
	
	/**
	 * Create the frame.
	 */
	public hostAddProperty(Session session) {
		
		Property propertyToAdd = new Property();
		ArrayList<Bedroom> bedroomsToAdd = new ArrayList<Bedroom>();
        ArrayList<Bathroom> bathroomsToAdd = new ArrayList<Bathroom>();
		
		this.session = session;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		panel_main.setBounds(0, 0, 1039, 600);
		contentPane.add(panel_main);
		panel_main.setLayout(null);
		
		//top search bar
		JPanel panel_top = new JPanel();
		panel_top.setLayout(null);
		panel_top.setBackground(new Color(204, 51, 51));
		panel_top.setBounds(0, 0, 1039, 84);
		panel_main.add(panel_top);
		
		JLabel lblHomebreaks = new JLabel("HomeBreaks");
		lblHomebreaks.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomebreaks.setForeground(Color.WHITE);
		lblHomebreaks.setFont(new Font("Dialog", Font.BOLD, 28));
		lblHomebreaks.setBounds(6, 15, 176, 47);
		panel_top.add(lblHomebreaks);
		
		JLabel lblladdProperty = new JLabel("Add Property");
		lblladdProperty.setHorizontalAlignment(SwingConstants.CENTER);
		lblladdProperty.setForeground(Color.WHITE);
		lblladdProperty.setFont(new Font("Dialog", Font.BOLD, 20));
		lblladdProperty.setBounds(424, 38, 276, 47);
		panel_top.add(lblladdProperty);
		
		JPanel panel_details = new JPanel();
		panel_details.setBackground(new Color(204, 102, 102));
		panel_details.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(153, 0, 0), null, null, null));
		panel_details.setBounds(19, 105, 325, 478);
		panel_main.add(panel_details);
		panel_details.setLayout(null);
		
		JLabel lblMaximumGuests = new JLabel("Maximum Guests:*");
		lblMaximumGuests.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaximumGuests.setForeground(Color.WHITE);
		lblMaximumGuests.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMaximumGuests.setBounds(39, 312, 155, 32);
		panel_details.add(lblMaximumGuests);
		
		txtmaxGuests = new JTextField();
		txtmaxGuests.setToolTipText("Enter Maximum Guests");
		txtmaxGuests.setLocale(Locale.UK);
		txtmaxGuests.setIgnoreRepaint(true);
		txtmaxGuests.setHorizontalAlignment(SwingConstants.LEFT);
		txtmaxGuests.setFont(new Font("Dialog", Font.PLAIN, 15));
		txtmaxGuests.setColumns(10);
		txtmaxGuests.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtmaxGuests.setBounds(215, 315, 56, 27);
		panel_details.add(txtmaxGuests);
		
		JCheckBox chckbxbreakfastAvailability = new JCheckBox("Breakfast Availability*");
		chckbxbreakfastAvailability.setForeground(new Color(165, 42, 42));
		chckbxbreakfastAvailability.setFont(new Font("Dialog", Font.BOLD, 15));
		chckbxbreakfastAvailability.setBounds(75, 269, 192, 23);
		panel_details.add(chckbxbreakfastAvailability);
		
		JLabel lblAreaName = new JLabel("Area Name:*");
		lblAreaName.setForeground(Color.WHITE);
		lblAreaName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblAreaName.setBorder(null);
		lblAreaName.setBounds(29, 210, 86, 16);
		panel_details.add(lblAreaName);
		
		txtAreaName = new JTextField();
		txtAreaName.setToolTipText("Enter Area Name");
		txtAreaName.setLocale(Locale.UK);
		txtAreaName.setIgnoreRepaint(true);
		txtAreaName.setHorizontalAlignment(SwingConstants.LEFT);
		txtAreaName.setForeground(new Color(128, 0, 0));
		txtAreaName.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtAreaName.setColumns(10);
		txtAreaName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtAreaName.setBounds(117, 205, 187, 27);
		panel_details.add(txtAreaName);
		
		JLabel lblPostCode = new JLabel("Post Code:*");
		lblPostCode.setForeground(Color.WHITE);
		lblPostCode.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblPostCode.setBorder(null);
		lblPostCode.setBounds(30, 172, 86, 16);
		panel_details.add(lblPostCode);
		
		txtPostCode = new JTextField();
		txtPostCode.setToolTipText("Enter Post Code");
		txtPostCode.setLocale(Locale.UK);
		txtPostCode.setIgnoreRepaint(true);
		txtPostCode.setHorizontalAlignment(SwingConstants.LEFT);
		txtPostCode.setForeground(new Color(128, 0, 0));
		txtPostCode.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtPostCode.setColumns(10);
		txtPostCode.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtPostCode.setBounds(118, 167, 187, 27);
		panel_details.add(txtPostCode);
		
		JLabel lblDoorNumber = new JLabel("Door Number:*");
		lblDoorNumber.setForeground(Color.WHITE);
		lblDoorNumber.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblDoorNumber.setBorder(null);
		lblDoorNumber.setBounds(10, 139, 106, 16);
		panel_details.add(lblDoorNumber);
		
		txtDoorNumber = new JTextField();
		txtDoorNumber.setToolTipText("Enter Door Number");
		txtDoorNumber.setLocale(Locale.UK);
		txtDoorNumber.setIgnoreRepaint(true);
		txtDoorNumber.setHorizontalAlignment(SwingConstants.LEFT);
		txtDoorNumber.setForeground(new Color(128, 0, 0));
		txtDoorNumber.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtDoorNumber.setColumns(10);
		txtDoorNumber.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtDoorNumber.setBounds(118, 134, 187, 27);
		panel_details.add(txtDoorNumber);
		
		JLabel lblStreetName = new JLabel("Street Name:*");
		lblStreetName.setForeground(Color.WHITE);
		lblStreetName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblStreetName.setBorder(null);
		lblStreetName.setBounds(19, 106, 96, 16);
		panel_details.add(lblStreetName);
		
		txtStreetName = new JTextField();
		txtStreetName.setToolTipText("Enter Street Name");
		txtStreetName.setLocale(Locale.UK);
		txtStreetName.setIgnoreRepaint(true);
		txtStreetName.setHorizontalAlignment(SwingConstants.LEFT);
		txtStreetName.setForeground(new Color(128, 0, 0));
		txtStreetName.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtStreetName.setColumns(10);
		txtStreetName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtStreetName.setBounds(117, 101, 187, 27);
		panel_details.add(txtStreetName);
		
		JLabel lblpropertyName = new JLabel("Name:*");
		lblpropertyName.setForeground(Color.WHITE);
		lblpropertyName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblpropertyName.setBorder(null);
		lblpropertyName.setBounds(59, 74, 54, 16);
		panel_details.add(lblpropertyName);
		
		txtpropertyName = new JTextField();
		txtpropertyName.setToolTipText("Enter Property Name");
		txtpropertyName.setLocale(Locale.UK);
		txtpropertyName.setIgnoreRepaint(true);
		txtpropertyName.setHorizontalAlignment(SwingConstants.LEFT);
		txtpropertyName.setForeground(new Color(128, 0, 0));
		txtpropertyName.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtpropertyName.setColumns(10);
		txtpropertyName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtpropertyName.setBounds(117, 69, 187, 27);
		panel_details.add(txtpropertyName);
		
		JLabel lblDetails = new JLabel("Details");
		lblDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblDetails.setForeground(Color.WHITE);
		lblDetails.setFont(new Font("Dialog", Font.BOLD, 18));
		lblDetails.setBounds(89, 25, 150, 32);
		panel_details.add(lblDetails);
		
		JLabel lblMandatoryQuestion = new JLabel("* Mandatory Question");
		lblMandatoryQuestion.setForeground(Color.WHITE);
		lblMandatoryQuestion.setFont(new Font("Dialog", Font.PLAIN, 9));
		lblMandatoryQuestion.setBorder(null);
		lblMandatoryQuestion.setBounds(223, 11, 96, 16);
		panel_details.add(lblMandatoryQuestion);
    
		//defining layered pane for panel switching
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(385, 105, 638, 478);
		panel_main.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel_propertyName = new JPanel();
		panel_propertyName.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_propertyName.setBackground(new Color(205, 92, 92));
		layeredPane.add(panel_propertyName, "name_171126336308239");
		panel_propertyName.setLayout(null);
		
		JLabel lblPropertyOffers = new JLabel("What this Property Offers...");
		lblPropertyOffers.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropertyOffers.setForeground(Color.WHITE);
		lblPropertyOffers.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPropertyOffers.setBounds(174, 11, 290, 39);
		panel_propertyName.add(lblPropertyOffers);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(271, 51, 228, 54);
		panel_propertyName.add(scrollPane);
		
		JTextArea txtshortDescription = new JTextArea();
		txtshortDescription.setToolTipText("Enter Short Description");
		txtshortDescription.setText("Enter Here");
		txtshortDescription.setLineWrap(true);
		txtshortDescription.setForeground(new Color(128, 0, 0));
		txtshortDescription.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtshortDescription.setCaretColor(new Color(128, 0, 0));
		txtshortDescription.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		txtshortDescription.setBackground(new Color(255, 240, 245));
		scrollPane.setViewportView(txtshortDescription);
		
		JLabel lblShortDescription = new JLabel("Short Description:*");
		lblShortDescription.setVerticalAlignment(SwingConstants.TOP);
		lblShortDescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblShortDescription.setForeground(Color.WHITE);
		lblShortDescription.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblShortDescription.setBounds(147, 61, 120, 22);
		panel_propertyName.add(lblShortDescription);
		
		JScrollPane scrollPane_Property = new JScrollPane();
		scrollPane_Property.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		scrollPane_Property.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_Property.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Property.setBounds(22, 117, 606, 350);
		panel_propertyName.add(scrollPane_Property);
		
		JPanel panel_propertyOffers = new JPanel();
		scrollPane_Property.setViewportView(panel_propertyOffers);
		panel_propertyOffers.setLayout(null);
		panel_propertyOffers.setPreferredSize(new Dimension(500, 750));
		panel_propertyOffers.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(139, 0, 0)));
		panel_propertyOffers.setBackground(new Color(255, 228, 225));
		
		JLabel lblPropertyOffers_1 = new JLabel("What this Property Offers...");
		lblPropertyOffers_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropertyOffers_1.setForeground(new Color(128, 0, 0));
		lblPropertyOffers_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblPropertyOffers_1.setBounds(156, 0, 290, 39);
		panel_propertyOffers.add(lblPropertyOffers_1);
		
		JPanel panel_living = new JPanel();
		panel_living.setLayout(null);
		panel_living.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_living.setBackground(new Color(165, 42, 42));
		panel_living.setBounds(20, 33, 249, 151);
		panel_propertyOffers.add(panel_living);
		
		JLabel lblLivingFacility = new JLabel("Living Facility");
		lblLivingFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblLivingFacility.setForeground(new Color(245, 255, 250));
		lblLivingFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblLivingFacility.setBounds(33, 5, 186, 37);
		panel_living.add(lblLivingFacility);
		
		JCheckBox chckbxTelevision = new JCheckBox("Television");
		chckbxTelevision.setForeground(new Color(165, 42, 42));
		chckbxTelevision.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxTelevision.setBounds(139, 41, 104, 23);
		panel_living.add(chckbxTelevision);
		
		JCheckBox chckbxWifi = new JCheckBox("WiFi");
		chckbxWifi.setForeground(new Color(165, 42, 42));
		chckbxWifi.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxWifi.setBounds(16, 41, 63, 23);
		panel_living.add(chckbxWifi);
		
		JCheckBox chckbxStreaming = new JCheckBox("Streaming");
		chckbxStreaming.setForeground(new Color(165, 42, 42));
		chckbxStreaming.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxStreaming.setBounds(139, 76, 104, 23);
		panel_living.add(chckbxStreaming);
		
		JCheckBox chckbxSatellite = new JCheckBox("Satellite");
		chckbxSatellite.setForeground(new Color(165, 42, 42));
		chckbxSatellite.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxSatellite.setBounds(16, 76, 91, 23);
		panel_living.add(chckbxSatellite);
		
		JCheckBox chckbxBoardGames = new JCheckBox("Games");
		chckbxBoardGames.setForeground(new Color(165, 42, 42));
		chckbxBoardGames.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxBoardGames.setBounds(139, 111, 104, 23);
		panel_living.add(chckbxBoardGames);
		
		JCheckBox chckbxDvdPlayer = new JCheckBox("DVD Player");
		chckbxDvdPlayer.setForeground(new Color(165, 42, 42));
		chckbxDvdPlayer.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxDvdPlayer.setBounds(16, 111, 111, 23);
		panel_living.add(chckbxDvdPlayer);
		
		JPanel panel_utility = new JPanel();
		panel_utility.setLayout(null);
		panel_utility.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_utility.setBackground(new Color(165, 42, 42));
		panel_utility.setBounds(302, 32, 273, 151);
		panel_propertyOffers.add(panel_utility);
		
		JLabel lblUtilityFacility = new JLabel("Utility Facility");
		lblUtilityFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblUtilityFacility.setForeground(new Color(245, 255, 250));
		lblUtilityFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblUtilityFacility.setBounds(46, 0, 186, 41);
		panel_utility.add(lblUtilityFacility);
		
		JCheckBox chckbxWashingMachine = new JCheckBox("Washer");
		chckbxWashingMachine.setForeground(new Color(165, 42, 42));
		chckbxWashingMachine.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxWashingMachine.setBounds(146, 41, 104, 23);
		panel_utility.add(chckbxWashingMachine);
		
		JCheckBox chckbxCentralHeating = new JCheckBox("Heating");
		chckbxCentralHeating.setForeground(new Color(165, 42, 42));
		chckbxCentralHeating.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxCentralHeating.setBounds(23, 41, 91, 23);
		panel_utility.add(chckbxCentralHeating);
		
		JCheckBox chckbxDryingMachine = new JCheckBox("Dryer");
		chckbxDryingMachine.setForeground(new Color(165, 42, 42));
		chckbxDryingMachine.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxDryingMachine.setBounds(23, 76, 91, 23);
		panel_utility.add(chckbxDryingMachine);
		
		JCheckBox chckbxFireExtinguisher = new JCheckBox("Fire Safety");
		chckbxFireExtinguisher.setForeground(new Color(165, 42, 42));
		chckbxFireExtinguisher.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxFireExtinguisher.setBounds(146, 76, 121, 23);
		panel_utility.add(chckbxFireExtinguisher);
		
		JCheckBox chckbxSmokeAlarm = new JCheckBox("Smoke Alarm");
		chckbxSmokeAlarm.setForeground(new Color(165, 42, 42));
		chckbxSmokeAlarm.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxSmokeAlarm.setBounds(23, 111, 111, 23);
		panel_utility.add(chckbxSmokeAlarm);
		
		JCheckBox chckbxFirstAidKit = new JCheckBox("First Aid Kit");
		chckbxFirstAidKit.setForeground(new Color(165, 42, 42));
		chckbxFirstAidKit.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxFirstAidKit.setBounds(146, 111, 104, 23);
		panel_utility.add(chckbxFirstAidKit);
		
		JPanel panel_bathing = new JPanel();
		panel_bathing.setLayout(null);
		panel_bathing.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_bathing.setBackground(new Color(165, 42, 42));
		panel_bathing.setBounds(20, 196, 249, 162);
		panel_propertyOffers.add(panel_bathing);
		
		JLabel lblBathingFacility = new JLabel("Bathing Facility");
		lblBathingFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblBathingFacility.setForeground(new Color(245, 255, 250));
		lblBathingFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBathingFacility.setBounds(33, 0, 186, 47);
		panel_bathing.add(lblBathingFacility);
		
		JLabel lblnoOfBathrooms = new JLabel("No. of Bathrooms: ");
		lblnoOfBathrooms.setHorizontalAlignment(SwingConstants.CENTER);
		lblnoOfBathrooms.setForeground(new Color(245, 255, 250));
		lblnoOfBathrooms.setFont(new Font("Dialog", Font.BOLD, 14));
		lblnoOfBathrooms.setBounds(16, 115, 129, 23);
		panel_bathing.add(lblnoOfBathrooms);
		
		JCheckBox chckbxHairDryer = new JCheckBox("Hair Dryer");
		chckbxHairDryer.setForeground(new Color(165, 42, 42));
		chckbxHairDryer.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxHairDryer.setBounds(16, 39, 94, 23);
		panel_bathing.add(chckbxHairDryer);
		
		JCheckBox chckbxShampoo = new JCheckBox("Shampoo");
		chckbxShampoo.setForeground(new Color(165, 42, 42));
		chckbxShampoo.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxShampoo.setBounds(139, 39, 104, 23);
		panel_bathing.add(chckbxShampoo);
		
		JCheckBox chckbxToiletPaper = new JCheckBox("Toilet Paper");
		chckbxToiletPaper.setForeground(new Color(165, 42, 42));
		chckbxToiletPaper.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxToiletPaper.setBounds(75, 74, 104, 23);
		panel_bathing.add(chckbxToiletPaper);
		
		textBathrooms = new JTextField();
		textBathrooms.setToolTipText("Enter Bathrooms");
		textBathrooms.setLocale(Locale.UK);
		textBathrooms.setIgnoreRepaint(true);
		textBathrooms.setHorizontalAlignment(SwingConstants.LEFT);
		textBathrooms.setFont(new Font("Dialog", Font.PLAIN, 15));
		textBathrooms.setColumns(10);
		textBathrooms.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		textBathrooms.setBounds(157, 112, 56, 27);
		panel_bathing.add(textBathrooms);
		
		JPanel panel_kitchen = new JPanel();
		panel_kitchen.setLayout(null);
		panel_kitchen.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_kitchen.setBackground(new Color(165, 42, 42));
		panel_kitchen.setBounds(302, 196, 273, 162);
		panel_propertyOffers.add(panel_kitchen);
		
		JLabel lblKitchenFacility = new JLabel("Kitchen Facility");
		lblKitchenFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblKitchenFacility.setForeground(new Color(245, 255, 250));
		lblKitchenFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblKitchenFacility.setBounds(41, 0, 186, 36);
		panel_kitchen.add(lblKitchenFacility);
		
		JCheckBox chckbxFridge = new JCheckBox("Fridge");
		chckbxFridge.setForeground(new Color(165, 42, 42));
		chckbxFridge.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxFridge.setBounds(23, 30, 91, 23);
		panel_kitchen.add(chckbxFridge);
		
		JCheckBox chckbxMicrowave = new JCheckBox("Microwave");
		chckbxMicrowave.setForeground(new Color(165, 42, 42));
		chckbxMicrowave.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxMicrowave.setBounds(146, 30, 104, 23);
		panel_kitchen.add(chckbxMicrowave);
		
		JCheckBox chckbxOven = new JCheckBox("Oven");
		chckbxOven.setForeground(new Color(165, 42, 42));
		chckbxOven.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxOven.setBounds(23, 65, 91, 23);
		panel_kitchen.add(chckbxOven);
		
		JCheckBox chckbxDishwasher = new JCheckBox("Dishwasher");
		chckbxDishwasher.setForeground(new Color(165, 42, 42));
		chckbxDishwasher.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxDishwasher.setBounds(146, 65, 104, 23);
		panel_kitchen.add(chckbxDishwasher);
		
		JCheckBox chckbxCookware = new JCheckBox("Cookware");
		chckbxCookware.setForeground(new Color(165, 42, 42));
		chckbxCookware.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxCookware.setBounds(23, 100, 111, 23);
		panel_kitchen.add(chckbxCookware);
		
		JCheckBox chckbxBasicProvisions = new JCheckBox("Basic Provisions");
		chckbxBasicProvisions.setForeground(new Color(165, 42, 42));
		chckbxBasicProvisions.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxBasicProvisions.setBounds(67, 134, 121, 23);
		panel_kitchen.add(chckbxBasicProvisions);
		
		JCheckBox chckbxTableware = new JCheckBox("Tableware");
		chckbxTableware.setForeground(new Color(165, 42, 42));
		chckbxTableware.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxTableware.setBounds(146, 100, 104, 23);
		panel_kitchen.add(chckbxTableware);
		
		JPanel panel_sleeping = new JPanel();
		panel_sleeping.setLayout(null);
		panel_sleeping.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_sleeping.setBackground(new Color(165, 42, 42));
		panel_sleeping.setBounds(20, 370, 249, 162);
		panel_propertyOffers.add(panel_sleeping);
		
		JLabel lblSleepingFacility = new JLabel("Sleeping Facility");
		lblSleepingFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblSleepingFacility.setForeground(new Color(245, 255, 250));
		lblSleepingFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSleepingFacility.setBounds(33, 0, 186, 47);
		panel_sleeping.add(lblSleepingFacility);
		
		JLabel lblNoOfBedrooms = new JLabel("No. of Bedrooms: ");
		lblNoOfBedrooms.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoOfBedrooms.setForeground(new Color(245, 255, 250));
		lblNoOfBedrooms.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNoOfBedrooms.setBounds(22, 103, 125, 23);
		panel_sleeping.add(lblNoOfBedrooms);
		
		JCheckBox chckbxLinenBedsheet = new JCheckBox("Linen Bedsheet");
		chckbxLinenBedsheet.setForeground(new Color(165, 42, 42));
		chckbxLinenBedsheet.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxLinenBedsheet.setBounds(7, 50, 123, 23);
		panel_sleeping.add(chckbxLinenBedsheet);
		
		JCheckBox chckbxTowels = new JCheckBox("Towels");
		chckbxTowels.setForeground(new Color(165, 42, 42));
		chckbxTowels.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxTowels.setBounds(150, 50, 90, 23);
		panel_sleeping.add(chckbxTowels);
		
		txtBedrooms = new JTextField();
		txtBedrooms.setToolTipText("Enter Bedrooms");
		txtBedrooms.setLocale(Locale.UK);
		txtBedrooms.setIgnoreRepaint(true);
		txtBedrooms.setHorizontalAlignment(SwingConstants.LEFT);
		txtBedrooms.setFont(new Font("Dialog", Font.PLAIN, 15));
		txtBedrooms.setColumns(10);
		txtBedrooms.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtBedrooms.setBounds(159, 100, 56, 27);
		panel_sleeping.add(txtBedrooms);
		
		JPanel panel_bathroom = new JPanel();
		panel_bathroom.setLayout(null);
		panel_bathroom.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_bathroom.setBackground(new Color(165, 42, 42));
		panel_bathroom.setBounds(302, 370, 273, 162);
		panel_propertyOffers.add(panel_bathroom);
		
		JCheckBox chckbxToilet = new JCheckBox("Toilet");
		chckbxToilet.setForeground(new Color(165, 42, 42));
		chckbxToilet.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxToilet.setBounds(23, 52, 91, 23);
		panel_bathroom.add(chckbxToilet);
		
		JCheckBox chckbxBath = new JCheckBox("Bath");
		chckbxBath.setForeground(new Color(165, 42, 42));
		chckbxBath.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxBath.setBounds(146, 52, 104, 23);
		panel_bathroom.add(chckbxBath);
		
		JCheckBox chckbxShower = new JCheckBox("Shower");
		chckbxShower.setForeground(new Color(165, 42, 42));
		chckbxShower.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxShower.setBounds(23, 87, 91, 23);
		panel_bathroom.add(chckbxShower);
		
		JCheckBox chckbxShared = new JCheckBox("Shared");
		chckbxShared.setForeground(new Color(165, 42, 42));
		chckbxShared.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxShared.setBounds(146, 87, 104, 23);
		panel_bathroom.add(chckbxShared);
		
		JButton btnAddBathroom = new JButton("Add");
		btnAddBathroom.setToolTipText("Click here to Add Bathroom");
		btnAddBathroom.setForeground(new Color(178, 34, 34));
		btnAddBathroom.setFont(new Font("Dialog", Font.BOLD, 16));
		btnAddBathroom.setBounds(75, 125, 109, 27);
		btnAddBathroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bathroomsToAdd.add(new Bathroom(chckbxToilet.isSelected(), chckbxBath.isSelected(), chckbxShower.isSelected(), chckbxShared.isSelected()));
				JOptionPane.showMessageDialog(null, "Added a bathroom");
			}
		});		
		panel_bathroom.add(btnAddBathroom);
		
		JLabel lblBathroom = new JLabel("Bathroom");
		lblBathroom.setHorizontalAlignment(SwingConstants.CENTER);
		lblBathroom.setForeground(new Color(245, 255, 250));
		lblBathroom.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBathroom.setBounds(32, 0, 186, 36);
		panel_bathroom.add(lblBathroom);
		
		JPanel panel_bedroom = new JPanel();
		panel_bedroom.setLayout(null);
		panel_bedroom.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_bedroom.setBackground(new Color(165, 42, 42));
		panel_bedroom.setBounds(20, 544, 249, 162);
		panel_propertyOffers.add(panel_bedroom);
		
		JLabel lblBedroom = new JLabel("Bedroom");
		lblBedroom.setHorizontalAlignment(SwingConstants.CENTER);
		lblBedroom.setForeground(new Color(245, 255, 250));
		lblBedroom.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBedroom.setBounds(33, 0, 186, 47);
		panel_bedroom.add(lblBedroom);
		
		JComboBox comboBox_bedOneType = new JComboBox();
		comboBox_bedOneType.setModel(new DefaultComboBoxModel(new String[] {"Single", "Double", "King Size", "Bunk"}));		
		comboBox_bedOneType.setForeground(Color.BLACK);
		comboBox_bedOneType.setFont(new Font("Dialog", Font.BOLD, 13));
		comboBox_bedOneType.setBounds(132, 53, 109, 27);
		panel_bedroom.add(comboBox_bedOneType);
		
		JComboBox comboBox_bedTwoType = new JComboBox();
		comboBox_bedTwoType.setModel(new DefaultComboBoxModel(new String[] {"Single", "Double", "King Size", "Bunk"}));		
		comboBox_bedTwoType.setForeground(Color.BLACK);
		comboBox_bedTwoType.setFont(new Font("Dialog", Font.BOLD, 13));
		comboBox_bedTwoType.setBounds(133, 92, 108, 27);
		panel_bedroom.add(comboBox_bedTwoType);
		
		JLabel lblbedTwoType = new JLabel("Bed 2 Type:");
		lblbedTwoType.setHorizontalAlignment(SwingConstants.CENTER);
		lblbedTwoType.setForeground(new Color(245, 255, 250));
		lblbedTwoType.setFont(new Font("Dialog", Font.BOLD, 14));
		lblbedTwoType.setBounds(22, 94, 109, 23);
		panel_bedroom.add(lblbedTwoType);
		
		JLabel lblbedOneType = new JLabel("Bed 1 Type:");
		lblbedOneType.setHorizontalAlignment(SwingConstants.CENTER);
		lblbedOneType.setForeground(new Color(245, 255, 250));
		lblbedOneType.setFont(new Font("Dialog", Font.BOLD, 14));
		lblbedOneType.setBounds(14, 55, 125, 23);
		panel_bedroom.add(lblbedOneType);
		
		JButton btnAddBedroom = new JButton("Add");
		btnAddBedroom.setToolTipText("Click here to Add Bedroom");
		btnAddBedroom.setForeground(new Color(178, 34, 34));
		btnAddBedroom.setFont(new Font("Dialog", Font.BOLD, 16));
		btnAddBedroom.setBounds(76, 129, 109, 27);
		panel_bedroom.add(btnAddBedroom);
		btnAddBedroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bedroomsToAdd.add(new Bedroom((String)comboBox_bedOneType.getSelectedItem(), (String)comboBox_bedTwoType.getSelectedItem()));
				JOptionPane.showMessageDialog(null, "Added a bedroom");
			}
		});		
		
		JPanel panel_outdoor = new JPanel();
		panel_outdoor.setLayout(null);
		panel_outdoor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_outdoor.setBackground(new Color(165, 42, 42));
		panel_outdoor.setBounds(302, 544, 273, 162);
		panel_propertyOffers.add(panel_outdoor);
		
		JLabel lblOutdoor = new JLabel("Outdoor");
		lblOutdoor.setHorizontalAlignment(SwingConstants.CENTER);
		lblOutdoor.setForeground(new Color(245, 255, 250));
		lblOutdoor.setFont(new Font("Dialog", Font.BOLD, 15));
		lblOutdoor.setBounds(39, 6, 186, 36);
		panel_outdoor.add(lblOutdoor);
		
		JCheckBox chckbxBarbeque = new JCheckBox("Barbeque");
		chckbxBarbeque.setForeground(new Color(165, 42, 42));
		chckbxBarbeque.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxBarbeque.setBounds(163, 54, 90, 23);
		panel_outdoor.add(chckbxBarbeque);
		
		JCheckBox chckbxPatio = new JCheckBox("Patio");
		chckbxPatio.setForeground(new Color(165, 42, 42));
		chckbxPatio.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxPatio.setBounds(29, 54, 79, 23);
		panel_outdoor.add(chckbxPatio);
		
		JCheckBox chckbxOnsitePark = new JCheckBox("On-site Park");
		chckbxOnsitePark.setForeground(new Color(165, 42, 42));
		chckbxOnsitePark.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxOnsitePark.setBounds(29, 88, 110, 23);
		panel_outdoor.add(chckbxOnsitePark);
		
		JCheckBox chckbxOnroadPark = new JCheckBox("On-road park");
		chckbxOnroadPark.setForeground(new Color(165, 42, 42));
		chckbxOnroadPark.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxOnroadPark.setBounds(163, 90, 104, 23);
		panel_outdoor.add(chckbxOnroadPark);
		
		JCheckBox chckbxPaidPark = new JCheckBox("Paid park");
		chckbxPaidPark.setForeground(new Color(165, 42, 42));
		chckbxPaidPark.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxPaidPark.setBounds(98, 126, 79, 23);
		panel_outdoor.add(chckbxPaidPark);
		
		JPanel panel_chargeBand = new JPanel();
		layeredPane.add(panel_chargeBand, "name_170703165265016");
		panel_chargeBand.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_chargeBand.setBackground(new Color(205, 92, 92));
		panel_chargeBand.setLayout(null);
		
		JButton btnaddChargeband = new JButton("Add Charge Band");
		btnaddChargeband.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnaddChargeband.setToolTipText("Click Here to Add Charge Band");
		btnaddChargeband.setFont(new Font("Dialog", Font.BOLD, 13));
		btnaddChargeband.setForeground(new Color(128, 0, 0));
		btnaddChargeband.setBounds(176, 367, 140, 37);
		panel_details.add(btnaddChargeband);
		
		JButton btnBackToDetails = new JButton("Back to Details");
		btnBackToDetails.setBounds(11, 367, 140, 37);
		panel_details.add(btnBackToDetails);
		btnBackToDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(panel_propertyName);
			}
		});
		btnBackToDetails.setToolTipText("Click Here to go Back");
		btnBackToDetails.setForeground(new Color(128, 0, 0));
		btnBackToDetails.setFont(new Font("Dialog", Font.BOLD, 13));
		btnBackToDetails.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnaddChargeband.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)	{
				switchPanels(panel_chargeBand);				
			}
		});
		
		JDateChooser chargeEndDate = new JDateChooser();
		chargeEndDate.setBounds(117, 247, 140, 26);
		panel_chargeBand.add(chargeEndDate);
		
		JLabel lblcheckEnd = new JLabel("Charge End");
		lblcheckEnd.setForeground(Color.WHITE);
		lblcheckEnd.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblcheckEnd.setBorder(null);
		lblcheckEnd.setBounds(39, 251, 74, 16);
		panel_chargeBand.add(lblcheckEnd);
		
		JLabel lblchargeStart = new JLabel("Charge Start");
		lblchargeStart.setForeground(Color.WHITE);
		lblchargeStart.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblchargeStart.setBorder(null);
		lblchargeStart.setBounds(37, 195, 80, 16);
		panel_chargeBand.add(lblchargeStart);
		
		JDateChooser chargeStartDate = new JDateChooser();
		chargeStartDate.setBounds(117, 191, 140, 26);
		panel_chargeBand.add(chargeStartDate);
		
		JLabel lblchargeBandDates = new JLabel("Charge Band Dates");
		lblchargeBandDates.setToolTipText("");
		lblchargeBandDates.setForeground(Color.WHITE);
		lblchargeBandDates.setFont(new Font("Dialog", Font.BOLD, 17));
		lblchargeBandDates.setBorder(null);
		lblchargeBandDates.setBounds(74, 119, 178, 29);
		panel_chargeBand.add(lblchargeBandDates);
		
		JLabel lblCharge_Band_header = new JLabel("Charge Band");
		lblCharge_Band_header.setHorizontalAlignment(SwingConstants.CENTER);
		lblCharge_Band_header.setForeground(Color.WHITE);
		lblCharge_Band_header.setFont(new Font("Dialog", Font.BOLD, 21));
		lblCharge_Band_header.setBounds(179, 27, 290, 39);
		panel_chargeBand.add(lblCharge_Band_header);
		
		JLabel lblPriceDetails = new JLabel("Price Details");
		lblPriceDetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblPriceDetails.setForeground(Color.WHITE);
		lblPriceDetails.setFont(new Font("Dialog", Font.BOLD, 17));
		lblPriceDetails.setBounds(374, 100, 229, 47);
		panel_chargeBand.add(lblPriceDetails);
		
		JLabel lblPricePerNight = new JLabel("Price per night:");
		lblPricePerNight.setHorizontalAlignment(SwingConstants.CENTER);
		lblPricePerNight.setForeground(Color.WHITE);
		lblPricePerNight.setFont(new Font("Dialog", Font.BOLD, 15));
		lblPricePerNight.setBounds(374, 171, 115, 35);
		panel_chargeBand.add(lblPricePerNight);
		
		txtpricePerNight = new JTextField();
		txtpricePerNight.setToolTipText("Enter Price per night");
		txtpricePerNight.setLocale(Locale.UK);
		txtpricePerNight.setIgnoreRepaint(true);
		txtpricePerNight.setHorizontalAlignment(SwingConstants.LEFT);
		txtpricePerNight.setForeground(new Color(128, 0, 0));
		txtpricePerNight.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtpricePerNight.setColumns(10);
		txtpricePerNight.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtpricePerNight.setBounds(493, 178, 99, 27);
		panel_chargeBand.add(txtpricePerNight);
		
		JLabel lblCleaningPrice = new JLabel("Cleaning Price:");
		lblCleaningPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblCleaningPrice.setForeground(Color.WHITE);
		lblCleaningPrice.setFont(new Font("Dialog", Font.BOLD, 15));
		lblCleaningPrice.setBounds(375, 220, 115, 33);
		panel_chargeBand.add(lblCleaningPrice);
		
		txtCleaningPrice = new JTextField();
		txtCleaningPrice.setToolTipText("Enter Cleaning Price");
		txtCleaningPrice.setLocale(Locale.UK);
		txtCleaningPrice.setIgnoreRepaint(true);
		txtCleaningPrice.setHorizontalAlignment(SwingConstants.LEFT);
		txtCleaningPrice.setForeground(new Color(128, 0, 0));
		txtCleaningPrice.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtCleaningPrice.setColumns(10);
		txtCleaningPrice.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtCleaningPrice.setBounds(493, 223, 99, 27);
		panel_chargeBand.add(txtCleaningPrice);
		
		JLabel lblServiceCharges = new JLabel("Service Charges:");
		lblServiceCharges.setHorizontalAlignment(SwingConstants.CENTER);
		lblServiceCharges.setForeground(Color.WHITE);
		lblServiceCharges.setFont(new Font("Dialog", Font.BOLD, 15));
		lblServiceCharges.setBounds(354, 273, 138, 33);
		panel_chargeBand.add(lblServiceCharges);
		
		txtserviceCharges = new JTextField();
		txtserviceCharges.setToolTipText("Enter Service Charges");
		txtserviceCharges.setLocale(Locale.UK);
		txtserviceCharges.setIgnoreRepaint(true);
		txtserviceCharges.setHorizontalAlignment(SwingConstants.LEFT);
		txtserviceCharges.setForeground(new Color(128, 0, 0));
		txtserviceCharges.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtserviceCharges.setColumns(10);
		txtserviceCharges.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(153, 0, 0)));
		txtserviceCharges.setBounds(493, 276, 99, 27);
		panel_chargeBand.add(txtserviceCharges);
		
		JButton btnConfirmChargeBand = new JButton("Confirm Charge Band");
		btnConfirmChargeBand.setToolTipText("Click here to Confirm Charge Band");
		btnConfirmChargeBand.setForeground(new Color(128, 0, 0));
		btnConfirmChargeBand.setFont(new Font("Dialog", Font.BOLD, 14));
		btnConfirmChargeBand.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnConfirmChargeBand.setBounds(237, 371, 160, 47);
		panel_chargeBand.add(btnConfirmChargeBand);
		btnConfirmChargeBand.addActionListener(new ActionListener() {
	        // Charge band validation
	        public void actionPerformed(ActionEvent e) {
	        	try {
	        		Double.parseDouble(txtpricePerNight.getText());
                    Double.parseDouble(txtserviceCharges.getText());
                    Double.parseDouble(txtCleaningPrice.getText());
	        	} catch(Exception ex) {
	        		JOptionPane.showMessageDialog(null, "Please check all inputs are correct.", "Error", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
	            if (chargeStartDate.getDate() != null && chargeEndDate.getDate() != null && 
	                    Double.parseDouble(txtpricePerNight.getText()) > 0 &&
	                    Double.parseDouble(txtserviceCharges.getText()) > 0 &&
	                    Double.parseDouble(txtCleaningPrice.getText()) > 0 &&
	            		chargeStartDate.getDate().compareTo(chargeEndDate.getDate()) < 0)
	            	{
	            	// Add chargeband to chargeBandsToAdd (All are added to DB after the main property is added)
	                chargeBandsToAdd.add(new ChargeBand(
	                        chargeStartDate.getDate(),
	                        chargeEndDate.getDate(),
	                        Double.parseDouble(txtpricePerNight.getText()),
	                        Double.parseDouble(txtserviceCharges.getText()), 
	                        Double.parseDouble(txtCleaningPrice.getText())));
	                JOptionPane.showMessageDialog(null, "Added a chargeband. Add the property to save it.");
	                switchPanels(panel_chargeBand);				
                } else {
	                JOptionPane.showMessageDialog(null, "Please check all inputs are correct!", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
		
		JButton btnaddProperty = new JButton("Add Property");
		btnaddProperty.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		btnaddProperty.setToolTipText("Click here to Add Property");
		btnaddProperty.setForeground(new Color(0, 128, 0));
		btnaddProperty.setFont(new Font("Dialog", Font.BOLD, 16));
		btnaddProperty.setBounds(81, 423, 160, 37);
		btnaddProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (chargeBandsToAdd.size() == 0) {
					JOptionPane.showMessageDialog(null, "Please add at least 1 chargeband", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					// Checks if a property with the entered name already exists
					// Checks required fields are filled in.
					boolean doorNum = false;
					try {
						Integer.valueOf(txtDoorNumber.getText());
						doorNum = true;
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Please check all inputs are correct", "Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
					if (sameNameExists(txtpropertyName.getText())) {
						JOptionPane.showMessageDialog(null, "Please choose a different property name.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if (txtshortDescription.getText().length() > 0 &&
							txtpropertyName.getText().length() > 0 &&
							txtAreaName.getText().length() > 0 &&
							doorNum &&
							txtStreetName.getText().length() > 0 &&
							txtPostCode.getText().length() > 0) { 
					
					// If the user has not clicked "add" for bedroom or bathroom, the selected items are added for them as a bed/bathroom.
					// Each property must have at least 1 bed and bathroom.
					if (bedroomsToAdd.size() == 0) {
						bedroomsToAdd.add(new Bedroom((String)comboBox_bedOneType.getSelectedItem(), (String)comboBox_bedTwoType.getSelectedItem()));
					}
					if (bathroomsToAdd.size() == 0) {
						bathroomsToAdd.add(new Bathroom(chckbxToilet.isSelected(), chckbxBath.isSelected(), chckbxShower.isSelected(), chckbxShared.isSelected()));
					}
			        propertyToAdd.hasBedrooms(bedroomsToAdd);
			        propertyToAdd.hasBathrooms(bathroomsToAdd);
			        
			        propertyToAdd.hasLivingFacility(chckbxWifi.isSelected(), chckbxTelevision.isSelected(), chckbxSatellite.isSelected(), chckbxStreaming.isSelected(), chckbxDvdPlayer.isSelected(), chckbxBoardGames.isSelected());
			        propertyToAdd.hasBathingFacility(chckbxHairDryer.isSelected(), chckbxShampoo.isSelected(), chckbxToiletPaper.isSelected());
			        propertyToAdd.hasKitchenFacility(chckbxFridge.isSelected(), chckbxMicrowave.isSelected(), chckbxOven.isSelected(), chckbxDishwasher.isSelected(), chckbxCookware.isSelected(), chckbxTableware.isSelected(), chckbxBasicProvisions.isSelected());
			        propertyToAdd.hasUtilityFacility(chckbxCentralHeating.isSelected(), chckbxWashingMachine.isSelected(), chckbxDryingMachine.isSelected(), chckbxFireExtinguisher.isSelected(), chckbxSmokeAlarm.isSelected(), chckbxFirstAidKit.isSelected());
			        propertyToAdd.hasSleepingFacility(chckbxLinenBedsheet.isSelected(), chckbxTowels.isSelected());
			        propertyToAdd.hasOutdoorFacility(chckbxOnsitePark.isSelected(), chckbxOnroadPark.isSelected(), chckbxPaidPark.isSelected(), chckbxPatio.isSelected(), chckbxBarbeque.isSelected());
				    
			        // Get information about the host from the session email
					//SQL initialization
					int hostID = getHostID(session);
			        
			        propertyToAdd.hostInfo("null", "null", hostID);
			        propertyToAdd.houseInfo(chckbxbreakfastAvailability.isSelected(), txtpropertyName.getText(), txtshortDescription.getText(), txtAreaName.getText(), Integer.valueOf(txtDoorNumber.getText()), txtStreetName.getText(), txtPostCode.getText());
					
			        new PropertyManager().addProperty(propertyToAdd);
					
			        // Get newly added property ID
			        int newPropID = 0;
					//SQL initialization
					Connection con = null;
					Connection con2 = null;
			        PreparedStatement pstmt = null;
			        PreparedStatement pstmt2 = null;
			        ResultSet rs = null;
			        
			        String sqlQuery = "SELECT propertyID FROM Property WHERE shortName = ?";
					
					try {					
						//establish connection
						con = SQL_Interface.getConnection();
						
						pstmt = con.prepareStatement(sqlQuery);
						pstmt.setString(1, txtpropertyName.getText());
						rs = pstmt.executeQuery();
						
						while (rs.next()) {
							newPropID = rs.getInt("propertyID");
						}
					}
					catch(Exception ex) {
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
			        
			        // Insert into ChargeBand DB
			        for (int i = 0; i < chargeBandsToAdd.size(); i++) {
			        	if (!overlaps(newPropID, new java.sql.Date(chargeBandsToAdd.get(i).startDate.getTime()),
			        			new java.sql.Date(chargeBandsToAdd.get(i).endDate.getTime()))) {
							try {				
								//establish connection					
								String sql_cb = "INSERT INTO ChargeBands (propertyID, startDate, endDate, "
										+ "pricePerNight, serviceCharge, cleaningCharge) "
										+ "VALUES (?,?,?,?,?,?)";
								
								con2 = SQL_Interface.getConnection();
								con2.setAutoCommit(false);
								pstmt2 = con2.prepareStatement(sql_cb);
								pstmt2.setInt(1, newPropID);
								pstmt2.setDate(2, new java.sql.Date(chargeBandsToAdd.get(i).startDate.getTime()));
								pstmt2.setDate(3, new java.sql.Date(chargeBandsToAdd.get(i).endDate.getTime()));
	
								pstmt2.setDouble(4, chargeBandsToAdd.get(i).pricePerNight);
								pstmt2.setDouble(5, chargeBandsToAdd.get(i).serviceCharge);
								pstmt2.setDouble(6, chargeBandsToAdd.get(i).cleaningCharge);
	
								int affectedRows_cb = pstmt2.executeUpdate();
								if(affectedRows_cb == 1) {
									// success entering property
									con2.commit();
								} else {	
									con2.rollback(); 
									JOptionPane.showMessageDialog(null, "Failure making a charge band", "Error", JOptionPane.ERROR_MESSAGE);
								}
							}
							catch (Exception ex) {
								ex.printStackTrace();	
							}
							finally {
								try {
					                if(pstmt2 != null) 	pstmt2.close();
					                if(con2 != null) 	con2.close();
								} catch (SQLException ex) {
									ex.printStackTrace();
								}
							}
						}
		        	}
			        session.startSession();
			        dispose();
		        }
				else {
					JOptionPane.showMessageDialog(null, "Please fill out all required fields properly", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}}
		});		
		panel_details.add(btnaddProperty);
	}
	// Get host ID from email in session.
	public int getHostID(Session session) {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int hostID = 0;
        
        String sqlQuery = "SELECT hostID FROM Host WHERE email = ?";
		
		try {					
			//establish connection
			con = SQL_Interface.getConnection();
			
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setString(1, session.getEmail());
			rs = pstmt.executeQuery();
			
			if (!rs.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, "Invalid session.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			while (rs.next()) {
				hostID = rs.getInt("hostID");
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
		return hostID;
	}
	// Check if a property already exists with the desired shortName (they must be unique)
	private boolean sameNameExists(String name) {
		boolean exists = false;
		//SQL initialization
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sqlQuery = "SELECT shortName FROM Property WHERE shortName = ?";
		
		try {					
			//establish connection
			con = SQL_Interface.getConnection();
			
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				exists = true;
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
		return exists;
	}
	// Checks if a period over start-end date overlaps with another chargeband
	public boolean overlaps(int propertyID, java.sql.Date startDate, java.sql.Date endDate) {
		boolean overlaps = false;
		//SQL initialization
		Connection con = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sqlQueryStartDate = "SELECT propertyID FROM ChargeBands "
				+ "WHERE ((startDate <= ? AND ? <= endDate ) OR  (startDate <= ? AND ? <= endDate )"
				+ "OR  ( ? <= startDate AND startDate <= ? ) OR ( ? <= endDate AND endDate <= ? )) "
				+ "AND propertyID = ? ";
        try {
        	con = SQL_Interface.getConnection();
        	con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlQueryStartDate);
			pstmt.setDate(1, (startDate));
			pstmt.setDate(2, (startDate));
			pstmt.setDate(3, (endDate));
			pstmt.setDate(4, (endDate));
			pstmt.setDate(5, (startDate));
			pstmt.setDate(6, (endDate));
			pstmt.setDate(7, (startDate));
			pstmt.setDate(8, (endDate));
			pstmt.setInt(9, propertyID);
			rs = pstmt.executeQuery();	
		
			//establish connection
			while (rs.next()) {
				overlaps = true;
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
		return overlaps;
	}
}
