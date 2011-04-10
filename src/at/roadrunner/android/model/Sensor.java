package at.roadrunner.android.model;

import at.roadrunner.android.sensor.Protocol;
import at.roadrunner.android.sensor.SensorType;

public abstract class Sensor {
	protected SensorType _type;
	protected Protocol _protocol;
	protected String _uri;
	
	public Sensor(String uri, Protocol protocol) {
		_uri = uri;
		_protocol = protocol;
	}

	public String getUri() {
		return _uri;
	}
	
	public Protocol getProtocol() {
		return _protocol;
	}

	public SensorType getType() {
		return _type;
	}

}
