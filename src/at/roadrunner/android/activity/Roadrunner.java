package at.roadrunner.android.activity;

import java.io.IOException;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import at.roadrunner.android.Config;
import at.roadrunner.android.R;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Log.LogType;
import at.roadrunner.android.util.AppInfo;

public class Roadrunner extends Activity {
	// Intent for scanning
	private static final String SCAN_INTENT = "com.google.zxing.client.android.SCAN";
	private static final String SCAN_PACKAGE = "com.google.zxing.client.android";

	private boolean _systemCheck = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roadrunner);
		
		ServiceController.startAllServices(this);
		
		// GPS
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		if (locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER) == false) {
			startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("SystemCheck", _systemCheck);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		_systemCheck = savedInstanceState.getBoolean("SystemCheck");
	}

	public void onScanClick(View view) {
		scan();
		openContextMenu(view);
	}
	
	public void onDeliveriesClick(View view) {
		startActivity(new Intent(this, Deliveries.class));
	}
	
	private void scan() {
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
			alertBuilder.show();
		}
	}
	
	/*
	 * Event onReplicateClick
	 */
	public void onReplicateClick(View view)  {;
		Toast.makeText(this, "Replication is running in service", Toast.LENGTH_SHORT).show();
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
				final String item = intent.getStringExtra("SCAN_RESULT");
				final RequestWorker reqWorker = new RequestWorker(this);
				
				// get layout
				AlertDialog.Builder sd = new AlertDialog.Builder(this).setTitle("Perform Action");
				View scanView = getLayoutInflater().inflate(R.layout.dialog_scan, (ViewGroup)findViewById(R.id.dialog_scan_root));
				Button btnLoad = (Button) scanView.findViewById(R.id.roadrunner_dialog_scan_load);
				Button btnUnload = (Button) scanView.findViewById(R.id.roadrunner_dialog_scan_unload);
				Button btnView = (Button) scanView.findViewById(R.id.roadrunner_dialog_scan_view);
				Button btnDeliver = (Button) scanView.findViewById(R.id.roadrunner_dialog_scan_deliver);
				sd.setView(scanView);
				
				// get container
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
				final String container = prefs.getString("transportation", Config.DEFAULT_TRANSPORTATION);

				// create AlertDialog
				final AlertDialog dialog = sd.create();
				final Context context = this;
				
				btnLoad.setOnClickListener(new OnClickListener() {
	                @Override
	                    public void onClick(View v) {
		                	// save the log
		    				reqWorker.saveLog(new JSONArray().put(item), LogType.LOAD, container, null);
		    				dialog.dismiss();
	                    }
	                });
				
				btnUnload.setOnClickListener(new OnClickListener() {
	                @Override
	                    public void onClick(View v) {
		                	// save the log
		                	reqWorker.saveLog(new JSONArray().put(item), LogType.UNLOAD, container, null);
		                	dialog.dismiss();
	                    }
	                });
				
				btnView.setOnClickListener(new OnClickListener() {
	                @Override
	                    public void onClick(View v) {
	                		dialog.dismiss();
	                    }
	                });
				
				btnDeliver.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View paramView) {
						Intent intent = new Intent(context, Signature.class);
						intent.putExtra("item", item);
						intent.putExtra("container", container);
						startActivity(intent);
						dialog.dismiss();
					}
				});
				
				// check if item is already loaded and modify the menu
				if (new RequestWorker(this).isLocalDocumentExisting(item)) {
					btnLoad.setEnabled(false);
				} else {
					btnUnload.setEnabled(false);
					btnView.setEnabled(false);
					btnDeliver.setEnabled(false);
				}
				
				dialog.show();
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
