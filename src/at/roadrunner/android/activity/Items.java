package at.roadrunner.android.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import at.roadrunner.android.R;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Item;

public class Items extends ListActivity {

	private ProgressDialog _progressDialog = null; 
    private ArrayList<Item> _items = null;
    private ItemAdapter _adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		
		_adapter = new ItemAdapter(this, R.layout.row_item_items);
		setListAdapter(_adapter);

		// Runnable to fill the items in a Thread
		Runnable getItems = new Runnable() {
			@Override
			public void run() {
				getItems();
			}
		};
		
		// start a new Thread to get the items
		new Thread(null, getItems, "getItemsOfCouchDB").start();
		
		// show the Progressbar
	    _progressDialog = ProgressDialog.show(this, getString(R.string.app_progress_pleasewait), getString(R.string.app_progress_retdata), true);
	}
	
	/*
	 * retrieves the items of the couchdb
	 */
	private void getItems() {
		_items = new ArrayList<Item>();
		String items = new RequestWorker(this).getLocalItems();
		
		if (items != null) {
			try {
				JSONObject obj = new JSONObject(items);
				JSONArray arr = obj.getJSONArray("rows").getJSONObject(0).getJSONArray("value");
				
				for (int i = 0; i < arr.length(); i++) {
					_items.add(new Item(arr.getJSONObject(i).getString("item"),  arr.getJSONObject(i).getString("timestamp")));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
		
		runOnUiThread(updateActivity);
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
				
				txtId.setText(getString(R.string.items_txt_key) + ": " + item.getKey());
				txtTimestamp.setText(getString(R.string.items_txt_timestamp) + ": " + item.getTimestamp());
			}

			return view;
		}
	}
	
	/*
	 * synchronizes the items with the server
	 */
	private void synchronize() {
//		int length = _items.size();
//		StringBuilder sbItems = new StringBuilder("[");
//		JSONArray jsonItems = null;
//		
//		for (int i = 0; i < length; i++) {
//			sbItems.append(_items.get(i).getKey());
//			if (i != length-1) {
//				sbItems.append(",");
//			}
//		}
//		sbItems.append("]");
//		
//		try {
//			jsonItems = new JSONArray(sbItems.toString());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		
//		new RequestWorker(this).replicateFromServer(jsonItems);
		
		Toast toast = Toast.makeText(this, "To be implemented", 3);
		toast.show();
	}
	
	/*
	 * inflate menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.items_menu, menu);
		return true;
	}
	
	 /*
     * Event OptionsMenuItemSelected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.items_menu_synchronize:
        	synchronize();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
