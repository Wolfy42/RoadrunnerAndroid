package at.roadrunner.android.activity;

import android.os.Bundle;
import at.roadrunner.android.R;
import com.google.android.maps.MapActivity;

public class DeliveryMap extends MapActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_map);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
