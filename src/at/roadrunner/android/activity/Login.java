package at.roadrunner.android.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import at.roadrunner.android.R;
import at.roadrunner.android.controller.ContainerController;
import at.roadrunner.android.controller.ItemController;
import at.roadrunner.android.couchdb.RequestWorker;

public class Login extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = "Login";
	
	private ProgressDialog _progressDialog = null;
	private ArrayAdapter<String> _containerAdapter;
	private ArrayList<String> _containers;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		new ContainerController(this).getContainers();
		
//		// Runnable to fill the items in a Thread
//		Runnable showItems = new Runnable() {
//			@Override
//			public void run() {
//				loadTransportations();
//			}
//		};
//
//		// start a new Thread to get the items
//		new Thread(null, showItems, "getTransportations").start();
//
//		// show the Progressbar
//		_progressDialog = ProgressDialog.show(this, getString(R.string.app_progress_pleasewait), getString(R.string.app_progress_retdata), true);
//		
//		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		
//		_containerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
//		_containerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		
//		final Spinner cbxTransportation = (Spinner) findViewById(R.id.login_container);
//		cbxTransportation.setAdapter(_containerAdapter);
//		cbxTransportation.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				
//				prefs.edit().putString("transportation", cbxTransportation.getSelectedItem().toString()).commit();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				prefs.edit().putString("transportation", cbxTransportation.getSelectedItem().toString()).commit();
//			}
//		});
	}
	
	private void loadTransportations() {
		_containers = new ArrayList<String>();
		
		new ContainerController(this).getContainers();
		runOnUiThread(updateActivity);
	}
	
	private final Runnable updateActivity = new Runnable() {
		@Override
		public void run() {
			

			_progressDialog.dismiss();
			_containerAdapter.notifyDataSetChanged();
		}
	};
	
	public void onLoginClick(View view) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		EditText txtUsername = (EditText) findViewById(R.id.login_username);
		EditText txtPassword = (EditText) findViewById(R.id.login_password);
		Spinner cbxTransportation = (Spinner) findViewById(R.id.login_container);
		
		// TODO: Authenticate through server -> Wolfy FIX THIS MAN!
		
		
		// save the used form values into the preferences
		prefs.edit().putString("user", txtUsername.toString()).commit();
		prefs.edit().putString("password", txtPassword.toString()).commit();
		prefs.edit().putString("transportation", cbxTransportation.getSelectedItem().toString()).commit();
		
		startActivity(new Intent(this, Roadrunner.class));
		
	}
}
