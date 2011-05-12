package at.roadrunner.android.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import at.roadrunner.android.Config;
import at.roadrunner.android.R;
import at.roadrunner.android.model.Item;
import at.roadrunner.android.model.ItemController;

public class ReplicationService extends Service {

	private NotificationManager _nM;
	private int NOTIFICATION = R.string.local_service_started;
	private static final String TAG = "ReplicationService";
	private ItemController _controller;
	
	// Timer / TimerTask 
	private Timer _timer;
	private TimerTask _timerTask = new TimerTask() { 
        public void run() { 
        	replicate(); 
        }
	}; 
	
	public class LocalBinder extends Binder {
		ReplicationService getService() {
			return ReplicationService.this;
		}
	}
	
	// This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder _binder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return _binder;
	}
	
	@Override
    public void onCreate() {
		_nM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

		//TODO: FIXME
        //showNotification();
    }

	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);
        
        // set the controller object
        _controller = new ItemController(this);
        
        if (_controller != null) {
	        _timer = new Timer();
	        _timer.schedule(_timerTask,0,Config.SERVICE_REPLICATION_INTERVAL);
        }
        
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
    	_nM.cancel(NOTIFICATION);

    	_timerTask.cancel();
    	_timer.cancel();
    	
        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }
    
	private void replicate() {
		Log.v(TAG, "replicate ....");
		
		// get local items
		List<Item> items = _controller.getLocalItems();
		
		// create JSONArray
		JSONArray jsonItems = new JSONArray();
		for (Item item : items) {
			jsonItems.put(item.getKey());
		}

		// replicate the items
		_controller.replicateLocalItems(jsonItems);
		
		Log.v(TAG, "replicated!");
	} 
    
    private void showNotification() {
		// In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_roadrunner_notification, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        //PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        //        new Intent(this, MonitoringController.class), 0);
        
        // Set the info for the views that show in the notification panel.
        //notification.setLatestEventInfo(this, getText(R.string.local_service_label),
        //               text, contentIntent);

        // Send the notification.
        _nM.notify(NOTIFICATION, notification);	
	}
}
