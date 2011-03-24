package at.roadrunner.android.test.simple;

import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.TestSuite;

public class SimpleTestSuite extends junit.framework.TestSuite {
	
    public static TestSuite suite() {
        return(new TestSuiteBuilder(SimpleTest.class)
        				.includeAllPackagesUnderHere()
                        .build());
    }
} 