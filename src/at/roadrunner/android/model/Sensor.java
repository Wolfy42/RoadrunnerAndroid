package at.roadrunner.android.model;

import at.roadrunner.android.sensor.SensorType;

public abstract class Sensor {
	protected SensorType _type;
	protected String _uri;
	
	public Sensor(String uri) {
		_uri = uri;
	}

	public String getUri() {
		return _uri;
	}

}
