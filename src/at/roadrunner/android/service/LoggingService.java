package at.roadrunner.android.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import at.roadrunner.android.Config;
import at.roadrunner.android.R;
import at.roadrunner.android.activity.MonitoringController;
import at.roadrunner.android.controller.LoggingController;
public class LoggingService extends  Service{

	private NotificationManager _notificationMgr;
	private Timer _timer = new Timer();
	private LoggingController _loggingController;

	@Override
	public void onCreate() {
		super.onCreate();
		
		_loggingController = new LoggingController(this);
		_notificationMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		displayNotificationMessage(getString(R.string.logging_service_running));
		
		startService();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopService();
	}
	
	private void displayNotificationMessage(String message) {
		
		_notificationMgr.cancel(R.id.logging_service_notification_id);
		
		Notification notification = new Notification(R.drawable.ic_roadrunner_notification,
				message, System.currentTimeMillis());
		
		notification.flags = Notification.FLAG_ONGOING_EVENT;

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
               new Intent(this, MonitoringController.class), 0);
		
		notification.setLatestEventInfo(this, getString(R.string.logging_service_label),
              message, contentIntent);
		
		_notificationMgr.notify(R.id.logging_service_notification_id, notification);		
	}

	private void startService() {
		
		_timer.scheduleAtFixedRate( new TimerTask() {
			public void run() {
				_loggingController.logSensorData();
			}
		}, 0, Config.LOGGING_SERVICE_INTERAL);
	}
	
	private void stopService() {
		if (_timer != null)  {
			_timer.cancel();
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
