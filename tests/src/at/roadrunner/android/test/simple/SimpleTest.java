package at.roadrunner.android.test.simple;

import junit.framework.TestCase;
import mockit.Mockit;
import at.roadrunner.android.model.Log;

public class SimpleTest extends TestCase {

	public void testSomething()  {
		assertTrue(1==1);
	}
	
	public void testSomethin2g()  {
		assertTrue(1==1);
	}
	
	public void testSomething3()  {
		assertTrue(1==1);
	}
	
	public void testMore()  {
		assertEquals(true, true);
	}
	
	public void testMock()  {
		 Log log = Mockit.setUpMock(new Log());
		 
		 assertTrue(log != null);
		 
	}
	
	
}
