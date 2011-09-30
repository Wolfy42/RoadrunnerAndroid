package at.roadrunner.android.couchdb;

import android.content.Context;
import android.content.Intent;
import at.roadrunner.android.Config;

public class CouchDBService {
	
	public static void startCouchDB(Context context)  {
		context.startService(new Intent(Config.COUCH_SERVICE));
	}
	
	public static void stopCouchDB(Context context)  {
		context.stopService(new Intent(Config.COUCH_SERVICE));
	}

	public static void restartCouchDB(Context context) {
		stopCouchDB(context);
		startCouchDB(context);
	}
}
