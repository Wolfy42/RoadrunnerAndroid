package test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import at.roadrunner.android.R;
import at.roadrunner.android.activity.Roadrunner;

@RunWith(RobolectricTestRunner.class)
public class SimpleTest {
	@Test
	public void shouldBeOk() throws Exception {
		String hello = new Roadrunner().getResources().getString(R.string.systemtest_test_ok);
		assertThat(hello, equalTo("OK"));
	}
}
