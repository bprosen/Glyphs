package com.renatusnetwork.glyphs.dependencies;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.listeners.LuckPermsMutateEvent;
import com.renatusnetwork.glyphs.utils.config.ConfigUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.node.NodeAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LuckPermsAdapter {

    private static LuckPermsAdapter instance;

    public static LuckPerms getInstance() {
        return instance != null ? instance.api : null;
    }

    public static void init() {
        if (ConfigUtils.luckperms_enabled) {
            instance = new LuckPermsAdapter();
        }
    }

    private LuckPerms api;

    public LuckPermsAdapter() {
        if (enable()) {
            subscribe();
            Glyphs.getLog().info("Hooked into LuckPerms");
        }
    }

    private boolean enable() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        api = provider != null ? provider.getProvider() : null;

        return api != null;
    }

    private void subscribe() {
        api.getEventBus().subscribe(Glyphs.getInstance(), NodeAddEvent.class, new LuckPermsMutateEvent()::onAdd);
    }
}
