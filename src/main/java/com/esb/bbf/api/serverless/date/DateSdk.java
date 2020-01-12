package com.esb.bbf.api.serverless.date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public final class DateSdk {

    public String string(String time) {
        return this.string(time,null);
    }
    public String string(String time, String format) {
        return this.string(new Long(time).longValue(), format);
    }
    public String string(long time) {
        return this.string(time,null);
    }
    public String string(long time, String format) {
        DateTime dateTime = new DateTime(time);
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return dateTime.toString(format);
    }
    public long toLong(String time) {
        return this.toLong(time,null);
    }
    public long toLong(String time, String format) {
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return DateTime.parse(time, DateTimeFormat.forPattern(format)).getMillis();
    }
    public long plus(Long time, String plusType, Integer plus) {
        DateTime dateTime = new DateTime();
        if (time == null || time.longValue() == 0) {
            dateTime = new DateTime();
        } else {
            dateTime = new DateTime(time);
        }
        if (StringUtils.isEmpty(plusType)) {
            return dateTime.plusMillis(plus).getMillis();
        }
        if (plusType.equals("millis")) {
            dateTime = dateTime.plusMillis(plus);
        }
        if (plusType.equals("second")) {
            dateTime = dateTime.plusSeconds(plus);
        }
        if (plusType.equals("minute")) {
            dateTime = dateTime.plusMinutes(plus);
        }
        if (plusType.equals("hour")) {
            dateTime = dateTime.plusHours(plus);
        }
        if (plusType.equals("day")) {
            dateTime = dateTime.plusDays(plus);
        }
        if (plusType.equals("week")) {
            dateTime = dateTime.plusWeeks(plus);
        }
        if (plusType.equals("month")) {
            dateTime = dateTime.plusMonths(plus);
        }
        if (plusType.equals("year")) {
            dateTime = dateTime.plusYears(plus);
        }
        return dateTime.getMillis();
    }



}
