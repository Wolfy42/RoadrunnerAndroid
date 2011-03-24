package at.roadrunner.android.barcode;

import android.content.Intent;

public class BarcodeIntent  extends Intent {
	
	public BarcodeIntent() {
		super("com.google.zxing.client.android.SCAN");
		setPackage("com.google.zxing.client.android");
		putExtra("SCAN_MODE", "QR_CODE_MODE");
	}
}
