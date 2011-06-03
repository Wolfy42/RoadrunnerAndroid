package at.roadrunner.android.model;

import java.io.Serializable;


public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _key;
	private long _timestamp;
	private String _name;
	private int _maxTemp;
	private int _minTemp;
	
	public Item(String name, int minTemp, int maxTemp) {
		_name = name;
		_minTemp = minTemp;
		_maxTemp = maxTemp;
	}
	
	public Item(String key, long timestamp) {
		_key = key;
		_timestamp = timestamp;
	}
	
	public void setKey(String key) {
		_key = key;
	}
	
	public String getKey() {
		return _key;
	}
	
	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}
	
	public long getTimestamp() {
		return _timestamp;
	}

	public void setName(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}

	public int getMaxTemp() {
		return _maxTemp;
	}

	public void setMaxTemp(int maxTemp) {
		_maxTemp = maxTemp;
	}

	public int getMinTemp() {
		return _minTemp;
	}

	public void setMinTemp(int minTemp) {
		_minTemp = minTemp;
	}
}
