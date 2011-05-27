package at.roadrunner.android.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.text.format.Time;

public class CalendarFactory {

	public Calendar createCalendarForUtc()  {
		return GregorianCalendar.getInstance(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
	}
}
