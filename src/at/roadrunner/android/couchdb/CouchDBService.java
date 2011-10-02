package at.roadrunner.android.couchdb;

import java.io.IOException;

import android.content.Context;
import android.content.ServiceConnection;
import android.util.Log;

import com.couchbase.android.CouchbaseMobile;
import com.couchbase.android.ICouchbaseDelegate;

public class CouchDBService {
	
	private ServiceConnection _couchServiceConnection;
	
	public void startCouchDB(Context context)  {
		 CouchbaseMobile couch = new CouchbaseMobile(context, mDelegate);
		 try {
			couch.copyIniFile("couch.ini");
		} catch (IOException e) {
			Log.e("roadrunner", "copy ini failed", e);
		}
		 
         _couchServiceConnection = couch.startCouchbase();
         Log.i("roadrunner", "call to start DB");
	}
	
	public void stopCouchDB(Context context)  {
		if (_couchServiceConnection != null)  {
			context.unbindService(_couchServiceConnection);
		}
		_couchServiceConnection = null;
	}

	public void restartCouchDB(Context context) {
		stopCouchDB(context);
		startCouchDB(context);
	}
	
	private final ICouchbaseDelegate mDelegate = new ICouchbaseDelegate() {
	    @Override
	    public void couchbaseStarted(String host, int port) {
	    	Log.i("roadrunner", "CouchDB started on "+host+" at port "+port);
	    }

	    @Override
	    public void exit(String error) {
	    	Log.i("roadrunner", "CouchDB stopped");
	    }
	};
}
