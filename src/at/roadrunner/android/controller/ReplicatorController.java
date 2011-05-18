package at.roadrunner.android.controller;

import java.util.List;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Item;

public class ReplicatorController {

	private Context _context;
	private RequestWorker _requestWorker;
	private ItemController _itemController;
	private ContainerController _containerController;
	private boolean _isContainerReplicated = false;
	
	public ReplicatorController(Context context) {
		_context = context;
		_requestWorker = new RequestWorker(_context);
		_itemController = new ItemController(_context);
		_containerController = new ContainerController(_context);
	}

	public void replicateLogsToServer() {
		_requestWorker.replicateLogsToRemoteDatabase();
		Log.e("roadrunner", "replicating logs to server");
	}
	
	public void replicateItemsFromServer() {
		// get local items
		List<Item> items = null;
		try {
			items = _itemController.getLoadedItems();
		} catch (CouchDBException e) {
			e.printStackTrace();
			return;
		}
		
		// create JSONArray
		JSONArray jsonItems = new JSONArray();
		for (Item item : items) {
			jsonItems.put(item.getKey());
		}

		// replicate the items
		_itemController.replicateLocalItems(jsonItems);
	}
	
	public void replicateContainerFromServer() {
		if (!_isContainerReplicated) {
			_isContainerReplicated = _containerController.replicateContainers();
		}
	}
}
