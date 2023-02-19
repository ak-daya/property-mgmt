package backend;

import java.util.Date;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;


/* 
 * Class for Property Elements initialisation and Sleeping Capacity
*/

public class Property{
	// Instance Variables

	public boolean breakfastAvailability;
	public int propertyID;
	public String shortName;
	public String longName;
	public String areaName;
	public int doorNumber; 			// hidden to guests by default
	public String streetName; 			// hidden to guests by default
	public String postcode;

	
	//constructor
	public Property() {
	}

	//methods
	public String description() {
		return this.shortName + " - " + this.longName;
	}
	
	public String getPublicLocation() {
		return ("Located in " + this.areaName + ".");
	}
	
	// Gets max guests for entire property using bedrooms' sleeping capacities
	public int maxGuests() {
		int guests=0;
		guests = Bedroom.getSleepingCapacity(bedrooms.get(0).bed1, bedrooms.get(0).bed2);
		return guests;
	}

	// Host Info
	public String forename;
	public String surname;
	public boolean superHost;
	public int hostID;
	
	// Living Facilities
	public boolean wifi;
	public boolean television;
	public boolean satellite;
	public boolean streaming;
	public boolean dvdPlayer;
	public boolean boardGames;
	
	// Bathing Facilities
	public boolean hairDryer;
	public boolean shampoo;
	public boolean toiletPaper;
	public int noOfBathrooms;
	
	// Bathing Facilities Bathroom
	public boolean toilet;
	public boolean bath;
	public boolean shower;
	public boolean shared;
	
	// Kitchen Facilities
	public boolean fridge;
	public boolean microwave;
	public boolean oven;
	public boolean dishwasher;
	public boolean cookware;
	public boolean tableware;
	public boolean basicProvisions;
	
	// Utility Facilities
	public boolean centralHeating;
	public boolean washingMachine;
	public boolean dryingMachine;
	public boolean fireExtinguisher;
	public boolean smokeAlarm;
	public boolean firstAidKit;
	
	// Sleeping Facilities
	public boolean bedLinen;
	public boolean towels;
	public int noOfBedrooms;
	
	// Sleeping Facilities Bedroom
	public ArrayList<Bathroom> bathrooms;
	public ArrayList<Bedroom> bedrooms;
	
	// iterates Bedrooms sleeping capacity
	public void SleepingCapacity() {}
	
	// Outdoor Facilities
	public boolean onsitePark;
	public boolean onroadPark;
	public boolean paidPark;
	public boolean patio;
	public boolean barbeque;
	
	// Constructor Declaration of Class
	public void houseInfo(boolean breakfastAvailability, String shortName, String longName, String areaName, int doorNumber, String streetName, String postcode) {
		this.breakfastAvailability = breakfastAvailability;
		this.shortName = shortName;
		this.longName = longName;
		this.areaName = areaName;
		this.doorNumber = doorNumber;
		this.streetName = streetName;
		this.postcode = postcode;
	}
	
	public void hasBathrooms(ArrayList<Bathroom> bathrooms) {
		this.bathrooms = bathrooms;
	}
	
	public void hasBedrooms(ArrayList<Bedroom> bedrooms) {
		this.bedrooms = bedrooms;
	}
	
	// Living Facilities
	public void hasLivingFacility(boolean television, boolean wifi, boolean satellite, boolean streaming, boolean dvdPlayer, boolean boardGames)
	{
		this.wifi = wifi;
		this.television = television;
		this.satellite = satellite;
		this.streaming = streaming;
		this.dvdPlayer = dvdPlayer;
		this.boardGames = boardGames;
	}
	
	// Bathing Facilities
	public void hasBathingFacility(boolean hairDryer, boolean shampoo, boolean toiletPaper)
	{
		this.hairDryer = hairDryer;
		this.shampoo = shampoo;
		this.toiletPaper = toiletPaper;
		this.noOfBathrooms = bathrooms.size();
	}
	
	// Bathing Facilities Bathrooms
	public void hasBathingFacilityBathroom(boolean toilet, boolean bath, boolean shower, boolean shared)
	{
		this.toilet = toilet;
		this.bath = bath;
		this.shower = shower;
		this.shared = shared;
	}
	
	// Kitchen Facilities
	public void hasKitchenFacility(boolean fridge, boolean microwave, boolean oven, boolean dishwasher, boolean cookware, boolean tableware, boolean basicProvisions)
	{
		this.fridge = fridge;
		this.microwave = microwave;
		this.oven = oven;
		this.dishwasher = dishwasher;
		this.cookware = cookware;
		this.tableware = tableware;
		this.basicProvisions = basicProvisions;
	}
	
	// Utility Facilities
	public void hasUtilityFacility(boolean centralHeating, boolean washingMachine, boolean dryingMachine, boolean fireExtinguisher, boolean smokeAlarm, boolean firstAidKit)
	{
		this.centralHeating = centralHeating;
		this.washingMachine = washingMachine;
		this.dryingMachine = dryingMachine;
		this.fireExtinguisher = fireExtinguisher;
		this.smokeAlarm = smokeAlarm;
		this.firstAidKit = firstAidKit;
	}
	
	// Sleeping Facilities
	public void hasSleepingFacility(boolean bedLinen, boolean towels)
	{
		this.bedLinen = bedLinen;
		this.towels = towels;
		this.noOfBedrooms = bedrooms.size();
	}
	
	// Outdoor Facilities
	public void hasOutdoorFacility(boolean onsitePark, boolean onRoadPark, boolean paidPark, boolean patio, boolean barbeque)
	{
		this.onsitePark = onsitePark;
		this.onroadPark = onRoadPark;
		this.paidPark = paidPark;
		this.patio = patio;
		this.barbeque = barbeque;
	}
	
	// Host Information
	public void hostInfo(String forename, String surname, int hostID) {
		this.forename = forename;
		this.surname = surname;
		this.superHost = false;
		this.hostID = hostID;
	}

}