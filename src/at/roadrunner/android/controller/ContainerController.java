package at.roadrunner.android.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;

public class ContainerController {

	private Context _context;
	
	public ContainerController(Context context) {
		_context = context;
	}

	public boolean replicateSelectedContainer() {
		return new RequestWorker(_context).replicateSelectedContainer();
	}
	
	public ArrayList<JSONObject> getContainers() {
		ArrayList<JSONObject> listOfContainers = null;
		try {
			String containers = new RequestWorker(_context).getContainerNames();
			if (containers != null) {
				listOfContainers = new ArrayList<JSONObject>();
				JSONArray array = new JSONObject(containers).getJSONArray("rows");
					
				for (int i=0; i < array.length(); i++)  {
					listOfContainers.add(array.getJSONObject(i).getJSONObject("value"));
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

