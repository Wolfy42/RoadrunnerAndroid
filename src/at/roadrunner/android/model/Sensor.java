package at.roadrunner.android.model;

import at.roadrunner.android.sensor.SensorType;

public abstract class Sensor {
	private SensorType _type;
	private String _url;
	
	public Sensor(SensorType type, String url) {
		_type = type;
		_url = url;
	}

}
