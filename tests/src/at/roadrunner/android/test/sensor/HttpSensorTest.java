package at.roadrunner.android.test.sensor;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import junit.framework.TestCase;
import android.util.Log;
import at.roadrunner.android.sensor.HttpSensor;

public class HttpSensorTest extends TestCase {

	private HttpSensor _httpSensor;

	static protected int _pos = 0;
	
	private class MockHttpURLConnection extends HttpURLConnection {
		
		protected MockHttpURLConnection(URL url) {
			super(url);
		}
		
		public InputStream getInputStream() {
			return new InputStream() {
				@Override
				public int read() throws IOException {
					char[] data = { '1', '7' };
					if (_pos >= data.length) {
						return -1;
					}
					return data[_pos++];
				}
			};
		}

		@Override
		public void disconnect() {}

		@Override
		public boolean usingProxy() {
			return false;
		}

		@Override
		public void connect() throws IOException {}
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		URL url  = new URL("http://172.16.102.224:4711");
		HttpURLConnection c = new MockHttpURLConnection(url);
	
		_httpSensor = new HttpSensor(c);	
	}
	
	/**
	 * Tests a HttpSensor at location URI
	 * If Node at location URI is not running, this test will not succeed 
	 * @throws IOException 
	 */
	public void testHttpSensor() throws IOException {
		String res = _httpSensor.getData();
		assertTrue(res.compareTo("17") == 0);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		_httpSensor = null;
	}

	
}
