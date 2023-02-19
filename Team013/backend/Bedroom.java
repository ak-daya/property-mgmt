package backend;

/* 
 * Class for Bedroom Properties and Bed Types and Calculating Sleeping Capacity based on No. of Rooms and Bed Types
*/

public class Bedroom {
	public String bed1;
	public String bed2;
	public int capacity;
	
	public Bedroom (String... bedTypes) {
		if (bedTypes.length == 1) {
			this.bed1 = bedTypes[0];
			this.bed2 = "";
		}
		else if (bedTypes.length == 2) {
			this.bed1 = bedTypes[0];
			this.bed2 = bedTypes[1];
		}
	}
	
	public static int getSleepingCapacity(String bed1, String bed2) {
		return getBedCapacity(bed1) + getBedCapacity(bed2);
	}
	

	public static int getBedCapacity(String bedType) {
		if (bedType == null) { 
			return 0;
		}
		int capacity = 0;
		if (bedType == "Single") { 
			capacity = 1;
		}
		else if (bedType.equals("Double") || bedType.equals("King Size") || bedType.equals("Bunk")) {
			capacity = 2;
		}
		return capacity;
	}
}
