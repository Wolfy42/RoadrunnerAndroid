/**
 * HttpSensorTest.java
 *
 * This file is part of the Roadrunner project.
 * 
 * Copyright (c) 2011 Franziskus Domig. All rights reserved.
 *
 * @author Franziskus Domig
 * @date 10.03.2011
 */
package at.roadrunner.android.test.sensor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.roadrunner.android.model.HttpSensor;
import at.roadrunner.android.sensor.SensorConnectionFactory;
import at.roadrunner.android.sensor.SensorType;

/**
 * Class HttpSensorTest
 * 
 * @author Matthias Schmid
 */
public class HttpSensorTest {

	private HttpSensor _httpSensor;
	
	private SensorConnectionFactory _sensorConnectionFactory;

	static private int _pos = 0;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		HttpURLConnection connection = mock(HttpURLConnection.class);

		InputStream stream = new InputStream() {
			@Override
			public int read() throws IOException {
				char[] data = { '1', '7', '.', '5' };
				if (_pos >= data.length) {
					return -1;
				}
				return data[_pos++];
			}
		};
		
		
		URL url = new URL("http://172.16.102.224:4711");
		when(connection.getInputStream()).thenReturn(stream);
		_sensorConnectionFactory = mock(SensorConnectionFactory.class);
		when(_sensorConnectionFactory.openHttpConnection(url)).thenReturn(connection);
		_httpSensor = new HttpSensor(url, SensorType.Temperature, _sensorConnectionFactory);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		_httpSensor = null;
		_pos = 0;
	}

	/**
	 * Test method for {@link at.roadrunner.sensor.HttpSensor#getData()}.
	 */
	@Test
	public void testGetData() {
		try {
			assertEquals("17.5", _httpSensor.getData());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
