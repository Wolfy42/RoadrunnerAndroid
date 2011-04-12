package at.roadrunner.android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import at.roadrunner.android.R;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Log.LogType;
import at.roadrunner.android.setup.CouchDB;
import at.roadrunner.android.util.AppInfo;

public class Roadrunner extends Activity {
	// Intent for scanning
	private static final String SCAN_INTENT = "com.google.zxing.client.android.SCAN";
	private static final String SCAN_PACKAGE = "com.google.zxing.client.android";
	
	// Intent for CouchDB
	private static final String COUCHDB_INTENT = "com.couchone.couchdb/.CouchFutonActivity";
	private static final String COUCHDB_PACKAGE = "com.couchone.couchdb";
	private static final String COUCHDB_SERVICE = "com.couchone.couchdb.CouchService";
	
	private Context _context = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roadrunner);
		_context = this;
		
		checkSystemState();
		
	}

	/*
	 * checks if the couchdb is installed and links to the market if not
	 * checks if couchdb is running and starts it if not
	 */
	private void checkSystemState() {
		// is CouchDB installed and running?
		if (AppInfo.isAppInstalled(_context, COUCHDB_PACKAGE) ) {
			if (AppInfo.isAppRunning(_context, COUCHDB_SERVICE) ) {
				// check if roadrunner db exists or create it
				new CouchDB().insertRoadrunnerUser();
				Log.v("test", "running");
			} else {
				//TODO: start the activity
				Log.v("test", "not running");
			}
		} else {
			// show dialog
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(R.string.roadrunner_dialog_missingCouchDB);
			alertBuilder.setCancelable(false);
			alertBuilder.setPositiveButton(R.string.app_dialog_yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + COUCHDB_PACKAGE));
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
			Log.v("test", "not installed");
		}
	}

	/*
	 * Event onScanClick
	 */
	public void onScanClick(View view) {
		if (AppInfo.isIntentAvailable(this, SCAN_INTENT)) {
			Intent intent = new Intent(SCAN_INTENT);
			intent.setPackage(SCAN_PACKAGE);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

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
				String contents = intent.getStringExtra("SCAN_RESULT");
				@SuppressWarnings("unused")
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

				Toast toast = Toast.makeText(getApplicationContext(), contents, 3);
				toast.show();
				
				new RequestWorker(this).saveLog(contents, LogType.LOAD);
				
			} else if (resultCode == RESULT_CANCELED) {

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
