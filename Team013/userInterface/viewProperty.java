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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.MatteBorder;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.toedter.calendar.JDateChooser;

import backend.Host;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class viewProperty extends JFrame {

	private JPanel contentPane;
	private JLayeredPane layeredPane;
	public int propertyID;
	private JTable ReviewTable;
	private DefaultTableModel resultModel;
	public Session session;	
	
	public void switchPanels (JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.validate();
	}
	
	/**
	 * Create the frame.
	 */

	public viewProperty(Session session, int propertyID, java.util.Date startDate, java.util.Date endDate) {
		this.propertyID = propertyID;
		this.session = session;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Property propData = (new PropertyManager().getProperty(propertyID));
    
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
		
		JPanel panel_Property = new JPanel();
		panel_Property.setBackground(new Color(205, 92, 92));
		panel_Property.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(153, 0, 0), null, null, null));
		panel_Property.setBounds(19, 105, 325, 478);
		panel.add(panel_Property);
		panel_Property.setLayout(null);
		
		JLabel lblpropertyInfoHeading = new JLabel("Property Information");
		lblpropertyInfoHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblpropertyInfoHeading.setForeground(Color.WHITE);
		lblpropertyInfoHeading.setFont(new Font("Dialog", Font.BOLD, 25));
		lblpropertyInfoHeading.setBounds(25, 28, 276, 47);
		panel_Property.add(lblpropertyInfoHeading);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(385, 105, 638, 478);
		panel.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel_propertyName = new JPanel();
		layeredPane.add(panel_propertyName, "name_170703165265016");
		panel_propertyName.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_propertyName.setBackground(new Color(205, 92, 92));
		panel_propertyName.setLayout(null);
		
		JLabel lblPropertyName = new JLabel(propData.shortName);
		lblPropertyName.setBounds(14, 16, 181, 47);
		lblPropertyName.setHorizontalAlignment(SwingConstants.LEFT);
		lblPropertyName.setForeground(Color.WHITE);
		lblPropertyName.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_propertyName.add(lblPropertyName);
		
		JScrollPane scrollPane_Property = new JScrollPane();
		scrollPane_Property.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Property.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_Property.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));

		scrollPane_Property.setBounds(14, 105, 614, 372);

		panel_propertyName.add(scrollPane_Property);
		
		JPanel panel_propertyOffers = new JPanel();
		scrollPane_Property.setViewportView(panel_propertyOffers);
		panel_propertyOffers.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(139, 0, 0)));
		panel_propertyOffers.setBackground(new Color(255, 228, 225));
		panel_propertyOffers.setLayout(null);
		panel_propertyOffers.setPreferredSize(new Dimension(500,750));
		
		JPanel panel_living = new JPanel();
		panel_living.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_living.setBackground(new Color(205, 92, 92));
		panel_living.setBounds(20, 33, 249, 151);
		panel_propertyOffers.add(panel_living);
		panel_living.setLayout(null);
		
		JLabel lblLivingFacility = new JLabel("Living Facility");
		lblLivingFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblLivingFacility.setForeground(new Color(245, 255, 250));
		lblLivingFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblLivingFacility.setBounds(33, 0, 186, 37);
		panel_living.add(lblLivingFacility);
		
		JCheckBox chckbxWifi = new JCheckBox("WiFi");
		chckbxWifi.setEnabled(false);
		chckbxWifi.setForeground(new Color(165, 42, 42));
		chckbxWifi.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxWifi.setBounds(11, 42, 63, 23);
		panel_living.add(chckbxWifi);
		
		JCheckBox chckbxTelevision = new JCheckBox("Television");
		chckbxTelevision.setEnabled(false);
		chckbxTelevision.setForeground(new Color(165, 42, 42));
		chckbxTelevision.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxTelevision.setBounds(134, 42, 104, 23);
		panel_living.add(chckbxTelevision);
		
		JCheckBox chckbxSatellite = new JCheckBox("Satellite");
		chckbxSatellite.setEnabled(false);
		chckbxSatellite.setForeground(new Color(165, 42, 42));
		chckbxSatellite.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxSatellite.setBounds(11, 77, 91, 23);
		panel_living.add(chckbxSatellite);
		
		JCheckBox chckbxStreaming = new JCheckBox("Streaming");
		chckbxStreaming.setEnabled(false);
		chckbxStreaming.setForeground(new Color(165, 42, 42));
		chckbxStreaming.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxStreaming.setBounds(134, 77, 104, 23);
		panel_living.add(chckbxStreaming);
		
		JCheckBox chckbxDvdPlayer = new JCheckBox("DVD Player");
		chckbxDvdPlayer.setEnabled(false);
		chckbxDvdPlayer.setForeground(new Color(165, 42, 42));
		chckbxDvdPlayer.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxDvdPlayer.setBounds(11, 112, 111, 23);
		panel_living.add(chckbxDvdPlayer);
		
		JCheckBox chckbxBoardGames = new JCheckBox("Games");
		chckbxBoardGames.setEnabled(false);
		chckbxBoardGames.setForeground(new Color(165, 42, 42));
		chckbxBoardGames.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxBoardGames.setBounds(134, 112, 104, 23);
		panel_living.add(chckbxBoardGames);
		
		JPanel panel_utility = new JPanel();
		panel_utility.setLayout(null);
		panel_utility.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_utility.setBackground(new Color(205, 92, 92));
		panel_utility.setBounds(302, 32, 273, 151);
		panel_propertyOffers.add(panel_utility);
		
		JLabel lblUtilityFacility = new JLabel("Utility Facility");
		lblUtilityFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblUtilityFacility.setForeground(new Color(245, 255, 250));
		lblUtilityFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblUtilityFacility.setBounds(46, 0, 186, 41);
		panel_utility.add(lblUtilityFacility);
		
		JCheckBox chckbxSmokeAlarm = new JCheckBox("Smoke Alarm");
		chckbxSmokeAlarm.setEnabled(false);
		chckbxSmokeAlarm.setForeground(new Color(165, 42, 42));
		chckbxSmokeAlarm.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxSmokeAlarm.setBounds(15, 113, 111, 23);
		panel_utility.add(chckbxSmokeAlarm);
		
		JCheckBox chckbxFirstAidKit = new JCheckBox("First Aid Kit");
		chckbxFirstAidKit.setEnabled(false);
		chckbxFirstAidKit.setForeground(new Color(165, 42, 42));
		chckbxFirstAidKit.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxFirstAidKit.setToolTipText("");
		chckbxFirstAidKit.setBounds(138, 113, 104, 23);
		panel_utility.add(chckbxFirstAidKit);
		
		JCheckBox chckbxDryingmachine = new JCheckBox("Dryer");
		chckbxDryingmachine.setEnabled(false);
		chckbxDryingmachine.setForeground(new Color(165, 42, 42));
		chckbxDryingmachine.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxDryingmachine.setBounds(15, 78, 91, 23);
		panel_utility.add(chckbxDryingmachine);
		
		JCheckBox chckbxFireExtinguisher = new JCheckBox("Fire Safety");
		chckbxFireExtinguisher.setEnabled(false);
		chckbxFireExtinguisher.setForeground(new Color(165, 42, 42));
		chckbxFireExtinguisher.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxFireExtinguisher.setBounds(138, 78, 121, 23);
		panel_utility.add(chckbxFireExtinguisher);
		
		JCheckBox chckbxCentralHeating = new JCheckBox("Heating");
		chckbxCentralHeating.setEnabled(false);
		chckbxCentralHeating.setForeground(new Color(165, 42, 42));
		chckbxCentralHeating.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxCentralHeating.setBounds(15, 43, 91, 23);
		panel_utility.add(chckbxCentralHeating);
		
		JCheckBox chckbxWashingMachine = new JCheckBox("Washer");
		chckbxWashingMachine.setEnabled(false);
		chckbxWashingMachine.setForeground(new Color(165, 42, 42));
		chckbxWashingMachine.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxWashingMachine.setBounds(138, 43, 104, 23);
		panel_utility.add(chckbxWashingMachine);
		
		JPanel panel_bathing = new JPanel();
		panel_bathing.setLayout(null);
		panel_bathing.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_bathing.setBackground(new Color(205, 92, 92));
		panel_bathing.setBounds(20, 196, 249, 162);
		panel_propertyOffers.add(panel_bathing);
		
		JLabel lblBathingFacility = new JLabel("Bathing Facility");
		lblBathingFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblBathingFacility.setForeground(new Color(245, 255, 250));
		lblBathingFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBathingFacility.setBounds(33, 0, 186, 47);
		panel_bathing.add(lblBathingFacility);
		
		JLabel lblnoBathrooms = new JLabel(String.valueOf(propData.noOfBathrooms));
		lblnoBathrooms.setHorizontalAlignment(SwingConstants.CENTER);
		lblnoBathrooms.setForeground(new Color(245, 255, 250));
		lblnoBathrooms.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblnoBathrooms.setBounds(134, 115, 94, 23);
		panel_bathing.add(lblnoBathrooms);
		
		JLabel lblnoOfBathrooms = new JLabel("No. of Bathrooms: ");
		lblnoOfBathrooms.setHorizontalAlignment(SwingConstants.CENTER);
		lblnoOfBathrooms.setForeground(new Color(245, 255, 250));
		lblnoOfBathrooms.setFont(new Font("Dialog", Font.BOLD, 13));
		lblnoOfBathrooms.setBounds(20, 115, 125, 23);
		panel_bathing.add(lblnoOfBathrooms);
		
		JCheckBox chckbxShampoo = new JCheckBox("Shampoo");
		chckbxShampoo.setEnabled(false);
		chckbxShampoo.setForeground(new Color(165, 42, 42));
		chckbxShampoo.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxShampoo.setBounds(133, 45, 104, 23);
		panel_bathing.add(chckbxShampoo);
		
		JCheckBox chckbxHairDryer = new JCheckBox("Hair Dryer");
		chckbxHairDryer.setEnabled(false);
		chckbxHairDryer.setForeground(new Color(165, 42, 42));
		chckbxHairDryer.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxHairDryer.setBounds(10, 45, 94, 23);
		panel_bathing.add(chckbxHairDryer);
		
		JCheckBox chckbxToiletPaper = new JCheckBox("Toilet Paper");
		chckbxToiletPaper.setEnabled(false);
		chckbxToiletPaper.setForeground(new Color(165, 42, 42));
		chckbxToiletPaper.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxToiletPaper.setBounds(69, 80, 104, 23);
		panel_bathing.add(chckbxToiletPaper);
		
		JPanel panel_kitchen = new JPanel();
		panel_kitchen.setLayout(null);
		panel_kitchen.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_kitchen.setBackground(new Color(205, 92, 92));
		panel_kitchen.setBounds(302, 196, 273, 162);
		panel_propertyOffers.add(panel_kitchen);
		
		JLabel lblKitchenFacility = new JLabel("Kitchen Facility");
		lblKitchenFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblKitchenFacility.setForeground(new Color(245, 255, 250));
		lblKitchenFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblKitchenFacility.setBounds(41, 0, 186, 36);
		panel_kitchen.add(lblKitchenFacility);
		
		JCheckBox chckbxCookware = new JCheckBox("Cookware");
		chckbxCookware.setEnabled(false);
		chckbxCookware.setForeground(new Color(165, 42, 42));
		chckbxCookware.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxCookware.setBounds(16, 102, 91, 23);
		panel_kitchen.add(chckbxCookware);
		
		JCheckBox chckbxBasicProvisions = new JCheckBox("Basic Provisions");
		chckbxBasicProvisions.setEnabled(false);
		chckbxBasicProvisions.setForeground(new Color(165, 42, 42));
		chckbxBasicProvisions.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxBasicProvisions.setBounds(64, 134, 121, 23);
		panel_kitchen.add(chckbxBasicProvisions);
		
		JCheckBox chckbxOven = new JCheckBox("Oven");
		chckbxOven.setEnabled(false);
		chckbxOven.setForeground(new Color(165, 42, 42));
		chckbxOven.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxOven.setBounds(16, 68, 91, 23);
		panel_kitchen.add(chckbxOven);
		
		JCheckBox chckbxDishwasher = new JCheckBox("Dishwasher");
		chckbxDishwasher.setEnabled(false);
		chckbxDishwasher.setForeground(new Color(165, 42, 42));
		chckbxDishwasher.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxDishwasher.setBounds(139, 68, 104, 23);
		panel_kitchen.add(chckbxDishwasher);
		
		JCheckBox chckbxFridge = new JCheckBox("Fridge");
		chckbxFridge.setEnabled(false);
		chckbxFridge.setForeground(new Color(165, 42, 42));
		chckbxFridge.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxFridge.setBounds(16, 33, 91, 23);
		panel_kitchen.add(chckbxFridge);
		
		JCheckBox chckbxMicrowave = new JCheckBox("Microwave");
		chckbxMicrowave.setEnabled(false);
		chckbxMicrowave.setForeground(new Color(165, 42, 42));
		chckbxMicrowave.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxMicrowave.setBounds(139, 33, 104, 23);
		panel_kitchen.add(chckbxMicrowave);
		
		JCheckBox chckbxTableware = new JCheckBox("Tableware");
		chckbxTableware.setForeground(new Color(165, 42, 42));
		chckbxTableware.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxTableware.setEnabled(false);
		chckbxTableware.setBounds(138, 103, 106, 23);
		panel_kitchen.add(chckbxTableware);
		
		JPanel panel_sleeping = new JPanel();
		panel_sleeping.setLayout(null);
		panel_sleeping.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_sleeping.setBackground(new Color(205, 92, 92));
		panel_sleeping.setBounds(20, 370, 249, 162);
		panel_propertyOffers.add(panel_sleeping);
		
		JLabel lblSleepingFacility = new JLabel("Sleeping Facility");
		lblSleepingFacility.setHorizontalAlignment(SwingConstants.CENTER);
		lblSleepingFacility.setForeground(new Color(245, 255, 250));
		lblSleepingFacility.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSleepingFacility.setBounds(33, 0, 186, 47);
		panel_sleeping.add(lblSleepingFacility);
		
		JLabel lblnoRooms = new JLabel(String.valueOf(propData.noOfBedrooms));
		lblnoRooms.setHorizontalAlignment(SwingConstants.CENTER);
		lblnoRooms.setForeground(new Color(245, 255, 250));
		lblnoRooms.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblnoRooms.setBounds(135, 101, 94, 23);
		panel_sleeping.add(lblnoRooms);
		
		JLabel lblNoOfRooms = new JLabel("No. of Bedrooms:");
		lblNoOfRooms.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoOfRooms.setForeground(new Color(245, 255, 250));
		lblNoOfRooms.setFont(new Font("Dialog", Font.BOLD, 13));
		lblNoOfRooms.setBounds(16, 101, 125, 23);
		panel_sleeping.add(lblNoOfRooms);
		
		JCheckBox chckbxLinenBedsheet = new JCheckBox("Linen Bedsheet");
		chckbxLinenBedsheet.setEnabled(false);
		chckbxLinenBedsheet.setForeground(new Color(165, 42, 42));
		chckbxLinenBedsheet.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxLinenBedsheet.setBounds(11, 52, 123, 23);
		panel_sleeping.add(chckbxLinenBedsheet);
		
		JCheckBox chckbxTowels = new JCheckBox("Towels");
		chckbxTowels.setEnabled(false);
		chckbxTowels.setForeground(new Color(165, 42, 42));
		chckbxTowels.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxTowels.setBounds(154, 52, 90, 23);
		panel_sleeping.add(chckbxTowels);
		
		JPanel panel_bathroom = new JPanel();
		panel_bathroom.setLayout(null);
		panel_bathroom.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_bathroom.setBackground(new Color(205, 92, 92));
		panel_bathroom.setBounds(302, 370, 273, 162);
		panel_propertyOffers.add(panel_bathroom);
		
		JLabel lblBathroom = new JLabel("Bathroom");
		lblBathroom.setHorizontalAlignment(SwingConstants.CENTER);
		lblBathroom.setForeground(new Color(245, 255, 250));
		lblBathroom.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBathroom.setBounds(41, 0, 186, 36);
		panel_bathroom.add(lblBathroom);
			
		JCheckBox chckbxToilet = new JCheckBox("Toilet");
		chckbxToilet.setEnabled(false);
		chckbxToilet.setForeground(new Color(165, 42, 42));
		chckbxToilet.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxToilet.setBounds(26, 46, 91, 23);
		panel_bathroom.add(chckbxToilet);
		
		JCheckBox chckbxBath = new JCheckBox("Bath");
		chckbxBath.setEnabled(false);
		chckbxBath.setForeground(new Color(165, 42, 42));
		chckbxBath.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxBath.setBounds(149, 46, 104, 23);
		panel_bathroom.add(chckbxBath);
		
		JCheckBox chckbxShower = new JCheckBox("Shower");
		chckbxShower.setEnabled(false);
		chckbxShower.setForeground(new Color(165, 42, 42));
		chckbxShower.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxShower.setBounds(26, 81, 91, 23);
		panel_bathroom.add(chckbxShower);
		
		JCheckBox chckbxShared = new JCheckBox("Shared");
		chckbxShared.setEnabled(false);
		chckbxShared.setForeground(new Color(165, 42, 42));
		chckbxShared.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxShared.setBounds(149, 81, 104, 23);
		panel_bathroom.add(chckbxShared);
		
		JPanel panel_bedroom = new JPanel();
		panel_bedroom.setLayout(null);
		panel_bedroom.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_bedroom.setBackground(new Color(205, 92, 92));
		panel_bedroom.setBounds(20, 544, 249, 162);
		panel_propertyOffers.add(panel_bedroom);
		JLabel lblBedroom = new JLabel("Bedroom");
		lblBedroom.setHorizontalAlignment(SwingConstants.CENTER);
		lblBedroom.setForeground(new Color(245, 255, 250));
		lblBedroom.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBedroom.setBounds(33, 0, 186, 47);
		panel_bedroom.add(lblBedroom);
		
		JLabel lblbedTwoType = new JLabel("Bed 2 Type:");
		lblbedTwoType.setHorizontalAlignment(SwingConstants.CENTER);
		lblbedTwoType.setForeground(new Color(245, 255, 250));
		lblbedTwoType.setFont(new Font("Dialog", Font.BOLD, 13));
		lblbedTwoType.setBounds(24, 67, 109, 23);
		panel_bedroom.add(lblbedTwoType);
		
		JLabel lblbedOneType = new JLabel("Bed 1 Type:");
		lblbedOneType.setHorizontalAlignment(SwingConstants.CENTER);
		lblbedOneType.setForeground(new Color(245, 255, 250));
		lblbedOneType.setFont(new Font("Dialog", Font.BOLD, 13));
		lblbedOneType.setBounds(16, 42, 125, 23);
		panel_bedroom.add(lblbedOneType);
		
		JLabel lblSleepingCapacity = new JLabel("Sleeping Capacity");
		lblSleepingCapacity.setHorizontalAlignment(SwingConstants.CENTER);
		lblSleepingCapacity.setForeground(new Color(245, 255, 250));
		lblSleepingCapacity.setFont(new Font("Dialog", Font.BOLD, 13));
		lblSleepingCapacity.setBounds(24, 114, 125, 23);
		panel_bedroom.add(lblSleepingCapacity);
		
		JLabel lblcapacitySleeping = new JLabel(String.valueOf(propData.maxGuests()));
		lblcapacitySleeping.setHorizontalAlignment(SwingConstants.CENTER);
		lblcapacitySleeping.setForeground(new Color(245, 255, 250));
		lblcapacitySleeping.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblcapacitySleeping.setBounds(160, 114, 69, 23);
		panel_bedroom.add(lblcapacitySleeping);
		
		JLabel lblbedOne = new JLabel(propData.bedrooms.get(0).bed1);
		lblbedOne.setHorizontalAlignment(SwingConstants.CENTER);
		lblbedOne.setForeground(new Color(245, 255, 250));
		lblbedOne.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblbedOne.setBounds(140, 41, 76, 23);
		panel_bedroom.add(lblbedOne);
		
		JLabel lblbedTwo = new JLabel(propData.bedrooms.get(0).bed2);
		lblbedTwo.setHorizontalAlignment(SwingConstants.CENTER);
		lblbedTwo.setForeground(new Color(245, 255, 250));
		lblbedTwo.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblbedTwo.setBounds(148, 66, 68, 23);
		panel_bedroom.add(lblbedTwo);
		
		JPanel panel_outdoor = new JPanel();
		panel_outdoor.setLayout(null);
		panel_outdoor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		panel_outdoor.setBackground(new Color(205, 92, 92));
		panel_outdoor.setBounds(302, 544, 273, 162);
		panel_propertyOffers.add(panel_outdoor);
		
		JLabel lblBedroom_1 = new JLabel("Outdoor");
		lblBedroom_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBedroom_1.setForeground(new Color(245, 255, 250));
		lblBedroom_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblBedroom_1.setBounds(41, 0, 186, 36);
		panel_outdoor.add(lblBedroom_1);
		
		JCheckBox chckbxPatio = new JCheckBox("Patio");
		chckbxPatio.setEnabled(false);
		chckbxPatio.setForeground(new Color(165, 42, 42));
		chckbxPatio.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxPatio.setBounds(28, 44, 79, 23);
		panel_outdoor.add(chckbxPatio);
		
		JCheckBox chckbxBarbeque = new JCheckBox("Barbeque");
		chckbxBarbeque.setEnabled(false);
		chckbxBarbeque.setForeground(new Color(165, 42, 42));
		chckbxBarbeque.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxBarbeque.setBounds(162, 44, 90, 23);
		panel_outdoor.add(chckbxBarbeque);
		
		JCheckBox chckbxOnsitePark = new JCheckBox("On-site park");
		chckbxOnsitePark.setForeground(new Color(165, 42, 42));
		chckbxOnsitePark.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxOnsitePark.setEnabled(false);
		chckbxOnsitePark.setBounds(28, 82, 102, 23);
		panel_outdoor.add(chckbxOnsitePark);
		
		JCheckBox chckbxOnroadPark = new JCheckBox("On-road park");
		chckbxOnroadPark.setForeground(new Color(165, 42, 42));
		chckbxOnroadPark.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxOnroadPark.setEnabled(false);
		chckbxOnroadPark.setBounds(148, 82, 105, 23);
		panel_outdoor.add(chckbxOnroadPark);
		
		JCheckBox chckbxPaidParking = new JCheckBox("Paid parking");
		chckbxPaidParking.setForeground(new Color(165, 42, 42));
		chckbxPaidParking.setFont(new Font("Dialog", Font.BOLD, 12));
		chckbxPaidParking.setEnabled(false);
		chckbxPaidParking.setBounds(85, 116, 111, 23);
		panel_outdoor.add(chckbxPaidParking);
		
		JLabel lblPropertyOffers = new JLabel("What this Property Offers...");
		lblPropertyOffers.setBounds(153, 0, 290, 31);
		panel_propertyOffers.add(lblPropertyOffers);
		lblPropertyOffers.setHorizontalAlignment(SwingConstants.CENTER);
		lblPropertyOffers.setForeground(new Color(128, 0, 0));
		lblPropertyOffers.setFont(new Font("Dialog", Font.BOLD, 16));
		
		// Sets checkboxes enabled if the property has a certain feature.
		// Checkboxes are diabled so the user cannot click them and tick/untick
		if (propData.smokeAlarm) {
			chckbxSmokeAlarm.setSelected(true);
		}
		chckbxSmokeAlarm.setEnabled(false);
		 
		if (propData.firstAidKit) {
			chckbxFirstAidKit.setSelected(true);
		}
		chckbxFirstAidKit.setEnabled(false);
		 
		if (propData.dryingMachine) {
			chckbxDryingmachine.setSelected(true);
		}
		chckbxDryingmachine.setEnabled(false);
		
		if (propData.tableware) {
			chckbxTableware.setSelected(true);
		}
		chckbxTableware.setEnabled(false);

		if (propData.fireExtinguisher) {
			chckbxFireExtinguisher.setSelected(true);
		}
		chckbxFireExtinguisher.setEnabled(false);
		
		if (propData.onsitePark) {
			chckbxOnsitePark.setSelected(true);
		}
		chckbxOnsitePark.setEnabled(false);
		
		if (propData.onroadPark) {
			chckbxOnroadPark.setSelected(true);
		}
		chckbxOnroadPark.setEnabled(false);
		
		if (propData.paidPark) {
			chckbxPaidParking.setSelected(true);
		}
		chckbxPaidParking.setEnabled(false);
		
		if (propData.centralHeating) {
			chckbxCentralHeating.setSelected(true);
		}
		chckbxCentralHeating.setEnabled(false);
		
		if (propData.washingMachine) {
			chckbxWashingMachine.setSelected(true);
		}
		chckbxWashingMachine.setEnabled(false);
		
		if (propData.shampoo) {
			chckbxShampoo.setSelected(true);
		}
		chckbxShampoo.setEnabled(false);
		 
		if (propData.hairDryer) {
			chckbxHairDryer.setSelected(true);
		}
		chckbxHairDryer.setEnabled(false);
		 
		if (propData.toiletPaper) {
			chckbxToiletPaper.setSelected(true);
		}
		chckbxToiletPaper.setEnabled(false);
		
		if (propData.cookware) {
			chckbxCookware.setSelected(true);
		}
		chckbxCookware.setEnabled(false);
		 
		if (propData.basicProvisions) {
			chckbxBasicProvisions.setSelected(true);
		}
		chckbxBasicProvisions.setEnabled(false);
		  
		if (propData.oven) {
			chckbxOven.setSelected(true);
		}
		chckbxOven.setEnabled(false);
		
		if (propData.dishwasher) {
			chckbxDishwasher.setSelected(true);
		}
		chckbxDishwasher.setEnabled(false);
		 
		if (propData.fridge) {
			chckbxFridge.setSelected(true);
		}
		chckbxFridge.setEnabled(false);
		 
		if (propData.microwave) {
			chckbxMicrowave.setSelected(true);
		}
		chckbxMicrowave.setEnabled(false);
		 
		if (propData.bedLinen) {
			chckbxLinenBedsheet.setSelected(true);
		}
		chckbxLinenBedsheet.setEnabled(false);
		    
		if (propData.towels) {
			chckbxTowels.setSelected(true);
		}
		chckbxTowels.setEnabled(false);
		
		//
		if (propData.bathrooms.get(0).toilet) {
			chckbxToilet.setSelected(true);
		}
		chckbxToilet.setEnabled(false);
		
		if (propData.bathrooms.get(0).shared) {
			chckbxShared.setSelected(true);
		}
		chckbxShared.setEnabled(false);
		
		if (propData.bathrooms.get(0).bath) {
			chckbxBath.setSelected(true);
		}
		chckbxBath.setEnabled(false);
		
		if (propData.bathrooms.get(0).shower) {
			chckbxShower.setSelected(true);
		}
		chckbxShower.setEnabled(false);
		
		if (propData.barbeque) {
			chckbxBarbeque.setSelected(true);		
		}
		chckbxBarbeque.setEnabled(false);
		
		if (propData.patio) {
			chckbxPatio.setSelected(true);		
		}
		chckbxPatio.setEnabled(false);
		
		if (propData.satellite) {
			chckbxSatellite.setSelected(true);
		}
		chckbxSatellite.setEnabled(false);
		
		if (propData.streaming) {
			chckbxStreaming.setSelected(true);
		}
		chckbxStreaming.setEnabled(false);
		
		if (propData.television) {
			chckbxTelevision.setSelected(true);
		}
		chckbxTelevision.setEnabled(false);
		
		if (propData.wifi) {
			chckbxWifi.setSelected(true);
		}
		chckbxWifi.setEnabled(false);
		
		if (propData.dvdPlayer) {
			chckbxDvdPlayer.setSelected(true);
		}
		chckbxDvdPlayer.setEnabled(false);
		
		if (propData.boardGames) {
			chckbxBoardGames.setSelected(true);
		}
		chckbxBoardGames.setEnabled(false);
		
		
		JLabel lblhostName = new JLabel(propData.forename + " " + propData.surname);
		lblhostName.setHorizontalAlignment(SwingConstants.LEFT);
		lblhostName.setForeground(Color.WHITE);
		lblhostName.setFont(new Font("Dialog", Font.BOLD, 20));
		lblhostName.setBounds(223, 16, 180, 47);
		panel_propertyName.add(lblhostName);
		
		JLabel lblPropertyNameby = new JLabel("by");
		lblPropertyNameby.setHorizontalAlignment(SwingConstants.LEFT);
		lblPropertyNameby.setForeground(Color.WHITE);
		lblPropertyNameby.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPropertyNameby.setBounds(190, 16, 43, 47);
		panel_propertyName.add(lblPropertyNameby);
		
		JLabel lblMaximumGuests = new JLabel("Maximum Guests:");
		lblMaximumGuests.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaximumGuests.setForeground(Color.WHITE);
		lblMaximumGuests.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMaximumGuests.setBounds(439, 72, 126, 32);
		panel_propertyName.add(lblMaximumGuests);
		
		JLabel lblMaximumGuestsNo = new JLabel(String.valueOf(propData.maxGuests()));
		lblMaximumGuestsNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaximumGuestsNo.setForeground(Color.WHITE);
		lblMaximumGuestsNo.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMaximumGuestsNo.setBounds(566, 72, 54, 32);
		panel_propertyName.add(lblMaximumGuestsNo);
		
		JLabel lblShortdescription = new JLabel(propData.description());
		lblShortdescription.setVerticalAlignment(SwingConstants.TOP);
		lblShortdescription.setHorizontalAlignment(SwingConstants.LEFT);
		lblShortdescription.setForeground(Color.WHITE);
		lblShortdescription.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblShortdescription.setBounds(24, 58, 312, 46);
		panel_propertyName.add(lblShortdescription);
		
		JCheckBox chckbxBreakfastAvailability = new JCheckBox("Breakfast Availability");
		chckbxBreakfastAvailability.setEnabled(false);
		chckbxBreakfastAvailability.setForeground(new Color(165, 42, 42));
		chckbxBreakfastAvailability.setFont(new Font("Dialog", Font.BOLD, 14));
		if (propData.breakfastAvailability) {
			chckbxBreakfastAvailability.setSelected(true);
		} else {
			chckbxBreakfastAvailability.setSelected(false);
		}    
		chckbxBreakfastAvailability.setBounds(432, 52, 173, 23);
		panel_propertyName.add(chckbxBreakfastAvailability);
		
		JCheckBox chckbxSuperhost = new JCheckBox("Superhost");
		chckbxSuperhost.setForeground(new Color(165, 42, 42));
		chckbxSuperhost.setFont(new Font("Dialog", Font.BOLD, 14));
		chckbxSuperhost.setEnabled(false);
		if (Host.isOwnerSuperhost(this.propertyID)) {
			chckbxSuperhost.setSelected(true);
		} else {
			chckbxSuperhost.setSelected(false);
		}	
		chckbxSuperhost.setBounds(432, 16, 99, 23);
		panel_propertyName.add(chckbxSuperhost);
		
		JPanel panel_csReviews = new JPanel();
		panel_csReviews.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_csReviews.setBackground(new Color(205, 92, 92));
		layeredPane.add(panel_csReviews, "name_171126336308239");
		panel_csReviews.setLayout(null);
		
		JLabel lblheadingviewReviews = new JLabel("Customer Satisfaction Reviews");
		lblheadingviewReviews.setBounds(99, 4, 461, 47);
		lblheadingviewReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblheadingviewReviews.setForeground(Color.WHITE);
		lblheadingviewReviews.setFont(new Font("Dialog", Font.BOLD, 20));
		panel_csReviews.add(lblheadingviewReviews);
		
		JButton btnWriteReview = new JButton("Write a Review");
		if(session.getUserType() != 'g') {btnWriteReview.setEnabled(false);}
		btnWriteReview.setBounds(244, 424, 131, 38);
		btnWriteReview.setFont(new Font("Dialog", Font.BOLD, 13));
		btnWriteReview.setForeground(new Color(139, 0, 0));
		btnWriteReview.setVisible(false);
		boolean bookingIDExists = false;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sqlQuery = "SELECT acceptedByHost FROM Booking "
							+ "INNER JOIN Guest ON Guest.guestID = Booking.guestID "
							+ "WHERE Guest.email = ? AND Booking.propertyID = ?";
		
		try {
			con = SQL_Interface.getConnection();
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setString(1, session.getEmail());
			pstmt.setInt(2, propertyID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				bookingIDExists = true;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if(rs != null) {rs.close();}
				if(pstmt != null) {pstmt.close();}
				if(con != null) {con.close();}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		if(bookingIDExists) {
			btnWriteReview.setVisible(true);
		}
		btnWriteReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new writeGuestReviews(session, propertyID).setVisible(true);
				}
				
		});
		
		btnWriteReview.setToolTipText("Click here to write a Review");
		panel_csReviews.add(btnWriteReview);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 91, 588, 317);
		panel_csReviews.add(scrollPane);
		
		String[] colName = new String[] {"Comment","Cleanliness","Communication", "Checkin", "Accuracy","Location","Value"};
		resultModel = new DefaultTableModel(colName,0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};	
		
		ReviewTable = new JTable(resultModel);
		ReviewTable.setEnabled(false);
		scrollPane.setViewportView(ReviewTable);
		ReviewTable.setRowSelectionAllowed(false);
		ReviewTable.setRowHeight(35);
		ReviewTable.setGridColor(new Color(128, 0, 0));
		ReviewTable.setForeground(new Color(128, 0, 0));
		ReviewTable.setFont(new Font("Dialog", Font.PLAIN, 12));
		ReviewTable.setPreferredSize(new Dimension(500,750));		
		ReviewTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );	
		 for(int x=0;x<7;x++){
			 ReviewTable.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
			 ReviewTable.getColumnModel().getColumn(x).setResizable(false);   
			 ReviewTable.getColumnModel().getColumn(x).setPreferredWidth(50);
	     }		
		ReviewTable.getColumnModel().getColumn(0).setPreferredWidth(600);
		ReviewTable.setBounds(28, 98, 585, 270);		
		ReviewTable.setBackground(new Color(255, 204, 204));
		
		JLabel lblAverageRatings = new JLabel("Average Ratings:");
		lblAverageRatings.setToolTipText("");
		lblAverageRatings.setForeground(Color.WHITE);
		lblAverageRatings.setFont(new Font("Dialog", Font.BOLD, 17));
		lblAverageRatings.setBorder(null);
		lblAverageRatings.setBounds(28, 51, 150, 29);
		panel_csReviews.add(lblAverageRatings);
		
		float overallRating = generateResultsForTable(resultModel);
		
		JLabel lblAverageRatingsdisplay = new JLabel("");
		lblAverageRatingsdisplay.setToolTipText("Show average ratings");
		lblAverageRatingsdisplay.setForeground(Color.WHITE);
		lblAverageRatingsdisplay.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAverageRatingsdisplay.setBorder(null);
		lblAverageRatingsdisplay.setBounds(176, 51, 103, 29);
		panel_csReviews.add(lblAverageRatingsdisplay);
		String s_overallRating = String.format("%.2f", overallRating);
		lblAverageRatingsdisplay.setText(s_overallRating);
		
		JButton btnbookingView = new JButton("View");
		btnbookingView.setForeground(new Color(128, 0, 0));
		btnbookingView.setFont(new Font("Dialog", Font.BOLD, 13));
		btnbookingView.setToolTipText("Click here to View/Edit Info");
		btnbookingView.setBounds(183, 116, 90, 29);
		panel_Property.add(btnbookingView);
		btnbookingView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)	{
				switchPanels(panel_propertyName);
			}
		});
		
		JLabel lblViewEditInfo = new JLabel("1. Details");
		lblViewEditInfo.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		lblViewEditInfo.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 17));
		lblViewEditInfo.setToolTipText("View");
		lblViewEditInfo.setForeground(Color.WHITE);
		lblViewEditInfo.setBorder(null);
		lblViewEditInfo.setBounds(52, 114, 150, 29);
		panel_Property.add(lblViewEditInfo);
		
		JLabel lblyourProperties = new JLabel("2. Reviews");
		lblyourProperties.setToolTipText("Your Properties");
		lblyourProperties.setForeground(Color.WHITE);
		lblyourProperties.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 17));
		lblyourProperties.setBorder(null);
		lblyourProperties.setBounds(49, 186, 111, 29);
		panel_Property.add(lblyourProperties);
		
		JButton btnyourProperties = new JButton("View");
		btnyourProperties.setForeground(new Color(128, 0, 0));
		btnyourProperties.setFont(new Font("Dialog", Font.BOLD, 13));
		btnyourProperties.setToolTipText("Click here to View Reviews");
		btnyourProperties.setBounds(183, 188, 90, 29);
		panel_Property.add(btnyourProperties);
		btnyourProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)	{
				switchPanels(panel_csReviews);
			}
		});
		
		JDateChooser checkInDateChooser = new JDateChooser();
		checkInDateChooser.setBounds(114, 299, 140, 26);
		panel_Property.add(checkInDateChooser);
		
		JLabel lblcheckIn_adjust = new JLabel("Check In");
		lblcheckIn_adjust.setFont(new Font("Dialog", Font.BOLD, 13));
		lblcheckIn_adjust.setForeground(Color.WHITE);
		lblcheckIn_adjust.setBorder(null);
		lblcheckIn_adjust.setBounds(50, 303, 64, 16);
		panel_Property.add(lblcheckIn_adjust);
		
		JLabel lblcheckOut_adjust = new JLabel("Check Out");
		lblcheckOut_adjust.setFont(new Font("Dialog", Font.BOLD, 13));
		lblcheckOut_adjust.setForeground(Color.WHITE);
		lblcheckOut_adjust.setBorder(null);
		lblcheckOut_adjust.setBounds(44, 348, 74, 16);
		panel_Property.add(lblcheckOut_adjust);
		
		JDateChooser checkOutDateChooser = new JDateChooser();
		checkOutDateChooser.setBounds(116, 344, 138, 26);
		panel_Property.add(checkOutDateChooser);
		if (session.getUserType() != 'h') {
			checkOutDateChooser.setDate(endDate);
			checkInDateChooser.setDate(startDate);
		}
		
		
		JButton btnmakeBooking = new JButton("Reserve");
		if(session.getUserType() != 'g') {btnmakeBooking.setEnabled(false);}
		btnmakeBooking.setForeground(new Color(128, 0, 0));
		btnmakeBooking.setFont(new Font("Dialog", Font.BOLD, 13));
		btnmakeBooking.setToolTipText("Click here to Reserve your Property");
		btnmakeBooking.setBounds(91, 407, 138, 38);
		panel_Property.add(btnmakeBooking);
		btnmakeBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat mySqlDate = new SimpleDateFormat("yyyy-MM-dd");
				String checkInDate = mySqlDate.format(checkInDateChooser.getDate());
				String checkOutDate = mySqlDate.format(checkOutDateChooser.getDate());
				LocalDate inDate = LocalDate.parse(checkInDate);
				LocalDate outDate = LocalDate.parse(checkOutDate);
				if (InputValidation.checkInDateValid(inDate, outDate) && InputValidation.dateCheckNotPast(inDate, outDate)) {
					try {
						new makeBookingPage(session, inDate, outDate, propertyID, String.valueOf(propData.maxGuests())).setVisible(true);
						dispose();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Date", "Error", JOptionPane.ERROR_MESSAGE);
				};
			}
		});
	
		JLabel lblAdjustDates = new JLabel("Check Availability");
		lblAdjustDates.setToolTipText("Adjust Dates");
		lblAdjustDates.setForeground(Color.WHITE);
		lblAdjustDates.setFont(new Font("Dialog", Font.BOLD, 17));
		lblAdjustDates.setBorder(null);
		lblAdjustDates.setBounds(91, 248, 150, 29);
		panel_Property.add(lblAdjustDates);
		
		JPanel panel_top = new JPanel();
		panel_top.setLayout(null);
		panel_top.setBackground(new Color(204, 51, 51));
		panel_top.setBounds(0, 0, 1045, 84);
		panel.add(panel_top);
		
		JLabel lblHomebreaks = new JLabel("HomeBreaks");
		lblHomebreaks.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomebreaks.setForeground(Color.WHITE);
		lblHomebreaks.setFont(new Font("Dialog", Font.BOLD, 28));
		lblHomebreaks.setBounds(11, 23, 176, 47);
		panel_top.add(lblHomebreaks);
		
		JButton btnSearch = new JButton("Back");
		btnSearch.setForeground(new Color(128, 0, 0));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSearch.setToolTipText("Click to go back to Search");
		btnSearch.setFont(new Font("Dialog", Font.BOLD, 14));
		btnSearch.setBounds(925, 33, 94, 36);
		panel_top.add(btnSearch);
		
		JLabel lblplaceSttayHeading = new JLabel("Your place to stay");
		lblplaceSttayHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblplaceSttayHeading.setForeground(Color.WHITE);
		lblplaceSttayHeading.setFont(new Font("Dialog", Font.BOLD, 20));
		lblplaceSttayHeading.setBounds(419, 33, 188, 47);
		panel_top.add(lblplaceSttayHeading);

	}
	
	public float generateResultsForTable(DefaultTableModel resultModel) {
		
		ResultSet rs=null;
		PreparedStatement pstmt =null;
		Connection con = null;
		float sum = 0;
		int rowCount = 0;
		float overallRating = 0;
		
		try {
			
			con = SQL_Interface.getConnection();
			String sqlQuery = "SELECT * FROM Review WHERE propertyID=?";		
			pstmt = con.prepareStatement(sqlQuery);	
			pstmt.setInt(1, this.propertyID);			
			rs = pstmt.executeQuery();				
			String comment="";
			int cleanlinessScore;
			int commsScore;
			int checkinScore;
			int accuracyScore;
			int locationScore;
			int valueScore;		
			while (rs.next()) {
				rowCount++;
				comment = rs.getString("comment");
				cleanlinessScore = rs.getInt("cleanlinessScore");	
				commsScore = rs.getInt("commsScore");	
				checkinScore = rs.getInt("checkinScore");	
				accuracyScore = rs.getInt("accuracyScore");	
				locationScore = rs.getInt("locationScore");		
				valueScore = rs.getInt("valueScore");						
				this.resultModel.addRow(new Object[] {comment,cleanlinessScore,commsScore,checkinScore,accuracyScore,locationScore,valueScore});
				
				sum += cleanlinessScore + commsScore + checkinScore + accuracyScore + locationScore + valueScore;
			}
				overallRating = sum/(6*rowCount);
			
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return overallRating;
	}
}

