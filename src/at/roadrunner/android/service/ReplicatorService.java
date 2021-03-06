package at.roadrunner.android.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import at.roadrunner.android.Config;
import at.roadrunner.android.controller.ReplicatorController;

public class ReplicatorService extends  Service{

	private Timer _timer = new Timer();
	private ReplicatorController _replicatorController;

	@Override
	public void onCreate() {
		super.onCreate();
		_replicatorController = new ReplicatorController(this);
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
				_replicatorController.replicateLogsToServer();
				_replicatorController.replicateItemsFromServer();
				_replicatorController.replicateContainerFromServer();
				_replicatorController.synchronizeTime();
			}
		}, 0, Config.REPLICATOR_SERVICE_INTERAL);
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