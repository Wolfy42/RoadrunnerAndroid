package at.roadrunner.android.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

public class Delivery implements Serializable {
	private static final long serialVersionUID = 1L;
	private long _trackingNumber;
	private Address _destination;
	private String _constituent;
	private ArrayList<Item> _items = new ArrayList<Item>();
	private ArrayList<at.roadrunner.android.sensor.Sensor> _sensors = new ArrayList<at.roadrunner.android.sensor.Sensor>();
	
	public Delivery(long trackingNumber) {
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

	public long getTrackingNumber() {
		return _trackingNumber;
	}
	
	public void setTrackingNumber(long number) {
		_trackingNumber = number;
	}

	public Address getDestination() {
		return _destination;
	}
	
	public void setDestination(Address destination) {
		_destination = destination;
	}

	public String getConstituent() {
		return _constituent;
	}
	
	public void setConstituent(String name) {
		_constituent = name;
	}

	public ArrayList<Item> getItems() {
		return _items;
	}
	
	public void addItem(Item item) {
		_items.add(item);
	}

	public ArrayList<at.roadrunner.android.sensor.Sensor> getSensor() {
		return _sensors;
	}
	
	public void addSensor(at.roadrunner.android.sensor.Sensor sensor) {
		_sensors.add(sensor);
	}
}
