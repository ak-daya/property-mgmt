package backend;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

/* 
 * Class for Input Validation for Login and SignUp
*/

public class InputValidation {
	//Email input validation - Source: https://www.baeldung.com/java-email-validation-regex (Accessed: X/1X/21)
	final static String regexPattern_email = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	final static String regexPattern_mobNum = "^(((00)44)|0)?[- ]?\\d{3,4}[- ]?\\d{3}[- ]?\\d{3}$";
	final static String regexPattern_password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&?_/$]).{8,32}$";

	
	public static boolean patternMatches(String input_string, String stringType) {
		boolean match = false;
		switch (stringType) {
			case "email":
				match = Pattern.compile(regexPattern_email).matcher(input_string).matches();
				break;
			case "mobileNumber":
				match = Pattern.compile(regexPattern_mobNum).matcher(input_string).matches();
				break;
			case "password":
				match = Pattern.compile(regexPattern_password).matcher(input_string).matches();
				break;
			default:
				
		}
		return match;
	}
	
	/* 
	 * Checks that neither of the dates are before todays date and other date validations
	*/
	
	public static boolean dateCheckNotPast(LocalDate checkInDate, LocalDate checkOutDate) {
		Calendar myCal = Calendar.getInstance();
		Date todayDate = myCal.getTime();
		LocalDate todayDateToCheck = todayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (checkInDate.isBefore(todayDateToCheck) || checkOutDate.isBefore(todayDateToCheck)) {
			return false;
		}
		return true;
	}
	
	public static boolean checkInDateValid(LocalDate checkInDate, LocalDate checkOutDate) {
		if (checkInDate.isAfter(checkOutDate) || checkInDate.equals(checkOutDate)) {
			return false;
		}
		return true;
	}
	
	/* 
	 * Checks Password Hashing and Encryption
	*/
	
	public static boolean checkPassword(String password_input, String db_hash) {
		boolean password_ok = false;
		
		if(null == db_hash || !db_hash.startsWith("$2a$")) return password_ok;
		
		password_ok = BCrypt.checkpw(password_input, db_hash);
		
		return password_ok;
	}
	
}