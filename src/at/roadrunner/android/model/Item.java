package at.roadrunner.android.model;

import java.io.Serializable;


public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _key;
	private String _name;
	private long _timestamp;
	
	public Item(String key, long timestamp) {
		_key = key;
		_timestamp = timestamp;
	}

	public void setName(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}

	public String getKey() {
		return _key;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	
}
