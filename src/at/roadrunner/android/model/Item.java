package at.roadrunner.android.model;

public class Item {
	private String m_key;
	private String m_timestamp;
	
	public Item(String key, String timestamp) {
		m_key = key;
		m_timestamp = timestamp;
	}

	public String getKey() {
		return m_key;
	}

	public String getTimestamp() {
		return m_timestamp;
	}
}
