package at.roadrunner.android.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Item;
import at.roadrunner.android.model.Log.LogType;

public class ItemController {
	private Context _context;
	
	public ItemController(Context context) {
		_context = context;
	}
	
	/**
	 * 
	 * @return List<Item> all loaded local items
	 */
	public List<Item> getLocalItems() {
		String loadedItems = null;
		List<Item> items = new ArrayList<Item>();

		try {
			loadedItems = new RequestWorker(_context).getLoadedItems();
		} catch (CouchDBException e) {
			e.printStackTrace();
		}

		if (loadedItems != null) {
			try {
				JSONObject result = new JSONObject(loadedItems);

				android.util.Log.v("blubb", result.toString());
				
				JSONArray rows = result.getJSONArray("rows");
				JSONObject row;
				JSONArray value;
				String loadedState = LogType.LOAD.name();

				for (int i = 0; i < rows.length(); i++) {
					row = rows.getJSONObject(i);
					value = row.getJSONArray("value");
					if (loadedState.equals(value.getString(0))) {
						Item item = new Item(row.getString("key"), value.getLong(1));
						
						// fill the other fields if available
						//item.setName(row.getString("name"));
						
						items.add(item);
					}
				}
				Collections.sort(items, new Comparator<Item>() {
					@Override
					public int compare(Item item1, Item item2) {
						return new Long(item1.getTimestamp()).compareTo(item2
								.getTimestamp());
					}
				});

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return items;
	}
	
	/**
	 * 
	 * @param items JSONArray with all items to be replicated
	 */
	public void replicateLocalItems(JSONArray items) {
		try {
			new RequestWorker(_context).replicateFromServer(items);
		} catch (CouchDBException e) {
			e.printStackTrace();
		}
	}
}
