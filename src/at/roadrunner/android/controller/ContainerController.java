package at.roadrunner.android.controller;

import android.content.Context;
import at.roadrunner.android.couchdb.RequestWorker;

public class ContainerController {

	private Context _context;
	
	public ContainerController(Context context) {
		_context = context;
	}

	public boolean replicateContainers() {
		return new RequestWorker(_context).replicateContainers();
	}
}

