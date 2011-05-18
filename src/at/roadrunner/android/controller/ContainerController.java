package at.roadrunner.android.controller;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;

public class ContainerController {

	private Context _context;
	
	public ContainerController(Context context) {
		_context = context;
	}

	public boolean replicateContainers() {
		return new RequestWorker(_context).replicateContainers();
	}
	
	public ArrayList<String> getContainers() {
		try {
			String containers = new RequestWorker(_context).getContainers();
			Log.v("blubb", containers);
		} catch (CouchDBException e) {
			e.printStackTrace();
		}
		return null;
	}
}

