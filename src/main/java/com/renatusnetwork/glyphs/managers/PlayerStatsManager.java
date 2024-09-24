package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.database.tables.FavoriteTagsUtils;
import com.renatusnetwork.glyphs.utils.database.tables.PlayerStatsUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class PlayerStatsManager {

    private static PlayerStatsManager instance;

    public static PlayerStatsManager getInstance() {
        return instance == null ? instance = new PlayerStatsManager() : instance;
    }

    private final HashMap<UUID, PlayerStats> playerStatsMap;

    public PlayerStatsManager() {
        this.playerStatsMap = new HashMap<>();
    }

    public PlayerStats get(Player player) {
        synchronized (playerStatsMap) {
            return playerStatsMap.get(player.getUniqueId());
        }
    }

    public PlayerStats get(UUID uuid) {
        synchronized (playerStatsMap) {
            return playerStatsMap.get(uuid);
        }
    }

    public boolean add(Player player) {
        PlayerStatsUtils.insert(player.getUniqueId(), player.getName());
        Tag currentTag = PlayerStatsUtils.getCurrentTag(player.getUniqueId());
        HashSet<Tag> favorites = FavoriteTagsUtils.getFavoriteTags(player.getUniqueId());

        synchronized (playerStatsMap) {
            return playerStatsMap.put(player.getUniqueId(),
                    PlayerStats.Builder.create()
                            .player(player)
                            .currentTag(currentTag)
                            .favorites(favorites)
                            .build()
            ) == null;
        }
    }

    public boolean remove(Player player) {
        synchronized (playerStatsMap) {
            return playerStatsMap.remove(player.getUniqueId()) != null;
        }
    }

    public void setTag(PlayerStats playerStats, Tag tag) {
        playerStats.setCurrentTag(tag);
        PlayerStatsUtils.setCurrentTag(playerStats.getUUID(), tag.getName());
    }

    public void resetTag(PlayerStats playerStats) {
        playerStats.resetCurrentTag();
        PlayerStatsUtils.resetCurrentTag(playerStats.getUUID());
    }

    public void addFavorite(PlayerStats playerStats, Tag tag) {
        playerStats.addFavorite(tag);
        FavoriteTagsUtils.addFavoriteTag(playerStats.getUUID(), tag.getName());
    }

    public void removeFavorite(PlayerStats playerStats, Tag tag) {
        playerStats.removeFavorite(tag);
        FavoriteTagsUtils.removeFavoriteTag(playerStats.getUUID(), tag.getName());
    }
}
