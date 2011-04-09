package at.roadrunner.android.model;

import at.roadrunner.android.sensor.SensorType;



public class TemperaturSensor extends Sensor {
	private double _minTemperature;
	private double _maxTemperature;
	
	public TemperaturSensor(SensorType type, String url) {
		super(type, url);
	}
}
