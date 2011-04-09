package at.roadrunner.android.activity;

import java.util.ArrayList;

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
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Item;

public class Items extends ListActivity{

	private ProgressDialog m_ProgressDialog = null; 
    private ArrayList<Item> m_items = null;
    private ItemAdapter m_adapter;
    private Runnable viewItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		
		m_items = new ArrayList<Item>();
		m_adapter = new ItemAdapter(this, R.layout.row_item_items, m_items);
		setListAdapter(m_adapter);

		// Runnable
		viewItems = new Runnable() {
			@Override
			public void run() {
				getItems();
			}
		};
		
		Thread thread = new Thread(null, viewItems, "doitbaby");
		thread.start();
		
	    m_ProgressDialog = ProgressDialog.show(this, "Please wait...", "Retrieving data ...", true);
	}
	
	private void getItems() {
		m_items = new ArrayList<Item>();
		String items = new RequestWorker(this).getLocalItems();
		
		if (items != null) {
			try {
				JSONObject obj = new JSONObject(items);
				JSONArray arr = obj.getJSONArray("rows").getJSONObject(0).getJSONArray("value");
				
				for (int i = 0; i < arr.length(); i++) {
					m_items.add(new Item(arr.getJSONObject(i).getString("item"),  arr.getJSONObject(i).getString("timestamp")));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		}
		
		runOnUiThread(updateActivity);
	}
	
	/*
	 * runnable to communicate between the UI thread
	 */
	private Runnable updateActivity = new Runnable() {
		
        @Override
        public void run() {
            if(m_items != null && m_items.size() > 0) {
                m_adapter.notifyDataSetChanged();
                for(int i = 0; i < m_items.size(); i++) {
                	m_adapter.add(m_items.get(i));
                }
            }
            
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
    };
    
	/*
	 * ArrayAdapter
	 */
	private class ItemAdapter extends ArrayAdapter<Item> {
		
		private ArrayList<Item> m_items;
		
		public ItemAdapter(Context context, int textViewRessourceId, ArrayList<Item> items) {
			super(context, textViewRessourceId, items);
			m_items = items;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater layInf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = layInf.inflate(R.layout.row_item_items, null);
			}
			
			Item item = m_items.get(position);
			if (item != null) {
				TextView txtId = (TextView) view.findViewById(R.id.items_key);
				TextView txtTimestamp = (TextView) view.findViewById(R.id.items_timestamp);
				
				txtId.setText(getString(R.string.items_txt_key) + ": " + item.getKey());
				txtTimestamp.setText(getString(R.string.items_txt_timestamp) + ": " + item.getTimestamp());
			}

			return view;
			
		}
	}
}
