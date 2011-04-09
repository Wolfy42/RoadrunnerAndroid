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
	 * @param sensorModelList the Sensor Model received from CouchDB 
	 * @return List<Sensor> the registered sensors of container
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws CouchDBNotReachableException 
	 */
	public List<Sensor> discover(List<at.roadrunner.android.model.Sensor> sensorModelList) throws IOException, 
			CouchDBNotReachableException, JSONException {
		
		List<Sensor> sensors = new ArrayList<Sensor>();
		for (at.roadrunner.android.model.Sensor s : sensorModelList) {
			sensors.add(new HttpSensor(new URL(s.getUri()), _connection_fact));
		}
		return sensors;
	}

}
