package at.roadrunner.android.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Item;
import at.roadrunner.android.model.Log.LogType;

public class ContainerController {

	private Context _context;
	
	public ContainerController(Context context) {
		_context = context;
	}

	public boolean replicateContainers() {
		return new RequestWorker(_context).replicateContainers();
	}
	
	public ArrayList<String> getContainers() {
		ArrayList<String> listOfContainers = null;
		try {
			String containers = new RequestWorker(_context).getContainers();
			if (containers != null) {
				listOfContainers = new ArrayList<String>();
				JSONArray array = new JSONObject(containers).getJSONArray("rows");
					
				for (int i=0; i < array.length(); i++)  {
					listOfContainers.add(array.getJSONObject(i).getString("value"));
				}
				return listOfContainers;
			}
		} catch (CouchDBException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

