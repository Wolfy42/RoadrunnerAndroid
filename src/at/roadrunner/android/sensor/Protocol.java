package at.roadrunner.android.sensor;


public enum Protocol {
	HTTP, 
	BLUETOOTH, 
	ZIGBEE;
	
	public String[] listAll() {
		String[] res = new String[this.values().length];
		for (Protocol p : this.values()) {
			res[p.ordinal()] = p.toString();
		}
		return res;
	}
}
