package at.roadrunner.android.test.sensor;

import android.test.suitebuilder.TestSuiteBuilder;
import at.roadrunner.android.test.simple.SimpleTest;
import junit.framework.TestSuite;

public class HttpSensorTestSuite extends TestSuite {

	public static TestSuite suite() {
        return(new TestSuiteBuilder(HttpSensorTest.class)
        				.includeAllPackagesUnderHere()
                        .build());
    }
}
