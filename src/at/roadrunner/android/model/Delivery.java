package at.roadrunner.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONObject;

public class Delivery implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _trackingNumber;
	private Address _from;
	private Address _to;
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

	public Address getAddressTo() {
		return _to;
	}
	
	public void setAddressTo(Address to) {
		_to = to;
	}
	
	public Address getAddressFrom() {
		return _from;
	}
	
	public void setAddressFrom(Address from) {
		_from = from;
	}

	public ArrayList<Item> getItems() {
		return _items;
	}
	
	public void addItem(Item item) {
		_items.add(item);
	}
}
