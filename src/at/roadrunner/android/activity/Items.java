package at.roadrunner.android.activity;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import at.roadrunner.android.Config;
import at.roadrunner.android.R;
import at.roadrunner.android.couchdb.CouchDBException.CouchDBNotReachableException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Item;

public class Items extends ListActivity {
	private ProgressDialog _progressDialog = null; 
    private ArrayList<Item> _items = null;
    private ItemAdapter _adapter;
    private String _statusText;
    private TextView _txtStatus;
   
    private static final int DETAILS_ID = 1;
    private static final int DELETE_ID = 2;
    
    @SuppressWarnings("unused")
	private static final String TAG = "Items";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
	
		// Kontextmenu
		getListView().setOnCreateContextMenuListener(this);
		
		_adapter = new ItemAdapter(this, R.layout.row_item_items);
		setListAdapter(_adapter);

		_statusText = getString(R.string.items_status_synchronize);
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
		String loadedItems = null;
		
		try {
			loadedItems = new RequestWorker(this).getLoadedItems();
		} catch (CouchDBNotReachableException e) {
			_statusText = getString(R.string.items_status_local_db_not_reachable);
			runOnUiThread(updateActivity);
			return;
		}
		
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
		try {
			synchronize();
			_statusText =  getString(R.string.items_status_last_synchronized) + ": " + Config.DATE_FORMAT.format(new Date().getTime());
		} catch (CouchDBNotReachableException e1) {
			_statusText = getString(R.string.items_status_remote_db_not_reachable);
		}

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
	private void synchronize() throws CouchDBNotReachableException {
		JSONArray jsonItems = new JSONArray();
		
		for (Item item : _items) {
			jsonItems.put(item.getKey());
		}
		
		new RequestWorker(this).replicateFromServer(jsonItems);
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
     * (non-Javadoc)
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.v(TAG, String.valueOf(position));
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = null;
		
		try {
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		} catch (ClassCastException e) {
        	e.printStackTrace();
        }
		
		switch (item.getItemId()) {
		case DETAILS_ID:
			Log.v(TAG, String.valueOf(info.id));
			showDetails();
			return true;
		case DELETE_ID:
			Log.v(TAG, String.valueOf(info.id));
			deleteItem();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void showDetails() {
		Toast toast = Toast.makeText(getApplicationContext(), "details", 3);
		toast.show();
	}
	
	private void deleteItem() {
		Toast toast = Toast.makeText(getApplicationContext(), "delete", 3);
		toast.show();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info;
        try {
        	info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
        	e.printStackTrace();
            return;
        }
        
        Item item = (Item) getListAdapter().getItem(info.position);
		menu.setHeaderTitle(item.getKey());
		menu.add(0, DETAILS_ID, 0, R.string.items_context_details_item);
		menu.add(0, DELETE_ID, 0, R.string.items_context_delete_item);
	}
    
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
				Date date = new Date(item.getTimestamp());
				txtTimestamp.setText(getString(R.string.items_txt_date) + ": " + Config.DATE_FORMAT.format(date));
				txtName.setText(item.getName());
			}

			return view;
		}
	}
}
