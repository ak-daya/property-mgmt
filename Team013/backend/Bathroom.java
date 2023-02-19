package backend;

/* 
 * Class for Bathroom Properties and Elements
*/

public class Bathroom {
	public boolean toilet;
	public boolean bath;
	public boolean shower;
	public boolean shared;
	
	public Bathroom(boolean toilet, boolean bath, boolean shower, boolean shared) {
		this.toilet = toilet;
		this.bath = bath;
		this.shower = shower;
		this.shared = shared;
	}
}
