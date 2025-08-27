package com.germarna.patterns.decorator.hexagonalddd.integration.e2e.helpers;
import java.util.Calendar;
import java.util.Date;

public final class Dates {
	private Dates() {
	}

	public static Date date(int year, int monthCalendarConst, int day, int hour, int min, int sec) {
		final Calendar cal = Calendar.getInstance();
		cal.set(year, monthCalendarConst, day, hour, min, sec);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date noon(int year, int monthCalendarConst, int day) {
		return date(year, monthCalendarConst, day, 12, 0, 0);
	}
}
