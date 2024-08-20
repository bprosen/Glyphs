package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class PlayerStatsManager {

    private static PlayerStatsManager instance;

    public static PlayerStatsManager getInstance() {
        if (instance == null) {
            instance = new PlayerStatsManager();
        }
        return instance;
    }

    private HashMap<Player, PlayerStats> playerStatsMap;

    public PlayerStatsManager() {
        this.playerStatsMap = new HashMap<>();
    }

    public PlayerStats get(Player player) {
        return playerStatsMap.get(player);
    }

    public boolean add(Player player) {
        // TODO: get from db

        Tag currentTag = null;
        HashSet<Tag> ownedTags = new HashSet<>();

        return playerStatsMap.put(player,
                PlayerStats.Builder.create()
                    .setPlayer(player)
                    .setCurrentTag(currentTag)
                    .setOwnedTags(ownedTags)
                    .build()
                ) == null;
    }
}
