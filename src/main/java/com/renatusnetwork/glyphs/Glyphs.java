package com.renatusnetwork.glyphs;

import com.renatusnetwork.glyphs.commands.tags.Tags;
import com.renatusnetwork.glyphs.listeners.ChatListener;
import com.renatusnetwork.glyphs.listeners.InventoryListener;
import com.renatusnetwork.glyphs.listeners.JoinQuitListener;
import com.renatusnetwork.glyphs.managers.DatabaseManager;
import com.renatusnetwork.glyphs.managers.MenuManager;
import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.utils.config.ConfigUtils;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Glyphs extends JavaPlugin {

    private static Glyphs instance;
    private static Logger log;

    public static Glyphs getInstance() {
        return instance;
    }

    public static Logger getLog() {
        return log;
    }

    @Override
    public void onEnable() {
        instance = this;
        log = getLogger();

        LangUtils.load();
        ConfigUtils.load();

        registerManagers();
        registerListeners();
        registerCommands();

        DatabaseManager.getInstance().initTables();

        log.info("Glyphs enabled");
    }

    @Override
    public void onDisable() {
        log.info("Glyphs disabled");
    }

    private void registerManagers() {
        // Trigger init steps to avoid having it happen on load
        PlayerStatsManager.getInstance();
        TagsManager.getInstance();
        MenuManager.getInstance();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
    }

    private void registerCommands() {
        getCommand("tags").setExecutor(new Tags());
    }
}
