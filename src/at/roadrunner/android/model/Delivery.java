package at.roadrunner.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONObject;

public class Delivery implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _trackingNumber;
	private Address _destination;
	private Address _from;
	private ArrayList<Item> _items = new ArrayList<Item>();
	
	public Delivery(String trackingNumber) {
		_trackingNumber = trackingNumber;
	}
	
	public Delivery(JSONObject delivery) {
		createObject(delivery);
	}

	/*
	 * creates the Delivery object of the given Json-Object
	 */
	private void createObject(JSONObject delivery) {
		//TODO: create the Object
	}

	public String getTrackingNumber() {
		return _trackingNumber;
	}
	
	public void setTrackingNumber(String number) {
		_trackingNumber = number;
	}

	public Address getDestination() {
		return _destination;
	}
	
	public void setDestination(Address destination) {
		_destination = destination;
	}
	
	public Address getFrom() {
		return _from;
	}
	
	public void setFrom(Address from) {
		_from = from;
	}

	public ArrayList<Item> getItems() {
		return _items;
	}
	
	public void addItem(Item item) {
		_items.add(item);
	}
}
