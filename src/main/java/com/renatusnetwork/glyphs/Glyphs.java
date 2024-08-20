package com.renatusnetwork.glyphs;

import org.bukkit.plugin.java.JavaPlugin;

public class Glyphs extends JavaPlugin {

    private static Glyphs instance;

    public static Glyphs getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
    }
}
