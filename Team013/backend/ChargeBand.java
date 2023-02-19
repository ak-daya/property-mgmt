package backend;

import java.util.Date;

/* 
 * Class to calculate charge band for properties
*/

public class ChargeBand {
	//attributes
	public Date startDate;
	public Date endDate;
	public double pricePerNight;
	public double serviceCharge;
	public double cleaningCharge;
	
	//constructor
	public ChargeBand(Date startDate, Date endDate, double pricePerNight, double serviceCharge, double cleaningCharge) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.pricePerNight = pricePerNight;
		this.serviceCharge = serviceCharge;
		this.cleaningCharge = cleaningCharge;
	}
}
