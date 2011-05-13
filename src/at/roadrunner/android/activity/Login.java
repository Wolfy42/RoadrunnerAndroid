package at.roadrunner.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import at.roadrunner.android.R;

public class Login extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	public void onLoginClick(View view) {
		EditText txtUsername = (EditText) findViewById(R.id.login_username);
		EditText txtPassword = (EditText) findViewById(R.id.login_password);
		
		if (txtUsername.getText().toString().equals("xxx") && txtPassword.getText().toString().equals("y") ) {
			startActivity(new Intent(this, Roadrunner.class));
		} else {
			Toast toast = Toast.makeText(this, "HAHA FALSCH GERATEN! ZONK! -> user: xxx, pwd: y2", Toast.LENGTH_LONG);
			toast.show();
		}
	}
}
