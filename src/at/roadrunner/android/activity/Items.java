package at.roadrunner.android.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import at.roadrunner.android.R;
import at.roadrunner.android.couchdb.CouchDBException.CouchDBNotReachableException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Item;

public class Items extends ListActivity {

	private ProgressDialog _progressDialog = null; 
    private ArrayList<Item> _items = null;
    private ItemAdapter _adapter;
    private String _statusText = "Synchronizing with Server...";
    private TextView _txtStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		
		_adapter = new ItemAdapter(this, R.layout.row_item_items);
		setListAdapter(_adapter);

		_txtStatus = (TextView) findViewById(R.id.items_status);
		_txtStatus.setText(_statusText);
		
		// Runnable to fill the items in a Thread
		Runnable showItems = new Runnable() {
			@Override
			public void run() {
				synchronizeAndShowItems();
			}
		};
		
		// start a new Thread to get the items
		new Thread(null, showItems, "getItemsOfCouchDB").start();
		
		// show the Progressbar
	    _progressDialog = ProgressDialog.show(this, getString(R.string.app_progress_pleasewait), getString(R.string.app_progress_retdata), true);
	}
	
	/*
	 * retrieves the items of the couchdb
	 */
	private void synchronizeAndShowItems() {
		_items = new ArrayList<Item>();
		String loadedItems = new RequestWorker(this).getLoadedItems();
		
		if (loadedItems != null) {
			try {
				JSONObject obj = new JSONObject(loadedItems);
				JSONArray arr = obj.getJSONArray("rows").getJSONObject(0).getJSONArray("value");
				
				for (int i = 0; i < arr.length(); i++) {
					_items.add(new Item(arr.getJSONObject(i).getString("item"),  arr.getJSONObject(i).getLong("timestamp")));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
		
		// synchronize
		synchronize();
		
		// addItemInformation
		String localItems = new RequestWorker(this).getReplicatedItems();
		if (localItems != null) {
			try {
				JSONObject obj = new JSONObject(localItems);
				JSONArray arr = obj.getJSONArray("rows");
				
				for (int i = 0; i < arr.length(); i++) {
					for (int j = 0; j < _items.size(); j++) {
						if (_items.get(j).getKey().equals(arr.getJSONObject(i).getString("id")) ) {
							_items.get(j).setName(arr.getJSONObject(i).getString("value"));
							break;
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		runOnUiThread(updateActivity);
	}
	
	/*
	 * synchronizes the items with the server
	 */
	private void synchronize() {
		JSONArray jsonItems = new JSONArray();
		
		for (Item item : _items) {
			jsonItems.put(item.getKey());
		}
		
		try {
			new RequestWorker(this).replicateFromServer(jsonItems);
		} catch (CouchDBNotReachableException e) {
			_statusText = "CouchDB not reachable";
		}
	}
	
	/*
	 * fill the adapter with the items of the list
	 */
	private Runnable updateActivity = new Runnable() {
		
        @Override
        public void run() {
            if(_items != null && _items.size() > 0) {
                _adapter.notifyDataSetChanged();
                for(int i = 0; i < _items.size(); i++) {
                	_adapter.add(_items.get(i));
                }
            }
            
            _progressDialog.dismiss();
            _adapter.notifyDataSetChanged();
            _txtStatus.setText(_statusText);
        }
    };
    
	/*
	 * ItemAdapter
	 */
	private class ItemAdapter extends ArrayAdapter<Item> {
		
		public ItemAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater layInf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = layInf.inflate(R.layout.row_item_items, null);
			}
			
			Item item = _items.get(position);
			if (item != null) {
				TextView txtId = (TextView) view.findViewById(R.id.items_key);
				TextView txtTimestamp = (TextView) view.findViewById(R.id.items_timestamp);
				TextView txtName = (TextView) view.findViewById(R.id.items_name);
				
				txtId.setText(getString(R.string.items_txt_key) + ": " + item.getKey());
				
				// convert the timestamp into a date
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				Date date = new Date(item.getTimestamp());
				txtTimestamp.setText(getString(R.string.items_txt_date) + ": " + sdf.format(date));
				
				txtName.setText(item.getName());
			}

			return view;
		}
	}
}
