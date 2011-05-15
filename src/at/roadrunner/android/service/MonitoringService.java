package at.roadrunner.android.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import at.roadrunner.android.Config;

public class MonitoringService extends Service {

	private Timer _timer = new Timer();

	@Override
	public void onCreate() {
		super.onCreate();
		startService();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopService();
	}

	private void startService() {
		
		_timer.scheduleAtFixedRate( new TimerTask() {
			public void run() {
				Log.e("roadrunner", "Should read sensor data");
			}
		}, 0, Config.MONITORING_SERVICE_INTERVAL);
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
