package at.roadrunner.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import at.roadrunner.android.R;
import at.roadrunner.android.service.MonitoringService;

public class MonitoringController extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_monitoring_controller);
	}
	
	public void onStartClick(View v) {
		startService(new Intent(MonitoringController.this, MonitoringService.class));
	}
	
	public void onStopClick(View v) {
		stopService(new Intent(MonitoringController.this, MonitoringService.class));
	}
}
