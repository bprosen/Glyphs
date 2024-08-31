package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.database.tables.PlayerStatsUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;

public class PlayerStatsManager {

    private static PlayerStatsManager instance;

    public static PlayerStatsManager getInstance() {
        return instance == null ? instance = new PlayerStatsManager() : instance;
    }

    private HashMap<Player, PlayerStats> playerStatsMap;

    public PlayerStatsManager() {
        this.playerStatsMap = new HashMap<>();
    }

    public PlayerStats get(Player player) {
        return playerStatsMap.get(player);
    }

    public boolean add(Player player) {
        PlayerStatsUtils.insert(player.getUniqueId(), player.getName());
        Tag currentTag = PlayerStatsUtils.getCurrentTag(player.getUniqueId());

        return playerStatsMap.put(player,
                PlayerStats.Builder.create()
                    .player(player)
                    .currentTag(currentTag)
                    .build()
                ) == null;
    }

    public boolean remove(Player player) {
        return playerStatsMap.remove(player) != null;
    }

    public void setTag(PlayerStats playerStats, Tag tag) {
        playerStats.setCurrentTag(tag);
        PlayerStatsUtils.setCurrentTag(playerStats.getUUID(), tag.getName());
    }

    public void resetTag(PlayerStats playerStats) {
        playerStats.resetCurrentTag();
        PlayerStatsUtils.resetCurrentTag(playerStats.getUUID());
    }
}
