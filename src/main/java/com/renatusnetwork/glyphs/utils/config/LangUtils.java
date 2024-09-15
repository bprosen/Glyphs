package com.renatusnetwork.glyphs.utils.config;

import com.renatusnetwork.glyphs.managers.ConfigManager;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LangUtils {

    public static String player_offline;
    public static String tag_exists;
    public static String tag_not_exists;
    public static String tag_title_not_exists;
    public static String tag_created;
    public static String tag_deleted;
    public static String tag_set;
    public static String tag_set_title;
    public static String tag_reset;
    public static String tag_reveal_title;
    public static String tag_info;
    public static String tags_command_help;

    public static void load() {
        FileConfiguration langConfig = ConfigManager.getInstance().get("lang");

        player_offline = langConfig.getString("player_offline");
        tag_exists = langConfig.getString("tag_exists");
        tag_not_exists = langConfig.getString("tag_not_exists");
        tag_title_not_exists = langConfig.getString("tag_title_not_exists");
        tag_created = langConfig.getString("tag_created");
        tag_deleted = langConfig.getString("tag_deleted");
        tag_set = langConfig.getString("tag_set");
        tag_set_title = langConfig.getString("tag_set_title");
        tag_reset = langConfig.getString("tag_reset");
        tag_info = flattenStringList(langConfig.getStringList("tag_info"));
        tags_command_help = flattenStringList(langConfig.getStringList("tags_command_help"));
    }

    public static String parse(String input, String... replacements) {
        for (int i = 0; i < replacements.length; i++) {
            input = input.replace("$" + (i + 1), replacements[i]);
        }

        return ChatUtils.color(input);
    }

    private static String flattenStringList(List<String> list) {
        return String.join("\n", list);
    }
}
