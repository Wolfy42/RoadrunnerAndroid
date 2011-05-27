package at.roadrunner.android.controller;

import java.util.Calendar;

public class TimeController {

	//Offset of the time in milliseconds
	private static Long _offset = Long.valueOf(0);
	
	private CalendarFactory _factory;
	
	public TimeController(CalendarFactory factory) {
		_factory = factory;
	}

	public static Long getGlobalOffset()  {
		synchronized (_offset)  {
			return _offset;
		}
	}
	
	public static void setGlobalOffset(Long offset)  {
		synchronized (_offset)  {
			_offset = offset;
		}
	}
	
	public long getOffset() {
		return getGlobalOffset();
	}

	public void setOffset(long offset) {
		setGlobalOffset(offset);
	}
	
	public Calendar getCalendar()  {
		return _factory.createCalendarForUtc();
	}

}
