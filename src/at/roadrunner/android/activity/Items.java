package at.roadrunner.android.activity;

import android.app.ListActivity;
import android.os.Bundle;
import at.roadrunner.android.R;

public class Items extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
	}
}