package at.roadrunner.android.sensor;

import android.location.Location;

public class SensorData {
	private static Float _temperature;
	private static Location _location;
	
	public static synchronized Float getTemperature()  {
		return _temperature;
	}
	
	public static synchronized void setTemperature(Float temperature)  {
		_temperature = temperature;
	}

	public static synchronized Location getLocation() {
		return _location;
	}

	public static synchronized void setLocation(Location location) {
		_location = location;
	}
}
