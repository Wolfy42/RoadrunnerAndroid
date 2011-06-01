package at.roadrunner.android.controller;

import java.util.Calendar;

import at.roadrunner.android.Config;
import at.roadrunner.android.couchdb.CouchDBException;
import at.roadrunner.android.couchdb.RequestWorker;
import at.roadrunner.android.model.Log;

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

	public void synchronizeTime(RequestWorker requestWorker, ItemController itemController) {
		try {
			long before = getTimestampForDatabase();
			long serverTime = requestWorker.getServerTime();
			long after = getTimestampForDatabase();
			
			android.util.Log.e("roadrunner", "before: "+before);
			android.util.Log.e("roadrunner", "servertime: "+serverTime);
			android.util.Log.e("roadrunner", "after: "+after);
			
			if (after-before <= Config.SERVER_RESPONSE_DELAY)  {
				if (Math.abs(serverTime-before) >= Config.SERVER_OFFSET_FOR_CORRECTION)  {
					setGlobalOffset(serverTime-before);
					android.util.Log.e("roadrunner", "correction: "+(serverTime-before));
					requestWorker.saveLog(itemController.getLoadedItemsAsArray(), Log.LogType.TIMESYNCHRONIZATION, "Correction: " + (serverTime-before) + " seconds.", null);
				}
			}
		} catch (CouchDBException e)  {
			e.printStackTrace();
		}
	}
}
