package at.roadrunner.android.test.barcode;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import at.roadrunner.android.barcode.BarcodeFactory;
import at.roadrunner.android.barcode.BarcodeReader;

public class BarcodeTest {

	private BarcodeReader _r;
	
	@Before
	public void setUp()  {
		_r = BarcodeFactory.createTestBarcodeReader();
	}
	
	@Test
	public void readBarcode()  {
		assertTrue(1 == _r.readNextBarcode());
	}
	
}
