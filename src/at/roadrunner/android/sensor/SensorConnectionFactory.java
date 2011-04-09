package at.roadrunner.android.sensor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Log;
import at.roadrunner.android.model.Log.LogType;

/**
 * Class SensorConnectionFactory
 * 
 * @author matthias schmid
 * @date 09.04.2011
 *
 * This Class opens Connections for all Types of Sensors. 
 * Uses Class RequestWorker to log Errors
 */
public class SensorConnectionFactory {

	/**
	 * Opens a HttpURLConnection to the specified URL
	 * Automatically logs Connection Errors with LogType SENSOR_NOT_ACCESSIBLE
	 * Rethrows the catched IOException to be handled by any upper Components
	 * @param url
	 * @return the open HttpURLConnection
	 * @throws IOException
	 */
	public HttpURLConnection openHttpConnection(URL url) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)url.openConnection();
		} catch (IOException e) {
			RequestWorker.log(LogType.SENSOR_NOT_ACCESSIBLE, Log.ERROR, url.getPath());
			throw e;
		}
		return connection;
	}

}
