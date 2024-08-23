package com.renatusnetwork.glyphs;

import com.renatusnetwork.glyphs.commands.TagsCMD;
import com.renatusnetwork.glyphs.listeners.ChatListener;
import com.renatusnetwork.glyphs.listeners.InventoryListener;
import com.renatusnetwork.glyphs.listeners.JoinQuitListener;
import com.renatusnetwork.glyphs.managers.DatabaseManager;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.event.world.ChunkEvent;
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

        registerListeners();
        registerCommands();

        LangUtils.load();
        DatabaseManager.getInstance().initTables();

        log.info("Glyphs enabled");
    }

    @Override
    public void onDisable() {
        log.info("Glyphs disabled");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
    }

    private void registerCommands() {
        getCommand("tags").setExecutor(new TagsCMD());
    }
}
