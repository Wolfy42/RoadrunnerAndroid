package at.roadrunner.android.couchdb;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import at.roadrunner.android.Config;

public class CouchDBService {

	private static ServiceConnection _connection = new CouchDBConnection();

	private static boolean _isBound = false;

	public static void startCouchDB(Context context) {
		context.bindService(new Intent(Config.COUCH_SERVICE), _connection,
				Context.BIND_AUTO_CREATE);
		_isBound = true;
	}

	public static void stopCouchDB(Context context) {
		if (_isBound) {
			context.unbindService(_connection);
			_isBound = false;
		}
		context.stopService(new Intent(Config.COUCH_SERVICE));
	}

	public static void restartCouchDB(Context context) {
		stopCouchDB(context);
		startCouchDB(context);
	}

	private static class CouchDBConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	}
}
