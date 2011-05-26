package at.roadrunner.android.controller;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Item;
import at.roadrunner.android.model.Log.LogType;
import at.roadrunner.android.sensor.SensorData;

public class LoggingController {
	
	private Context _context;
	private RequestWorker _requestWorker;
	
	public LoggingController(Context context) {
		_context = context;
		_requestWorker = new RequestWorker(_context);
	}
	
	public void logSensorData()  {
		try  {
			logSensorDataForLoadedItems();
		} catch (Exception e)  {
			e.printStackTrace();
			//TODO: What should happen? Maybe CouchDB is not reachable
		}
	}

	private void logSensorDataForLoadedItems() throws CouchDBException {
		ArrayList<Item> items = new ItemController(_context).getLoadedItems();
		if (items.size() == 0)  return;
		JSONArray array = new JSONArray();
		for (Item item : items)  {
			array.put(item.getKey());
		}
		Float temperature = SensorData.getTemperature();
		Location loc = SensorData.getLocation();
		
		if (temperature == null)  {
			_requestWorker.saveLog(array, 
					LogType.TEMPERROR, 
					"Temperatue sensor not available.", null);
		}  else  {
			_requestWorker.saveLog(array, 
					LogType.TEMPSENSOR, 
					Float.toString(SensorData.getTemperature()), null);
		}
		
		if (loc == null)  {
			_requestWorker.saveLog(array, 
					LogType.POSERROR, 
					"GPS not available.", null);
		}  else  {
			_requestWorker.saveLog(array, 
					LogType.POSSENSOR, 
					loc.getLongitude()+","+loc.getLatitude(), null);
		}
		Log.e("roadrunner", items.toString());
	}
}
