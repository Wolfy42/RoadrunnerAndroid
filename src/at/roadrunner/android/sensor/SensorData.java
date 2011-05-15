package at.roadrunner.android.sensor;

public class SensorData {
	private static float _temperature = 42; //TODO: remove default
	
	public static synchronized float getTemperature()  {
		return _temperature;
	}
	
	public static synchronized void setTemperature(float temperature)  {
		_temperature = temperature;
	}
}
