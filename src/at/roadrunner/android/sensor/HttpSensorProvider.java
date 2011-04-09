/*
 * HttpSensorProvider.java
 */
package at.roadrunner.android.sensor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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

	protected SensorConnectionFactory _connection_fact;
	
	protected final static String _request_URI = Config.DB_HOST + "/_design/views/sensor";
	
	/**
	 * Constructor
	 * @param cId Integer Container Id
	 */
	public HttpSensorProvider() {
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
		
		String[] uris = getSensorUris();
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
	 * @return String[] 
	 * @throws JSONException 
	 * @throws CouchDBNotReachableException 
	 */
	private String[] getSensorUris() 
			throws CouchDBNotReachableException, JSONException {
	
		String[] sensorUris =  new String[1];
		 
		String res = HttpExecutor.getInstance().executeForResponse(
				RequestFactory.createHttpGet(HttpSensorProvider._request_URI));
		

		// FIXME: Parse Json answer into sensorURIs[] Array
		JSONObject json = new JSONObject(res); 
		
		
		
		return sensorUris;
	}
}
