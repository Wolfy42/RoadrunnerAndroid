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
import at.roadrunner.android.controllers.ContainerController;
import at.roadrunner.android.controllers.ItemController;
import at.roadrunner.android.model.Item;

public class ReplicationService extends Service {

	private NotificationManager _nM;
	private int NOTIFICATION = R.string.local_service_started;
	private static final String TAG = "ReplicationService";
	private ItemController _itemController;
	private ContainerController _containerController;
	
	// Timer / TimerTask 
	private Timer _timerItems;
	private TimerTask _timerTaskItems = new TimerTask() { 
        public void run() { 
        	replicateItems(); 
        }
	}; 
	
	// Timer / TimerTask 
	private Timer _timerContainer;
	private TimerTask _timerTaskContainer = new TimerTask() { 
        public void run() { 
        	replicateContainers(); 
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
        _itemController = new ItemController(this);
        _containerController = new ContainerController(this);
        
        // replicate
	    _timerItems = new Timer();
	    _timerItems.schedule(_timerTaskItems,0,Config.SERVICE_REPLICATION_ITEM_INTERVAL);
        
        // replicate container once at start
	    _timerContainer = new Timer();
	    _timerContainer .schedule(_timerTaskContainer,0,Config.SERVICE_REPLICATION_CONTAINER_INTERVAL);
	    
	    
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
    	_nM.cancel(NOTIFICATION);

    	_timerTaskItems.cancel();
    	_timerItems.cancel();
    	
    	_timerTaskContainer.cancel();
    	_timerContainer.cancel();
    	
        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }
    
	private void replicateItems() {
		Log.v(TAG, "replicate items....");
		
		// get local items
		List<Item> items = _itemController.getLocalItems();
		
		// create JSONArray
		JSONArray jsonItems = new JSONArray();
		for (Item item : items) {
			jsonItems.put(item.getKey());
		}

		// replicate the items
		_itemController.replicateLocalItems(jsonItems);
		
		Log.v(TAG, "items replicated!");
	} 
	
	private void replicateContainers() {
		Log.v(TAG, "replicate transportation....");

		if (_containerController.replicateContainers()) {
			
			
			_timerTaskContainer.cancel();
	    	_timerContainer.cancel();
		}
		
		Log.v(TAG, "transportation replicated!");
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
