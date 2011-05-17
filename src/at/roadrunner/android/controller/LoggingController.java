package at.roadrunner.android.controller;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;
import at.roadrunner.android.couchdb.CouchDBException.CouchDBNotReachableException;
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

	private void logSensorDataForLoadedItems() throws CouchDBNotReachableException {
		ArrayList<Item> items = new ItemController(_context).getLoadedItems();
		if (items.size() == 0)  return;
		JSONArray array = new JSONArray();
		for (Item item : items)  {
			array.put(item.getKey());
		}
		_requestWorker.saveLog(array, 
				LogType.TEMPSENSOR, 
				Float.toString(SensorData.getTemperature()));
		
		Log.e("roadrunner", items.toString());
	}
}
