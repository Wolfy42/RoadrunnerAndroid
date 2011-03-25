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
package at.roadrunner.android.sensor;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Class HttpSensor
 * 
 * @author Franziskus Domig
 */
public class HttpSensor implements Sensor {

	/**
	 * URI
	 */
	protected String _uri;

	/**
	 * Constructor
	 * @param uri
	 */
	public HttpSensor(String uri) {
		_uri = uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.roadrunner.sensor.Sensor#getData()
	 */
	public String getData() throws IOException {
		StringBuilder string = new StringBuilder();
		int b = 0;
		while (-1 != (b = query().getEntity().getContent().read())) {
			string.append((char) b);
		}
		return string.toString();
	}
	
	/**
	 * Establishes the Sensor Connection
	 * 
	 * @return HttpResponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResponse query() throws ClientProtocolException, IOException {
		HttpClient c = new DefaultHttpClient();
		return c.execute(new HttpGet(_uri));
	}
}
