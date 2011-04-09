package at.roadrunner.android.model;

import java.util.ArrayList;

import org.json.JSONObject;

public class Delivery {
	private long _trackingNumber;
	private Address _destination;
	private String _constituent;
	private ArrayList<Item> _items;
	private ArrayList<Sensor> _sensor;
	
	public Delivery(JSONObject delivery) {
		createObject(delivery);
	}

	/*
	 * creates the Delivery object of the given Json-Object
	 */
	private void createObject(JSONObject delivery) {
		//TODO: create the Object
		
	}

	public long getTrackingNumber() {
		return _trackingNumber;
	}

	public Address getDestination() {
		return _destination;
	}

	public String getConstituent() {
		return _constituent;
	}

	public ArrayList<Item> getItems() {
		return _items;
	}

	public ArrayList<Sensor> getSensor() {
		return _sensor;
	}
}
