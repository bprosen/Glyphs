package com.renatusnetwork.glyphs.utils.config;

public class ParseUtils {

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
