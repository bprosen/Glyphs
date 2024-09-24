package com.renatusnetwork.glyphs.listeners;

import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.menus.MenuHolder;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.menus.items.MenuItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof MenuHolder) {
            event.setCancelled(true);

            Inventory clickedInventory = event.getClickedInventory();
            if (clickedInventory != null && clickedInventory.getHolder() instanceof MenuHolder) {
                MenuHolder holder = (MenuHolder) clickedInventory.getHolder();
                PlayerStats playerStats = PlayerStatsManager.getInstance().get((Player) event.getWhoClicked());

                MenuItem menuItem = holder.getMenuPage().getItem(event.getSlot());

                if (menuItem instanceof ActionItem) {
                    ((ActionItem) menuItem).click(playerStats);
                }
            }
        }
    }
}
