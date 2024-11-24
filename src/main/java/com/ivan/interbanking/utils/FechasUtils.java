package com.ivan.interbanking.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FechasUtils
{

	private FechasUtils()
	{
		throw new IllegalStateException("Utility class");
	}

	public static LocalDate restarUnMes(Long fechaEnMilis)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(fechaEnMilis);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}
}
