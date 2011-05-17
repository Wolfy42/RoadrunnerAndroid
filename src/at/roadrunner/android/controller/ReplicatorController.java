package at.roadrunner.android.controller;

import android.content.Context;
import android.util.Log;
import at.roadrunner.android.couchdb.RequestWorker;

public class ReplicatorController {

	private Context _context;
	private RequestWorker _requestWorker;
	
	public ReplicatorController(Context context) {
		_context = context;
		_requestWorker = new RequestWorker(_context);
	}

	public void replicateLogsToServer() {
		_requestWorker.replicateLogsToRemoteDatabase();
		Log.e("roadrunner", "replicating logs to server");
	}
}
