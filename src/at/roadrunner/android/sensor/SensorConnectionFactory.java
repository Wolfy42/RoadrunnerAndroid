package at.roadrunner.android.sensor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class SensorConnectionFactory
 * 
 * @author matthias schmid
 * @date 09.04.2011
 *
 * This Class opens Connections for all Types of Sensors 
 */
public class SensorConnectionFactory {

	/**
	 * Opens a HttpURLConnection to the specified URL
	 * 
	 * @param url
	 * @return the open HttpURLConnection
	 * @throws IOException
	 */
	public HttpURLConnection openHttpConnection(URL url) throws IOException {
		return (HttpURLConnection)url.openConnection();
	}

}
