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

import java.util.ArrayList;
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

public class writeGuestReviews extends JFrame {

	private JPanel contentPane;
	public Session session;
	public int bookingID;
	
	/**
	 * Create the frame.
	 * @param propertyID 
	 * @param session 
	 */
	public writeGuestReviews(Session session, int bookingID) {
		this.session = session;
		this.bookingID = bookingID;
		
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
		panel.setBounds(0, 0, 1041, 601);
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
		
		JLabel lblheadingReviews = new JLabel("Customer Satisfaction Reviews");
		lblheadingReviews.setHorizontalAlignment(SwingConstants.CENTER);
		lblheadingReviews.setForeground(Color.WHITE);
		lblheadingReviews.setFont(new Font("Dialog", Font.BOLD, 20));
		lblheadingReviews.setBounds(314, 6, 461, 47);
		panel_top.add(lblheadingReviews);
		
		JPanel panel_reviews = new JPanel();
		panel_reviews.setBounds(516, 78, 480, 487);
		panel.add(panel_reviews);
		panel_reviews.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_reviews.setBackground(new Color(205, 92, 92));
		panel_reviews.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 105, 397, 187);
		panel_reviews.add(scrollPane);
		
		JTextArea txtenterReviews = new JTextArea();
		txtenterReviews.setForeground(new Color(128, 0, 0));
		txtenterReviews.setBackground(new Color(255, 240, 245));
		txtenterReviews.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		txtenterReviews.setText("Please Enter your Reviews here...");
		txtenterReviews.setCaretColor(new Color(128, 0, 0));
		txtenterReviews.setLineWrap(true);
		txtenterReviews.setFont(new Font("Dialog", Font.PLAIN, 15));
		txtenterReviews.setToolTipText("Enter your Reviews / Feedback");
		scrollPane.setViewportView(txtenterReviews);
		
		JLabel lblReviewsFeedback = new JLabel("Reviews / Feedback");
		lblReviewsFeedback.setHorizontalAlignment(SwingConstants.CENTER);
		lblReviewsFeedback.setForeground(Color.WHITE);
		lblReviewsFeedback.setFont(new Font("Dialog", Font.BOLD, 20));
		lblReviewsFeedback.setBounds(119, 19, 236, 47);
		panel_reviews.add(lblReviewsFeedback);
		
		
		JLabel lblmax255 = new JLabel("Keep it crisp! Max 100 characters.");
		lblmax255.setForeground(new Color(255, 255, 255));
		lblmax255.setBounds(265, 294, 164, 16);
		panel_reviews.add(lblmax255);
		
