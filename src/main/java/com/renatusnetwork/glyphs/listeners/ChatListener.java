package com.renatusnetwork.glyphs.listeners;

import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerStats playerStats = PlayerStatsManager.getInstance().get(player);

        if (playerStats != null) {
            Tag currentTag = playerStats.getCurrentTag();
            event.setFormat(event.getFormat().replace("{glyphs_tag_title}", currentTag != null ? ChatUtils.color(currentTag.getTitle()) : ""));
        }
    }
}
