package com.renatusnetwork.glyphs;

import com.renatusnetwork.glyphs.managers.DatabaseManager;
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

        DatabaseManager.getInstance().initTables();

        log.info("Glyphs enabled");
    }

    @Override
    public void onDisable() {
        log.info("Glyphs disabled");
    }
}
