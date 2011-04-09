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
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.roadrunner.android.couchdb.CouchDBException.CouchDBNotReachableException;
import at.roadrunner.android.model.Sensor;
import at.roadrunner.android.model.TemperatureSensor;
import at.roadrunner.android.sensor.HttpSensor;
import at.roadrunner.android.sensor.HttpSensorProvider;
import at.roadrunner.android.sensor.SensorConnectionFactory;
import at.roadrunner.android.sensor.SensorType;

/**
 * Class HttpSensorTest
 * 
 * @author Matthias Schmid
 */
public class HttpSensorProviderTest {

	private HttpSensor _httpSensor;
	private HttpSensorProvider _httpSensorProvider;
	private List<Sensor> _sensorModelList;
	private List<at.roadrunner.android.sensor.Sensor> _hwSensors;
	
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
		
		_httpSensor = mock(HttpSensor.class);
		when(_httpSensor.getData()).thenReturn("foo");
		_hwSensors = new ArrayList<at.roadrunner.android.sensor.Sensor>();
		_hwSensors.add(_httpSensor);
		
		_sensorModelList = new ArrayList<Sensor>();
		_sensorModelList.add(new TemperatureSensor("http://172.16.102.224:4711"));
		_httpSensorProvider = mock(HttpSensorProvider.class);
		when(_httpSensorProvider.discover(_sensorModelList)).thenReturn(_hwSensors);
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		_httpSensor = null;
		_httpSensorProvider = null;
		_hwSensors = null;
		_sensorModelList = null;
		_pos = 0;
	}

	/**
	 * Test method for {@link at.roadrunner.sensor.HttpSensor#getData()}.
	 * @throws JSONException 
	 * @throws CouchDBNotReachableException 
	 */
	@Test
	public void testGetData() throws CouchDBNotReachableException, JSONException {
		try {
			
			List<at.roadrunner.android.sensor.Sensor> hwSensors = _httpSensorProvider.discover(_sensorModelList);
			for (at.roadrunner.android.sensor.Sensor s : hwSensors) {
				assertEquals("foo", s.getData());
			}
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
