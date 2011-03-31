package test;

import org.junit.Test;
import org.junit.runner.RunWith;

import at.roadrunner.android.R;
import at.roadrunner.android.activity.Roadrunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MyTest {
	@Test
	public void shouldBeOk() throws Exception {
		String hello = new Roadrunner().getResources().getString(R.string.systemtest_test_ok);
		assertThat(hello, equalTo("OK"));
	}
}
