package at.roadrunner.android.activity;

import android.app.ListActivity;

public class Items extends ListActivity {
//	private ProgressDialog _progressDialog = null;
//	private ArrayList<Item> _items = null;
//	private ItemAdapter _adapter;
//	private String _statusText;
//	private TextView _txtStatus;
//	private ItemController _controller;
//
//	private static final int DETAILS_ID = 1;
//	private static final int DELETE_ID = 2;
//
//	private static final String TAG = "Items";
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_items);
//
//		// ItemController
//		_controller = new ItemController(this);
//		
//		// Contextmenu
//		getListView().setOnCreateContextMenuListener(this);
//
//		_adapter = new ItemAdapter(this, R.layout.row_item_items);
//		setListAdapter(_adapter);
//
//		_statusText = getString(R.string.items_status_loading_local_items);
//		_txtStatus = (TextView) findViewById(R.id.items_status);
//		_txtStatus.setText(_statusText);
//
//		// Runnable to fill the items in a Thread
//		Runnable showItems = new Runnable() {
//			@Override
//			public void run() {
//				showItems();
//			}
//		};
//
//		// start a new Thread to get the items
//		new Thread(null, showItems, "getItemsOfCouchDB").start();
//
//		// show the Progressbar
//		_progressDialog = ProgressDialog.show(this,
//				getString(R.string.app_progress_pleasewait),
//				getString(R.string.app_progress_retdata), true);
//	}
//
//	/*
//	 * retrieves the items of the couchdb
//	 */
//<<<<<<< HEAD
//	private void showItems() {
//		_items = new ArrayList<Item>();
//		
//		for (Item item : _controller.getLocalItems()) {
//			_items.add(item);
//=======
//	private void synchronizeAndShowItems() {
//		try {
//			_items = new ItemController(this).getLoadedItems();
//		} catch (CouchDBNotReachableException e2) {
//			_statusText = getString(R.string.items_status_local_db_not_reachable);
//			runOnUiThread(updateActivity);
//			_items = new ArrayList<Item>();
//		}
//		
//		// synchronize
//		try {
//			synchronize();
//			_statusText =  getString(R.string.items_status_last_synchronized) + ": " + Config.DATE_FORMAT.format(new Date().getTime());
//		} catch (CouchDBNotReachableException e1) {
//			_statusText = getString(R.string.items_status_remote_db_not_reachable);
//>>>>>>> branch 'refs/heads/develop' of git@github.com:Wolfy42/RoadrunnerAndroid
//		}
//
//		runOnUiThread(updateActivity);
//	}
//
//	/*
//	 * fill the adapter with the items of the list
//	 */
//	private final Runnable updateActivity = new Runnable() {
//
//		@Override
//		public void run() {
//			if (_items != null && _items.size() > 0) {
//				_adapter.notifyDataSetChanged();
//				for (int i = 0; i < _items.size(); i++) {
//					_adapter.add(_items.get(i));
//				}
//			}
//
//			_progressDialog.dismiss();
//			_adapter.notifyDataSetChanged();
//			_txtStatus.setText(R.string.items_status_local_items_loaded);
//		}
//	};
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView,
//	 * android.view.View, int, long)
//	 */
//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		Log.v(TAG, String.valueOf(position));
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
//	 */
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		AdapterView.AdapterContextMenuInfo info = null;
//
//		try {
//			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//		} catch (ClassCastException e) {
//			e.printStackTrace();
//		}
//
//		switch (item.getItemId()) {
//			case DETAILS_ID:
//				Log.v(TAG, String.valueOf(info.id));
//				showDetails();
//				return true;
//			case DELETE_ID:
//				Log.v(TAG, String.valueOf(info.id));
//				deleteItem();
//				return true;
//			default:
//				return super.onContextItemSelected(item);
//		}
//	}
//
//	private void showDetails() {
//		Toast toast = Toast.makeText(getApplicationContext(), "details", 3);
//		toast.show();
//	}
//
//	private void deleteItem() {
//		Toast toast = Toast.makeText(getApplicationContext(), "delete", 3);
//		toast.show();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu,
//	 * android.view.View, android.view.ContextMenu.ContextMenuInfo)
//	 */
//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v,
//			ContextMenuInfo menuInfo) {
//		AdapterView.AdapterContextMenuInfo info;
//		try {
//			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//		} catch (ClassCastException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		Item item = (Item) getListAdapter().getItem(info.position);
//		menu.setHeaderTitle(item.getKey());
//		menu.add(0, DETAILS_ID, 0, R.string.items_context_details_item);
//		menu.add(0, DELETE_ID, 0, R.string.items_context_delete_item);
//	}
//
//	/*
//	 * ItemAdapter
//	 */
//	private class ItemAdapter extends ArrayAdapter<Item> {
//
//		public ItemAdapter(Context context, int textViewResourceId) {
//			super(context, textViewResourceId);
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			View view = convertView;
//			if (view == null) {
//				LayoutInflater layInf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				view = layInf.inflate(R.layout.row_item_items, null);
//			}
//
//			Item item = _items.get(position);
//			if (item != null) {
//				TextView txtId = (TextView) view.findViewById(R.id.items_key);
//				TextView txtTimestamp = (TextView) view
//						.findViewById(R.id.items_timestamp);
//				TextView txtName = (TextView) view
//						.findViewById(R.id.items_name);
//
//				txtId.setText(getString(R.string.items_txt_key) + ": "
//						+ item.getKey());
//
//				// convert the timestamp into a date
//				Date date = new Date(item.getTimestamp());
//				txtTimestamp.setText(getString(R.string.items_txt_date) + ": "
//						+ Config.DATE_FORMAT.format(date));
//				txtName.setText(item.getName());
//			}
//
//			return view;
//		}
//	}
}
