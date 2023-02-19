package backend;

import java.util.ArrayList;
import java.util.List;

/* 
 * Class for Person Information initialisation and calling
*/

public class Person {
	String email;
	//https://medium.com/javarevisited/handling-passwords-in-java-swing-and-sql-f0e52002a04c for pwd
	String password;	
	String title;
	String fname;
	String lname;
	int mobileNum;
	
	//Constructor
	public Person(String email, String title, String fname, String lname, int mobileNum) {
		this.email = email;
		this.title = title;
		this.fname = fname;
		this.lname = lname;
		this.mobileNum = mobileNum;	
	}
	
	
	//methods
	public String getFullName() {
		return this.title + ". " + this.fname + this.lname;
	}
	
	//returns email, full name and phone number to be called by the page where confidential details are displayed
	public List<String> getInfo(){
		List<String> personalInfo = new ArrayList<String>();
		personalInfo.add(email);
		personalInfo.add(this.getFullName());
		personalInfo.add(String.valueOf(mobileNum));
		return personalInfo;
	}
	
	public String getEmail() 	{ return email; 	}
	public String getTitle() 	{ return title; 	}
	public String getFname() 	{ return fname; 	} 
	public String getLname() 	{ return lname; 	}
	public int getMobileNum() 	{ return mobileNum; }

}
