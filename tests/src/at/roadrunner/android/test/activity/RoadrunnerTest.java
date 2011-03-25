package at.roadrunner.android.test.activity;

import android.test.ActivityInstrumentationTestCase2;
import at.roadrunner.android.activity.Roadrunner;

public class RoadrunnerTest extends ActivityInstrumentationTestCase2<Roadrunner> {

	public RoadrunnerTest() {
		super("at.roadrunner.android", Roadrunner.class);
	}
	
	private Roadrunner _a;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		_a = getActivity();

	}
	
	public void testPreconditions()  {
		assertNotNull(_a);
		
		
	}

}
