package at.roadrunner.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import at.roadrunner.android.R;

public class Login extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = "Login";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		String[] containers = new String[] {"LKW1", "LKW2", "LKW3", "PKW1"};
		ArrayAdapter<String> containerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, containers);
		containerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		Spinner cbxTransportation = (Spinner) findViewById(R.id.login_container);
		cbxTransportation.setAdapter(containerAdapter);
		
		
		
	}
	
	public void onLoginClick(View view) {
		EditText txtUsername = (EditText) findViewById(R.id.login_username);
		EditText txtPassword = (EditText) findViewById(R.id.login_password);
		
		if (txtUsername.getText().toString().equals("xxx") && txtPassword.getText().toString().equals("y") ) {
			startActivity(new Intent(this, Roadrunner.class));
		} else {
			Toast toast = Toast.makeText(this, "HAHA FALSCH GERATEN! ZONK! -> user: xxx, pwd: y", Toast.LENGTH_LONG);
			toast.show();
		}
	}
}
