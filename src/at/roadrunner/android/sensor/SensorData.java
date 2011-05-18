package at.roadrunner.android.sensor;

public class SensorData {
	//TODO: remove default
	private static float _temperature = 42;
	
	public static synchronized float getTemperature()  {
		return _temperature;
	}
	
	public static synchronized void setTemperature(float temperature)  {
		_temperature = temperature;
	}
}
