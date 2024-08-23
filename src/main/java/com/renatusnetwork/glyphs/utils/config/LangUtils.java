package com.renatusnetwork.glyphs.utils.config;

import com.renatusnetwork.glyphs.managers.ConfigManager;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;

public class LangUtils {

    public static String player_offline;
    public static String tag_exists;
    public static String tag_created;

    public static void load() {
        FileConfiguration langConfig = ConfigManager.getInstance().get("lang");

        player_offline = langConfig.getString("player_offline");
        tag_exists = langConfig.getString("tag_exists");
        tag_created = langConfig.getString("tag_created");
    }

    public static String parse(String input, String replacement) {
        return ChatUtils.color(input.replace("$1", replacement));
    }

    public static String parseString(String input, List<String> replacements) {
        for (int i = 0; i < replacements.size(); i++) {
            input = input.replace("$" + (i + 1), replacements.get(i));
        }

        return ChatUtils.color(input);
    }
}
