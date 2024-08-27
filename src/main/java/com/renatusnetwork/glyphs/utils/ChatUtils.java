package com.renatusnetwork.glyphs.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatUtils {

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void sendMessages(Player player, List<String> messages) {
        messages.forEach(message -> player.sendMessage(color(message)));
    }
}
