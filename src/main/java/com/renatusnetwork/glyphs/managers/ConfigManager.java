package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.Glyphs;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static ConfigManager instance;

    public static ConfigManager getInstance() {
        return instance == null ? instance = new ConfigManager(Glyphs.getInstance()) : instance;
    }

    private Map<String, File> files;
    private Map<String, FileConfiguration> configs;

    public ConfigManager(Plugin plugin) {
        this.files = new HashMap<>();
        this.configs = new HashMap<>();

        init("config", plugin);
        init("menus", plugin);
        init("lang", plugin);
    }

    private void init(String fileName, Plugin plugin) {
        File file = new File(plugin.getDataFolder(), fileName + ".yml");
        FileConfiguration fileConfig = new YamlConfiguration();

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                copy(plugin.getResource( fileName + ".yml"), file);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        files.put(fileName, file);
        configs.put(fileName, fileConfig);

        load(fileName);
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf,0, len);
            }

            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration get(String fileName) {
        return configs.get(fileName);
    }

    public void load(String fileName) {
        try {
            configs.get(fileName).load(files.get(fileName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(String fileName) {
        try {
            configs.get(fileName).save(files.get(fileName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
