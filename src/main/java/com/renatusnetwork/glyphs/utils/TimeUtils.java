package com.renatusnetwork.glyphs.utils;

import java.util.Date;

public class TimeUtils {

    public static String parseTime(int seconds) {
        return new Date((long) seconds * 1000).toString();
    }
}
