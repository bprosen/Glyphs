package com.renatusnetwork.glyphs;

import com.renatusnetwork.glyphs.commands.glyphs.GlyphsCommand;
import com.renatusnetwork.glyphs.commands.tags.TagsCommand;
import com.renatusnetwork.glyphs.listeners.ChatListener;
import com.renatusnetwork.glyphs.listeners.InventoryListener;
import com.renatusnetwork.glyphs.listeners.JoinQuitListener;
import com.renatusnetwork.glyphs.managers.*;
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
        DatabaseManager.getInstance().initTables();

        registerManagers();
        registerListeners();
        registerCommands();

        log.info("Glyphs enabled");
    }

    @Override
    public void onDisable() {
        DatabaseManager.getInstance().close();
        log.info("Glyphs disabled");
    }

    public void loadAllData() {
        ConfigManager.getInstance().load();
        ConfigUtils.load();
        LangUtils.load();
        MenuManager.getInstance().load();
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
        getCommand("tags").setExecutor(new TagsCommand());
        getCommand("glyphs").setExecutor(new GlyphsCommand());
    }
}
