/*
 * HttpSensorProvider.java
 */
package at.roadrunner.android.sensor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Class HttpSensorProvider
 * 
 * @author matthias schmid
 * @date 25.03.2011
 */
public class HttpSensorProvider {

	
	/**
	 * The Container Id
	 */
	private Integer _container_id;
	
	/**
	 * Constructor
	 * @param cId Integer Container Id
	 */
	public HttpSensorProvider(Integer cId) {
		_container_id = cId;
	}
	
	
	/**
	 * Discovers all available sensors 
	 * 
	 * @return List<Sensor> the registered sensors of container
	 * @throws MalformedURLException 
	 */
	public List<URL> discover() throws MalformedURLException {
		
		String[] uris = getSensorUris(_container_id);
		List<URL> sensors = new ArrayList<URL>();
		for (String s : uris) {
			sensors.add(new URL(s));
		}
		return sensors;
	}
	
	/**
	 * Gets the URIs of all Sensors in Container _container_id 
	 * 
	 * @param cId
	 * @return String[] 
	 */
	private String[] getSensorUris(Integer cId) {
		String[] sensorUris =  new String[1];
		
		sensorUris[0] = "http://roadrunner.server:4711";
		return sensorUris;
	}
}
