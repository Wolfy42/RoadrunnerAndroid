package at.roadrunner.android.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Delivery;
import at.roadrunner.android.model.Item;
import at.roadrunner.android.model.Address;

public class DeliveryController {

	private Context _context;
	
	public DeliveryController(Context context) {
		_context = context;
	}

	public ArrayList<Delivery> getDeliveries() {
		ArrayList<Delivery> _deliveries = new ArrayList<Delivery>();
		JSONArray arr = new RequestWorker(_context).getDeliveries();
		
		JSONObject obj;
		Delivery delivery = null;
		try {
			for (int i=0; i < arr.length()-1; i++)  {
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
				
				JSONObject item = obj.getJSONObject("doc");
				delivery.getItems().add(new Item(
						item.getString("name"), 
						Double.parseDouble(item.getString("tempMin")), 
						Double.parseDouble(item.getString("tempMax"))));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return _deliveries;
	}
}

