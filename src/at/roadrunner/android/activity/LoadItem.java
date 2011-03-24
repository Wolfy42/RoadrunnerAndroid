package at.roadrunner.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import at.roadrunner.android.barcode.BarcodeIntent;

public class LoadItem  extends Activity  {
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		startActivityForResult(new BarcodeIntent(), 0);
		
		return true;
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
		
		Log.e("w", data.getDataString());
		
	}
	

}
