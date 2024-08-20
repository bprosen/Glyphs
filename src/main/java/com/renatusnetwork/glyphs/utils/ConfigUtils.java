package com.renatusnetwork.glyphs.utils;

import com.renatusnetwork.glyphs.managers.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    public static FileConfiguration getConfig() {
        return ConfigManager.getInstance().get("config");
    }
}
