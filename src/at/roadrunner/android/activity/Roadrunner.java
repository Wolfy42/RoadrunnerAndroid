package at.roadrunner.android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import at.roadrunner.android.R;
import at.roadrunner.android.couchdb.CouchDBService;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Log.LogType;
import at.roadrunner.android.service.MonitoringService;
import at.roadrunner.android.util.AppInfo;

public class Roadrunner extends Activity {
	// Intent for scanning
	private static final String SCAN_INTENT = "com.google.zxing.client.android.SCAN";
	private static final String SCAN_PACKAGE = "com.google.zxing.client.android";
	
	private LogType _status;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roadrunner);
		
		// start CouchDB Service
		CouchDBService.startCouchDB(this);
		// start Monitoring Service
		startService(new Intent(Roadrunner.this, MonitoringService.class));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		CouchDBService.stopCouchDB(this);
	}
	
	public void onLoadClick(View view) {
		scanItem(LogType.LOAD);
	}
	
	public void onUnloadClick(View view) {
		scanItem(LogType.UNLOAD);
	}
	
	private void scanItem(LogType status) {
		if (AppInfo.isIntentAvailable(this, SCAN_INTENT)) {
			Intent intent = new Intent(SCAN_INTENT);
			intent.setPackage(SCAN_PACKAGE);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			
			_status = status;
			
			startActivityForResult(intent, 0);
		} else {
			// show dialog
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(R.string.roadrunner_dialog_missingScanner);
			alertBuilder.setCancelable(false);
			alertBuilder.setPositiveButton(R.string.app_dialog_yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + SCAN_PACKAGE));
					startActivity(intent);
				}
			});
			alertBuilder.setNegativeButton(R.string.app_dialog_no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			AlertDialog alert = alertBuilder.create();
			
			alert.show();
		}
	}

	/*
	 * Event onReplicateClick
	 */
	public void onReplicateClick(View view)  {
		new RequestWorker(this).replicate();
	}

	/*
	 * Event onItemsClick
	 */
	public void onItemsClick(View view) {
		startActivity(new Intent(this, Items.class));
	}
	
	/*
	 * onActivityResult
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String item = intent.getStringExtra("SCAN_RESULT");
				String msg = "The item: '" + item + "' was successfully ";
				
				if (_status == LogType.LOAD) {
					msg += "loaded.";
					new RequestWorker(this).saveLog(item, LogType.LOAD);
				} else {
					msg += "unloaded.";
					new RequestWorker(this).saveLog(item, LogType.UNLOAD);
				}
				
				Toast toast = Toast.makeText(getApplicationContext(), msg, 3);
				toast.show();
			} 
		}
	}

	/*
	 * inflate menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.roadrunner_menu, menu);
		return true;
	}

    /*
     * Event OptionsMenuItemSelected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.roadrunner_menu_settings:
        	startActivity(new Intent(this, Settings.class));
            return true;
        case R.id.roadrunner_menu_info:
        	startActivity(new Intent(this, Info.class));
            return true;
        case R.id.roadrunner_menu_systemtest:
        	startActivity(new Intent(this, SystemTest.class));
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
