package com.renatusnetwork.glyphs.utils.config;

import com.renatusnetwork.glyphs.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    public static String main_menu_name;
    public static Material menu_default_material;
    public static Material menu_acquired_tag_material;
    public static Material menu_missing_tag_material;
    public static String permission_node_prefix;
    public static int minimum_search_input;

    public static void load() {
        FileConfiguration config = getConfig();

        main_menu_name = config.getString("main_menu_name");
        menu_default_material = Material.matchMaterial(config.getString("menu_default_material"));
        menu_acquired_tag_material = Material.matchMaterial(config.getString("menu_acquired_tag_material"));
        menu_missing_tag_material = Material.matchMaterial(config.getString("menu_missing_tag_material"));
        permission_node_prefix = config.getString("permission_node_prefix");
        minimum_search_input = config.getInt("minimum_search_input");
    }

    public static FileConfiguration getConfig() {
        return ConfigManager.getInstance().get("config");
    }
}
