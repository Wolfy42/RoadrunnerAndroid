package at.roadrunner.android.test.sensor;

import android.test.suitebuilder.TestSuiteBuilder;
import at.roadrunner.android.test.simple.SimpleTest;
import junit.framework.TestSuite;

public class SensorTestSuite extends TestSuite {

	public static TestSuite suite() {
        return(new TestSuiteBuilder(SensorTest.class)
        				.includeAllPackagesUnderHere()
                        .build());
    }
}
