/**
 * HttpSensor.java
 *
 * This file is part of the Roadrunner project.
 * 
 * Copyright (c) 2011 Franziskus Domig. All rights reserved.
 *
 * @author Franziskus Domig
 * @date 10.03.2011
 */
package at.roadrunner.android.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

import at.roadrunner.android.sensor.Sensor;
import at.roadrunner.android.sensor.SensorConnectionFactory;
import at.roadrunner.android.sensor.SensorType;

/**
 * Class HttpSensor
 * 
 * @author Matthias Schmid
 */
public class HttpSensor implements Sensor, Serializable {

	private static final long serialVersionUID = 1L;

	protected URL _url;
	
	protected SensorType _type;

	protected SensorConnectionFactory _factory;
	
	public HttpSensor(URL url, SensorType type, SensorConnectionFactory f) {
		_url = url;
		_factory = f;
		_type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.roadrunner.sensor.Sensor#getData()
	 */
	public String getData() throws IOException {
		StringBuilder string = new StringBuilder();
		int b = 0;
		InputStream stream = _factory.openHttpConnection(_url)
				.getInputStream();
		while (-1 != (b = stream.read())) {
			string.append((char) b);
		}
		return string.toString();
	}
	
	public SensorType getType() {
		return _type;
	}
}
