package at.roadrunner.android.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import at.roadrunner.android.R;

public class Settings extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// preferences
		addPreferencesFromResource(R.xml.preferences_roadrunner);
	}
}
