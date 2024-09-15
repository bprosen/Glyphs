package com.renatusnetwork.glyphs.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String strip(String input) {
        return ChatColor.stripColor(input);
    }
}
