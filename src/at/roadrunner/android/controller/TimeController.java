package at.roadrunner.android.controller;

import java.util.Calendar;

import android.content.Context;
import at.roadrunner.android.Config;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;

public class TimeController {

	//Offset of the time in seconds
	private static Long _offset = Long.valueOf(0);
	
	private CalendarFactory _factory;
	
	public TimeController()  {
		_factory = new CalendarFactory();
	}
	
	public TimeController(CalendarFactory factory) {
		_factory = factory;
	}
	
	private static Long getGlobalOffset()  {
		synchronized (_offset)  {
			return _offset;
		}
	}
	
	private static void setGlobalOffset(Long offset)  {
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
		Calendar cal = _factory.createCalendarForUtc();
		cal.add(Calendar.SECOND, getGlobalOffset().intValue());
		return cal;
	}

	public long getTimestampForDatabase() {
		return _factory.createCalendarForUtc().getTimeInMillis()/1000+getGlobalOffset();
	}

	public void synchronizeTime(RequestWorker requestWorker) {
		try {
			long before = getTimestampForDatabase();
			long serverTime = requestWorker.getServerTime();
			long after = getTimestampForDatabase();
			
			if (after-before <= Config.SERVER_RESPONSE_DELAY)  {
				if (Math.abs(serverTime-before) >= Config.SERVER_OFFSET_FOR_CORRECTION)  {
					//TODO: save log for correction
					setGlobalOffset(serverTime-before);
				}
			}
		} catch (CouchDBException e)  {
			e.printStackTrace();
		}
	}
}
