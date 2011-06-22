package at.roadrunner.android;

import android.app.Application;
import at.roadrunner.android.activity.ServiceController;

public class ApplicationController extends Application {

	private boolean _loginStatus = false;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// start the services
		new ServiceController().startAllServices(this);
	}

	public void setLoginStatus(boolean loginStatus) {
		_loginStatus = loginStatus;
	}

	public boolean getLoginStatus() {
		return _loginStatus;
	}
}
