package at.roadrunner.android.activity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import at.roadrunner.android.Config;
import at.roadrunner.android.R;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Address;
import at.roadrunner.android.model.Delivery;
import at.roadrunner.android.model.HttpSensor;
import at.roadrunner.android.model.Item;
import at.roadrunner.android.model.Log.LogType;
import at.roadrunner.android.sensor.SensorConnectionFactory;
import at.roadrunner.android.sensor.SensorType;
import at.roadrunner.android.service.MonitoringService;

public class Monitoring extends Activity {

	@SuppressWarnings("unused")
	private static final String TAG = "Monitoring";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitoring);

		Delivery delivery = createDelivery();

		// start Monitoring Service
		Intent monitoringSevice = new Intent(this, MonitoringService.class);
		monitoringSevice.putExtra("delivery", delivery);
		startService(monitoringSevice);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// stop Monitoring Service
		stopService(new Intent(Monitoring.this, MonitoringService.class));
	}

	private Delivery createDelivery() {
		// TODO: Trackingnumber should be filled dynamically
		Delivery delivery = new Delivery(4711);

		// TODO: Address should be filled dynamically
		Address destination = new Address("Stefan Balle", "Balle AG",
				"Gartenweg 4", "6714", "Nüziders", "Austria");
		delivery.setDestination(destination);

		// TODO: constituent (Auftragsgeber) shoulb be filled dynamically
		delivery.setConstituent("FH Vorarlberg");

		// add items
		addItems(delivery);

		// add sensors
		addSensors(delivery);

		return delivery;
	}

	/*
	 * - replicate the local items with the server items - load items of local
	 * DB and create Items - add this Items to the Delivery
	 */
	// FIXME: CodeDuplication with Items.java
	// FIXME: oh man! optimize this ugly hack pls! :D
	private void addItems(Delivery delivery) {
		String loadedItems = null;
		ArrayList<Item> items = new ArrayList<Item>();

		try {
			loadedItems = new RequestWorker(this).getLoadedItems();
		} catch (CouchDBException e) {
			e.printStackTrace();
		}

		if (loadedItems != null) {
			try {
				JSONObject result = new JSONObject(loadedItems);

				JSONArray rows = result.getJSONArray("rows");
				JSONObject row;
				JSONArray value;
				String loadedState = LogType.LOAD.name();

				for (int i = 0; i < rows.length(); i++) {
					row = rows.getJSONObject(i);
					value = row.getJSONArray("value");
					if (loadedState.equals(value.getString(0))) {
						items.add(new Item(row.getString("key"), value
								.getLong(1)));
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

		// replicate
		try {
			JSONArray jsonItems = new JSONArray();

			for (Item item : items) {
				jsonItems.put(item.getKey());
			}

			// replicate
			new RequestWorker(this).replicateFromServer(jsonItems);
			// get replicated items
			String localItems = new RequestWorker(this).getReplicatedItems();

			// addItemInformation
			if (localItems != null) {
				try {
					JSONObject obj = new JSONObject(localItems);
					JSONArray arr = obj.getJSONArray("rows");

					for (int i = 0; i < arr.length(); i++) {
						for (int j = 0; j < items.size(); j++) {
							if (items
									.get(j)
									.getKey()
									.equals(arr.getJSONObject(i)
											.getString("id"))) {
								items.get(j)
										.setName(
												arr.getJSONObject(i).getString(
														"value"));
								break;
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (CouchDBException e) {
			e.printStackTrace();
		}

		// add the items to the delivery
		for (Item item : items) {
			delivery.addItem(item);
		}
	}

	/*
	 * - read the sensors of the selected (on the phone) transportation - add
	 * the Sensors to the delivery
	 */
	private void addSensors(Delivery delivery) {
		// TODO: Load the sensors from the Server (with the selected
		// transportation on the phone)
		try {
			delivery.addSensor(new HttpSensor(new URL(
					Config.TEMPERATUR_SENSOR_URL_1), SensorType.Temperature,
					new SensorConnectionFactory()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
