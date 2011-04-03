package at.roadrunner.android.test.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import at.roadrunner.android.R;
import at.roadrunner.android.activity.Roadrunner;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.tester.android.view.TestMenu;
import com.xtremelabs.robolectric.tester.android.view.TestMenuItem;
@RunWith(RobolectricTestRunner.class)
public class RoadrunnerMenuTest {

	private Roadrunner _activity;
	private TestMenu _menu;
	
	@Before
	public void setUp() throws Exception {
		_activity = new Roadrunner();
		_menu = new TestMenu();
		
		_activity.onCreate(null);
		_activity.onCreateOptionsMenu(_menu);
	}
	
	@Test
	public void testPreconditions()  {
		assertNotNull(_activity);
		assertNotNull(_menu);
	}
	
	@Test
	public void menuItemNameShouldBeSettings()  {
		TestMenuItem menuItem = (TestMenuItem) _menu.getItem(0);
		assertEquals(menuItem.getTitle().toString(), "@string/roadrunner_menu_settings");
	}
	
	@Test
	public void menuItemSelectionShouldWork()  {
		TestMenuItem menuItem = (TestMenuItem) _menu.getItem(0);
		assertTrue(_activity.onOptionsItemSelected(menuItem));
	}
	
	@Test
	public void menuItemSelectionShouldStartSettings()  {
		TestMenuItem menuItem = (TestMenuItem) _menu.getItem(0);
		_activity.onOptionsItemSelected(menuItem);
		Intent intent = Robolectric.shadowOf(_activity.getApplication()).getNextStartedActivity();
		assertTrue(intent.getComponent().getClassName().endsWith("Settings"));	
	}
	
}
