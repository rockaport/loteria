package com.asesolutions.mobile.loteria.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final SimpleDateFormat iso8601dateFormat = new SimpleDateFormat(ISO8601, Locale.US);

    public static long iso8601toUnixTime(String dateTime) throws ParseException {
        return iso8601toDate(dateTime).getTime();
    }

    private static Date iso8601toDate(String dateTime) throws ParseException {
        return iso8601dateFormat.parse(dateTime);
    }

    public static Date unixToDate(long unixTime) {
        return new Date(unixTime);
    }
}
