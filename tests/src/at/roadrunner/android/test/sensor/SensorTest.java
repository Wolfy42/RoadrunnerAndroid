package at.roadrunner.android.test.sensor;


import java.io.IOException;

import at.roadrunner.android.sensor.HttpSensor;
import at.roadrunner.android.sensor.Sensor;
import junit.framework.TestCase;

public class SensorTest extends TestCase {

	
	/**
	 * Tests a HttpSensor at location URI
	 * If Node at location URI is not running, this test will not succeed 
	 */
	public void testHttpSensor() {
		String uri = "http://roadrunner.server:471";
		Sensor sensor = new HttpSensor(uri); 
		try {
			assertTrue(sensor.getData() == "17");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
