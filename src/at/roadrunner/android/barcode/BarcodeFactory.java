package at.roadrunner.android.barcode;

public class BarcodeFactory {

	public static BarcodeReader createTestBarcodeReader()  {
		return new TestBarcodeReader();
	}
	
	
}
