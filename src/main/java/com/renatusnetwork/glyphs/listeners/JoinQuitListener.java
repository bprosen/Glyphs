package com.renatusnetwork.glyphs.listeners;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        new BukkitRunnable() {
            @Override
            public void run() {
                PlayerStatsManager.getInstance().add(player);
            }
        }.runTaskAsynchronously(Glyphs.getInstance());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerStatsManager.getInstance().remove(event.getPlayer());
    }
}
