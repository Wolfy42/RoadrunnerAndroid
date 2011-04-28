package at.roadrunner.android.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import at.roadrunner.android.Config;
import at.roadrunner.android.R;
import at.roadrunner.android.activity.MonitoringController;

public class MonitoringService extends Service {

	private NotificationManager _nM;
	private volatile Thread _thread;
	private Handler _handler;
	private int NOTIFICATION = R.string.local_service_started;
	private static final String TAG = "MonitoringService";
	
	public class LocalBinder extends Binder {
		MonitoringService getService() {
			return MonitoringService.this;
		}
	}
	
	@Override
    public void onCreate() {
		_nM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        showNotification();
        
        //startThread();
    }

	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        Log.v(TAG, "onStartCommand");
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
    	_nM.cancel(NOTIFICATION);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
        
        stopThread();
    }

	// This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder _binder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return _binder;
	}
	
	private void showNotification() {
		// In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_roadrunner_notification, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MonitoringController.class), 0);
        
        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.local_service_label),
                       text, contentIntent);

        // Send the notification.
        _nM.notify(NOTIFICATION, notification);	
	}
	
	@SuppressWarnings("unused")
	private synchronized void startThread() {
		_handler = new Handler();
		if (_thread == null) {
			_thread = new Thread(new Runnable() {
				@Override
				public void run() {
					logData();
					_handler.postDelayed(this, Config.MONITORING_SERVICE_INTERVAL);
				}
			});
			_thread.start();
		}
	}
	
	private synchronized void stopThread() {
		if (_thread != null) {
			Thread moribund = _thread;
			_thread = null;
			moribund.interrupt();
		}
	}

	// log the data (gps pos, sensor values, ...)
	private void logData() {
		Log.v(TAG, "fick dich");
	}
}
