package at.roadrunner.android.sensor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SensorConnectionFactory {

	public HttpURLConnection openHttpConnection(URL url) throws IOException {
		return (HttpURLConnection)url.openConnection();
	}

}
