package at.roadrunner.android.controller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import at.roadrunner.android.activity.Roadrunner;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;

@RunWith(RobolectricTestRunner.class)
public class TimeControllerTest {
	
	private CalendarFactory _factory;
	private TimeController _controller;
	
	@Before
	public void setUp()  {
		_factory = new CalendarFactory();
		_controller = new TimeController(_factory);
		_controller.setOffset(0);
	}
	
	@Test
	public void testPreconditions()  {
		assertNotNull(_factory);
		assertNotNull(_controller);
	}
	
	@Test
	public void offsetShouldBeZeroAtStart()  {
		assertEquals(0, new TimeController(new CalendarFactory()).getOffset());
	}
	
	@Test
	public void offsetShouldBeChangable()  {
		_controller.setOffset(5);
		assertEquals(5, _controller.getOffset());
	}
	
	@Test
	public void offsetShouldBeEqualForAllTimeControllers()  {
		_controller.setOffset(1);
		new TimeController(_factory).setOffset(-10);
		assertEquals(-10, _controller.getOffset());
	}
	
	@Test
	public void mockedTimestampShouldBeReturned()  {
		CalendarFactory factory = mock(CalendarFactory.class);
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
		when(factory.createCalendarForUtc()).thenReturn(cal);
		assertSame(cal, factory.createCalendarForUtc());
	}
	
	@Test
	public void timeControllerShouldReturnDefinedCalendar()  {
		CalendarFactory factory = mock(CalendarFactory.class);
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
		when(factory.createCalendarForUtc()).thenReturn(cal);
		assertSame(cal, new TimeController(factory).getCalendar());
	}
	
	@Test
	public void timeControllerShouldReturnUtcInSeconds()  {
		CalendarFactory factory = mock(CalendarFactory.class);
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
		when(factory.createCalendarForUtc()).thenReturn(cal);
		assertEquals(cal.getTimeInMillis()/1000, new TimeController(factory).getTimestampForDatabase());
	}
	
	@Test
	public void timeControllerShouldReturnTimestampWithOffset()  {
		CalendarFactory factory = mock(CalendarFactory.class);
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
		when(factory.createCalendarForUtc()).thenReturn(cal);
		
		TimeController controller = new TimeController(factory);
		controller.setOffset(5);
		assertEquals(cal.getTimeInMillis()/1000+5, controller.getTimestampForDatabase());
	}
	
	@Test
	public void timeControllerShouldReturnCalendarWithOffset()  {
		CalendarFactory factory = mock(CalendarFactory.class);
		Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
		Calendar cal2 = GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
		when(factory.createCalendarForUtc()).thenReturn(cal);
		
		TimeController controller = new TimeController(factory);
		controller.setOffset(-5);
		cal2.add(Calendar.SECOND, -5);
		assertEquals(cal2, controller.getCalendar());
	}
	
	@Test
	public void timeControllerShouldNotCorrectTimeBecauseDelayedServerResponse() throws CouchDBException  {
		CalendarFactory factory = mock(CalendarFactory.class);
		final Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
		when(factory.createCalendarForUtc()).thenReturn(cal);
		final TimeController controller = new TimeController(factory);
		
		//Worker will answer with delayed time
		RequestWorker worker = mock(RequestWorker.class);
		when(worker.getServerTime())
			.thenAnswer(new Answer<Long>() {
				@Override
				public Long answer(InvocationOnMock invocation) throws Throwable {
					long time = controller.getTimestampForDatabase();
					cal.add(Calendar.SECOND, 3);
					return time;
				}
			});
		
		controller.synchronizeTime(worker);
		assertEquals(0, controller.getOffset());
	}
	
	@Test
	public void timeControllerShouldNotCorrectTimeBecauseTimeOffsetToLittle() throws CouchDBException  {
		CalendarFactory factory = mock(CalendarFactory.class);
		final Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
		when(factory.createCalendarForUtc()).thenReturn(cal);
		final TimeController controller = new TimeController(factory);
		
		//Worker will answer with delayed time
		RequestWorker worker = mock(RequestWorker.class);
		when(worker.getServerTime())
			.thenAnswer(new Answer<Long>() {
				@Override
				public Long answer(InvocationOnMock invocation) throws Throwable {
					long time = controller.getTimestampForDatabase()-4;
					cal.add(Calendar.SECOND, 1);
					return time;
				}
			});
		
		controller.synchronizeTime(worker);
		assertEquals(0, controller.getOffset());
	}
	
	@Test
	public void timeControllerShoulCorrectOffset() throws CouchDBException  {
		CalendarFactory factory = mock(CalendarFactory.class);
		final Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
		when(factory.createCalendarForUtc()).thenReturn(cal);
		final TimeController controller = new TimeController(factory);
		
		//Worker will answer with delayed time
		RequestWorker worker = mock(RequestWorker.class);
		when(worker.getServerTime())
			.thenAnswer(new Answer<Long>() {
				@Override
				public Long answer(InvocationOnMock invocation) throws Throwable {
					long time = controller.getTimestampForDatabase()+10;
					cal.add(Calendar.SECOND, 1);
					return time;
				}
			});
		
		controller.synchronizeTime(worker);
		assertEquals(10, controller.getOffset());
	}
	
}
