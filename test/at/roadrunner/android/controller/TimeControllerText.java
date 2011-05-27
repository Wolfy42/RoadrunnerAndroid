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

import android.text.format.Time;
public class TimeControllerText {
	
	private CalendarFactory _factory;
	private TimeController _controller;
	
	@Before
	public void setUp()  {
		_factory = new CalendarFactory();
		_controller = new TimeController(_factory);
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
	
	
}
