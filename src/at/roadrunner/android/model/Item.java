package at.roadrunner.android.model;

import java.io.Serializable;


public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _key;
	private long _timestamp;
	private String _name;
	private double _maxTemp;
	private double _minTemp;
	private boolean _loaded;
	
	public Item(String name, double minTemp, double maxTemp, boolean loaded) {
		_name = name;
		_minTemp = minTemp;
		_maxTemp = maxTemp;
		_loaded = loaded;
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

	public double getMaxTemp() {
		return _maxTemp;
	}

	public void setMaxTemp(double maxTemp) {
		_maxTemp = maxTemp;
	}

	public double getMinTemp() {
		return _minTemp;
	}

	public void setMinTemp(double minTemp) {
		_minTemp = minTemp;
	}
	
	public boolean isLoaded()  {
		return _loaded;
	}
	
	public String getMinTempString() {
		double temp = getMinTemp();
		String result = "";
		if (Math.floor(temp) != temp) {
			result = String.valueOf(temp);
		} else {
			result = String.valueOf((int)Math.round(temp));
		}
		return result + " C°";
	}
	
	public String getMaxTempString() {
		double temp = getMaxTemp();
		String result = "";
		if (Math.floor(temp) != temp) {
			result = String.valueOf(temp);
		} else {
			result = String.valueOf((int)Math.round(temp));
		}
		return result + " C°";
	}
}
