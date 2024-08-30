package com.renatusnetwork.glyphs.utils.config;

import com.renatusnetwork.glyphs.managers.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    public static String main_menu_name;

    public static void load() {
        FileConfiguration langConfig = getConfig();

        main_menu_name = langConfig.getString("main_menu_name");
    }

    public static FileConfiguration getConfig() {
        return ConfigManager.getInstance().get("config");
    }
}
