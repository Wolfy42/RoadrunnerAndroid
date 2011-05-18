package at.roadrunner.android.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import at.roadrunner.android.R;
import at.roadrunner.android.controller.ContainerController;
import at.roadrunner.android.couchdb.RequestWorker;

public class Login extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = "Login";
	
	private ProgressDialog _progressDialog = null;
	private ArrayAdapter<String> _containerAdapter;
	private ArrayList<String> _containers;
	
	private EditText _username; 
	private EditText _password;
	private Spinner _container;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		_username = (EditText) findViewById(R.id.login_username);
		_password = (EditText) findViewById(R.id.login_password);
		_container = (Spinner) findViewById(R.id.login_container);
		
		// Runnable to fill the items in a Thread
		Runnable loadContainers = new Runnable() {
			@Override
			public void run() {
				loadTransportations();
			}
		};

		// start a new Thread to get the items
		new Thread(null, loadContainers, "getTransportations").start();

		// show the Progressbar
		_progressDialog = ProgressDialog.show(this, getString(R.string.app_progress_pleasewait), getString(R.string.app_progress_retdata), true);
		
		_containerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		_containerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		final Spinner cbxTransportation = (Spinner) findViewById(R.id.login_container);
		cbxTransportation.setAdapter(_containerAdapter);
	}
	
	private void loadTransportations() {
		_containers = new ContainerController(this).getContainers();
		runOnUiThread(updateActivity);
	}
	
	private final Runnable updateActivity = new Runnable() {
		@Override
		public void run() {
			if (_containers != null) {
				for (String container : _containers) {
					_containerAdapter.add(container);
				}
				_containerAdapter.notifyDataSetChanged();
			} 
			_progressDialog.dismiss();
		}
	};
	
	public void onLoginClick(View view) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		String username = _username.getText().toString();
		String password = _password.getText().toString();
		String container = _container.getSelectedItem().toString();
		
		if (new RequestWorker(this).isAuthenticatedAtServer(username, password))  {
			// save the used form values into the preferences
			Editor edit = prefs.edit();
			edit.putString("user", username);
			edit.putString("password", password);
			edit.putString("transportation", container);
			edit.commit();
			
			startActivity(new Intent(this, Roadrunner.class));
		}  else  {
			Toast.makeText(this, "Not Authenticated", Toast.LENGTH_SHORT).show();
		}
	}
}
