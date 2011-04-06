/*
 * HttpSensorProvider.java
 */
package at.roadrunner.android.sensor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import at.roadrunner.android.Config;
import at.roadrunner.android.couchdb.CouchDBException.CouchDBNotReachableException;
import at.roadrunner.android.couchdb.HttpExecutor;
import at.roadrunner.android.couchdb.RequestFactory;


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
	protected Integer _container_id;
	
	protected SensorConnectionFactory _connection_fact;
	
	/**
	 * Constructor
	 * @param cId Integer Container Id
	 */
	public HttpSensorProvider(Integer cId) {
		_container_id = cId;
		_connection_fact = new SensorConnectionFactory();
	}
	
	
	/**
	 * Discovers all available sensors 
	 * 
	 * @return List<Sensor> the registered sensors of container
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws CouchDBNotReachableException 
	 */
	public List<Sensor> discover() throws IOException, 
			CouchDBNotReachableException, JSONException {
		
		String[] uris = getSensorUris(_container_id);
		List<Sensor> sensors = new ArrayList<Sensor>();
		for (String s : uris) {
			URL url = new URL(s);
			sensors.add(new HttpSensor(url, _connection_fact));
		}
		return sensors;
	}
	
	/**
	 * Gets the URIs of all Sensors in Container _container_id 
	 * 
	 * @param cId
	 * @return String[] 
	 * @throws JSONException 
	 * @throws CouchDBNotReachableException 
	 */
	private String[] getSensorUris(Integer cId) 
			throws CouchDBNotReachableException, JSONException {
	
		String[] sensorUris =  new String[1];
		String requestURI = Config.DB_HOST; // FIXME: create correct URL  Config.URI???
		String json = HttpExecutor.getInstance().executeForResponse(
				RequestFactory.createHttpGet(requestURI));
		
		// FIXME: Parse Json answer into sensorURIs[] Array
		
		// FIXME: delete this line if request factory works
		sensorUris[0] = "http://roadrunner.server:4711";
		
		return sensorUris;
	}
}
