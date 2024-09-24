package com.renatusnetwork.glyphs.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String strip(String input) {
        return ChatColor.stripColor(input);
    }

    public static String removeColorCodes(String input) {
        return strip(color(input));
    }
}
