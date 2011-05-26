package at.roadrunner.android.controller;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.HttpExecutor;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.sensor.SensorData;

public class MonitoringController {
	private Context _context;
	private RequestWorker _requestWorker;
	private LocationManager _locationManager;
	
	public MonitoringController(Context context) {
		_context = context;
		_requestWorker = new RequestWorker(_context);
		_locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

	}
	
	public void readSensorData()  {
		readTemperaturSensor();
		readPositionSensor();
	}
	
	private void readTemperaturSensor()  {
		JSONArray sensors = _requestWorker.getSensorsForSelectedContainer();
		float temperature = 0;
		int count = 0;
		
		if (sensors != null) {
			for (int i=0; i<sensors.length(); i++)  {
				try {
					String url = sensors.getString(i);
					String temp = new HttpExecutor().executeForResponse(new HttpGet(url));
					temperature += Float.parseFloat(temp);
					count++;
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (CouchDBException e) {
					e.printStackTrace();
				}
			}
			if (count == 0)  {
				SensorData.setTemperature(null);
			}  else  {
				SensorData.setTemperature(temperature/count);
			}
		}
	}
	
	private void readPositionSensor() {
		Location loc = _locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		SensorData.setLocation(loc);
	}
}
