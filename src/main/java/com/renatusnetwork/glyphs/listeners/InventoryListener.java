package com.renatusnetwork.glyphs.listeners;

import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            PlayerStats playerStats = PlayerStatsManager.getInstance().get((Player) event.getPlayer());

            if (playerStats != null) {
                playerStats.resetOpenedMenu();
            }
        }
    }
}
