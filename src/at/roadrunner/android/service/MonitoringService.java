package at.roadrunner.android.service;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import at.roadrunner.android.Config;
import at.roadrunner.android.controller.MonitoringController;

public class MonitoringService extends Service {
	private Timer _timer = new Timer();
	private MonitoringController _monitoringController;

	@Override
	public void onCreate() {
		super.onCreate();
		_monitoringController = new MonitoringController(this);
		startService();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopService();
	}

	private void startService() {
		_timer.schedule(new TimerTask() {
			public void run() {
				_monitoringController.readSensorData();
			}
		}, 0, Config.MONITORING_SERVICE_INTERVAL);
	}
	
	private void stopService() {
		if (_timer != null)  {
			_timer.cancel();
		}
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
