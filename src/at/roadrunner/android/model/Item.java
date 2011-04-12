package at.roadrunner.android.model;

import android.util.Log;

public class Item {
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

	@Override
	public boolean equals(Object o) {
		Log.v("item", ( (Item) o)._key);
		return ( (Item) o)._key.equals(this._key);
	}
}
