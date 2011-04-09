package at.roadrunner.android.model;

import at.roadrunner.android.sensor.SensorType;



public class TemperatureSensor extends Sensor {
	private double _minTemperature;
	private double _maxTemperature;
	
	public TemperatureSensor(String url) {
		super(url);
		_type = SensorType.Temperature;
	}
}
