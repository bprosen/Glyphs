package com.renatusnetwork.glyphs.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static String parseTimeToCreatedAt(int seconds) {
        Date date = new Date((long) seconds * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // for some reason that makes no sense, only Calendar.MONTH is index 0, so + 1 for actual month
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
    }
}