		JPanel panel_ratings = new JPanel();
		panel_ratings.setLayout(null);
		panel_ratings.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(204, 51, 51), null, null, null));
		panel_ratings.setBackground(new Color(205, 92, 92));
		panel_ratings.setBounds(26, 78, 443, 487);
		panel.add(panel_ratings);
		
		JLabel lblReviewRatings = new JLabel("Ratings");
		lblReviewRatings.setHorizontalAlignment(SwingConstants.CENTER);
		lblReviewRatings.setForeground(Color.WHITE);
		lblReviewRatings.setFont(new Font("Dialog", Font.BOLD, 20));
		lblReviewRatings.setBounds(114, 19, 236, 47);
		panel_ratings.add(lblReviewRatings);
		
		JSlider slider_cleanliness = new JSlider();
		slider_cleanliness.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		slider_cleanliness.setValue(1);		
		slider_cleanliness.setMinimum(1);
		slider_cleanliness.setMaximum(5);
		slider_cleanliness.setSnapToTicks(true);
		slider_cleanliness.setOpaque(true);
		slider_cleanliness.setMinorTickSpacing(1);
		slider_cleanliness.setMajorTickSpacing(1);
		slider_cleanliness.setPaintTicks(true);
		slider_cleanliness.setPaintLabels(true);
		slider_cleanliness.setBounds(195, 82, 190, 44);
		panel_ratings.add(slider_cleanliness);
		
		JLabel lblCleanliness = new JLabel("Cleanliness:");
		lblCleanliness.setToolTipText("");
		lblCleanliness.setForeground(new Color(255, 255, 255));
		lblCleanliness.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblCleanliness.setBorder(null);
		lblCleanliness.setBounds(88, 91, 105, 29);
		panel_ratings.add(lblCleanliness);
		
		JLabel lblCommunications = new JLabel("Communications:");
		lblCommunications.setToolTipText("");
		lblCommunications.setForeground(Color.WHITE);
		lblCommunications.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblCommunications.setBorder(null);
		lblCommunications.setBounds(47, 148, 137, 29);
		panel_ratings.add(lblCommunications);
		
		JSlider slider_communications = new JSlider();
		slider_communications.setSnapToTicks(true);
		slider_communications.setPaintTicks(true);
		slider_communications.setPaintLabels(true);
		slider_communications.setOpaque(true);
		slider_communications.setMinorTickSpacing(1);
		slider_communications.setValue(1);			
		slider_communications.setMinimum(1);
		slider_communications.setMaximum(5);
		slider_communications.setMajorTickSpacing(1);
		slider_communications.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		slider_communications.setBounds(193, 140, 190, 44);
		panel_ratings.add(slider_communications);
		
		JLabel lblCheckin = new JLabel("Check-In:");
		lblCheckin.setToolTipText("");
		lblCheckin.setForeground(Color.WHITE);
		lblCheckin.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblCheckin.setBorder(null);
		lblCheckin.setBounds(102, 207, 89, 29);
		panel_ratings.add(lblCheckin);
		
		JSlider slider_checkin = new JSlider();
		slider_checkin.setSnapToTicks(true);
		slider_checkin.setPaintTicks(true);
		slider_checkin.setPaintLabels(true);
		slider_checkin.setOpaque(true);
		slider_checkin.setMinorTickSpacing(1);
		slider_checkin.setValue(1);			
		slider_checkin.setMinimum(1);
		slider_checkin.setMaximum(5);
		slider_checkin.setMajorTickSpacing(1);
		slider_checkin.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		slider_checkin.setBounds(193, 198, 190, 44);
		panel_ratings.add(slider_checkin);
		
		JLabel lblAccuracy = new JLabel("Accuracy:");
		lblAccuracy.setToolTipText("");
		lblAccuracy.setForeground(Color.WHITE);
		lblAccuracy.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblAccuracy.setBorder(null);
		lblAccuracy.setBounds(96, 267, 105, 29);
		panel_ratings.add(lblAccuracy);
		
		JSlider slider_accuracy = new JSlider();
		slider_accuracy.setSnapToTicks(true);
		slider_accuracy.setPaintTicks(true);
		slider_accuracy.setPaintLabels(true);
		slider_accuracy.setOpaque(true);
		slider_accuracy.setMinorTickSpacing(1);
		slider_accuracy.setValue(1);				
		slider_accuracy.setMinimum(1);
		slider_accuracy.setMaximum(5);
		slider_accuracy.setMajorTickSpacing(1);
		slider_accuracy.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		slider_accuracy.setBounds(193, 258, 190, 44);
		panel_ratings.add(slider_accuracy);
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setToolTipText("");
		lblLocation.setForeground(Color.WHITE);
		lblLocation.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblLocation.setBorder(null);
		lblLocation.setBounds(102, 325, 82, 29);
		panel_ratings.add(lblLocation);
		
		JSlider slider_location = new JSlider();
		slider_location.setSnapToTicks(true);
		slider_location.setPaintTicks(true);
		slider_location.setPaintLabels(true);
		slider_location.setOpaque(true);
		slider_location.setMinorTickSpacing(1);
		slider_location.setValue(1);				
		slider_location.setMinimum(1);
		slider_location.setMaximum(5);
		slider_location.setMajorTickSpacing(1);
		slider_location.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		slider_location.setBounds(193, 317, 190, 44);
		panel_ratings.add(slider_location);
		
		JLabel lblValue = new JLabel("Value:");
		lblValue.setToolTipText("");
		lblValue.setForeground(Color.WHITE);
		lblValue.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblValue.setBorder(null);
		lblValue.setBounds(121, 382, 70, 29);
		panel_ratings.add(lblValue);
		
		JSlider slider_value = new JSlider();
		slider_value.setSnapToTicks(true);
		slider_value.setPaintTicks(true);
		slider_value.setPaintLabels(true);
		slider_value.setOpaque(true);
		slider_value.setMinorTickSpacing(1);
		slider_value.setValue(1);				
		slider_value.setMinimum(1);
		slider_value.setMaximum(5);
		slider_value.setMajorTickSpacing(1);
		slider_value.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		slider_value.setBounds(193, 373, 190, 44);
		panel_ratings.add(slider_value);
		
		JButton btnSubmitReview = new JButton("Submit Review");
		btnSubmitReview.setForeground(new Color(128, 0, 0));
		btnSubmitReview.setFont(new Font("Dialog", Font.BOLD, 13));
		// Make new review of a property.
		btnSubmitReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				PreparedStatement pstmtR = null;
				
				String sql_newProp = "INSERT INTO Review (propertyID, bookingID, guestID, "
						+ "comment, cleanlinessScore, commsScore, checkinScore, accuracyScore, locationScore, valueScore) "
						+ "VALUES (?,?,?,"
						+ "?,?,?,?,?,?,?)";
				// Review can't be made if they have already made a review for their booking
				if (!reviewExists(bookingID)) {
					if(txtenterReviews.getText().length() <= 100) {
						try {					
							//establish connection
							int[] bookingInfo = getBookingInfo(bookingID);
							
							con = SQL_Interface.getConnection();
							con.setAutoCommit(false);
							pstmtR = con.prepareStatement(sql_newProp);
							pstmtR.setInt(1, bookingInfo[0]);
							pstmtR.setInt(2, bookingID);
							pstmtR.setInt(3, bookingInfo[1]);
							pstmtR.setString(4, txtenterReviews.getText());
							pstmtR.setInt(5, slider_cleanliness.getValue());
							pstmtR.setInt(6, slider_communications.getValue());
							pstmtR.setInt(7, slider_checkin.getValue());	
							pstmtR.setInt(8, slider_accuracy.getValue());
							pstmtR.setInt(9, slider_location.getValue());
							pstmtR.setInt(10, slider_value.getValue());
							
							int affectedRows_prop = pstmtR.executeUpdate();
							if(affectedRows_prop == 1) {
								// Success entering review
								JOptionPane.showMessageDialog(null, "Thanks for your valuable feedback!", "Feedback", JOptionPane.INFORMATION_MESSAGE);
								con.commit();
							} else {
								con.rollback(); 
								JOptionPane.showMessageDialog(null, "Failure making review", "Error", JOptionPane.ERROR_MESSAGE);
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
				                if(pstmtR != null) 	pstmtR.close();
				                if(con != null) 	con.close();
							} catch (SQLException ex) {
								ex.printStackTrace();
							}
						}
						dispose();
					}
					else JOptionPane.showMessageDialog(null, "Comment is too long!", "Error", JOptionPane.ERROR_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(null, "Review already exists for this booking", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		});
		btnSubmitReview.setBounds(179, 367, 131, 38);
		panel_reviews.add(btnSubmitReview);
		btnSubmitReview.setToolTipText("Click here to Submit a Review");
		
		
	}
	
	public int[] getBookingInfo(int bookingID) {
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int guestID = 0;
        int propertyID = 0;
        
        String sqlQuery = "SELECT propertyID, guestID FROM Booking WHERE bookingID = ?";
		
		try {					
			//establish connection
			con = SQL_Interface.getConnection();
			
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setInt(1, bookingID);
			rs = pstmt.executeQuery();
			
			if (!rs.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, "Invalid booking.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			while (rs.next()) {
				guestID = rs.getInt("guestID");
				propertyID = rs.getInt("propertyID");
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
		int[] results = {propertyID, guestID};
		return results;
	}
	
	// Check if review exists for a bookingID already
	private boolean reviewExists(int bookingID) {
		boolean exists = false;
		//SQL initialization
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sqlQuery = "SELECT bookingID FROM Review WHERE bookingID = ?";
		
		try {					
			//establish connection
			con = SQL_Interface.getConnection();
			
			pstmt = con.prepareStatement(sqlQuery);
			pstmt.setInt(1, bookingID);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				exists = true;
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
		return exists;
	};
}
