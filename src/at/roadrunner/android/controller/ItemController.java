package at.roadrunner.android.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import at.roadrunner.android.couchdb.CouchDBException.CouchDBNotReachableException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Item;
import at.roadrunner.android.model.Log.LogType;

public class ItemController {
	
	private Context _context;
	
	public ItemController(Context context) {
		_context = context;
	}
	
	public ArrayList<Item> getLoadedItems() throws CouchDBNotReachableException  {
		ArrayList<Item> items = new ArrayList<Item>();
		String loadedItems = null;
		loadedItems = new RequestWorker(_context).getLoadedItems();
		
		if (loadedItems != null) {
			try {
				JSONObject result = new JSONObject(loadedItems);
				
				JSONArray rows = result.getJSONArray("rows");
				JSONObject row;
				JSONArray value;
				String loadedState = LogType.LOAD.name();
				
				for (int i=0; i < rows.length(); i++)  {
					row = rows.getJSONObject(i);
					value = row.getJSONArray("value");
					if (loadedState.equals(value.getString(0)))  {
						items.add(new Item(row.getString("key"), value.getLong(1)));
					}
				}
				Collections.sort(items, new Comparator<Item>()  {
					@Override
					public int compare(Item item1, Item item2) {
						return new Long(item1.getTimestamp()).compareTo(item2.getTimestamp());
					}
				});

			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
		return items;
	}
}
