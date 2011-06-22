package at.roadrunner.android.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Delivery;
import at.roadrunner.android.model.Item;
import at.roadrunner.android.model.Address;

public class DeliveryController {

	private Context _context;
	private ItemController _itemController;
	
	public DeliveryController(Context context) {
		_context = context;
		_itemController = new ItemController(_context);
	}

	public ArrayList<Delivery> getDeliveries() {
		ArrayList<Delivery> _deliveries = new ArrayList<Delivery>();
		JSONArray arr = new RequestWorker(_context).getDeliveries();
		
		JSONObject obj;
		Delivery delivery = null;
		try {
			for (int i=0; i < arr.length(); i++)  {
				obj = arr.getJSONObject(i);
				if (delivery == null || !delivery.getTrackingNumber().equals(obj.getString("id")))  {
					delivery = new Delivery(obj.getString("id"));
					
					JSONObject from = obj.getJSONObject("value").getJSONObject("from");
					Address fromAdr = new Address(from.getString("name"), from.getString("street"), from.getString("zip"), from.getString("city"), from.getString("country"));
					JSONObject to = obj.getJSONObject("value").getJSONObject("to");
					Address toAdr = new Address(to.getString("name"), to.getString("street"), to.getString("zip"), to.getString("city"), to.getString("country"));
					
					delivery.setAddressFrom(fromAdr);
					delivery.setAddressTo(toAdr);
					
					_deliveries.add(delivery);
				}
				if (!obj.isNull("doc"))  {
					JSONObject item = obj.getJSONObject("doc");
					try {
						delivery.getItems().add(new Item(
								item.getString("name"), 
								Double.parseDouble(item.getString("tempMin")), 
								Double.parseDouble(item.getString("tempMax")),
								_itemController.isItemLoaded(obj.getJSONObject("value").getString("_id"))));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (CouchDBException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		ArrayList<Delivery> loadedDeliveries = new ArrayList<Delivery>();
		for (Delivery del : _deliveries)  {
			boolean isLoaded = false;
			for (Item item : del.getItems())  {
				if (item.isLoaded())  {
					isLoaded = true;
				}
			}
			if (isLoaded)  {
				loadedDeliveries.add(del);
			}
		}
		
		// sort the delivery-items
		for (Delivery del: loadedDeliveries)  {
			Collections.sort(del.getItems(), new Comparator<Item>()  {
				@Override
				public int compare(Item object1, Item object2) {
					int obj1 = object1.isLoaded() ? 0 : 1;
					int obj2 = object2.isLoaded() ? 0 : 1;
					return Integer.valueOf(obj1).compareTo(obj2);
				}
			});
		}
		
		return loadedDeliveries;
	}
}

