package at.roadrunner.android.model;

import at.roadrunner.android.sensor.Protocol;
import at.roadrunner.android.sensor.SensorType;



public class TemperatureSensor extends Sensor {
	private double _minTemperature;
	private double _maxTemperature;
	
	public TemperatureSensor(String url, Protocol protocol) {
		super(url, protocol);
		_type = SensorType.Temperature;
	}
}
