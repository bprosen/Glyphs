package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.database.DatabaseUtils;
import com.renatusnetwork.glyphs.utils.database.tables.PlayerStatsUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class PlayerStatsManager {

    private static PlayerStatsManager instance;

    public static PlayerStatsManager getInstance() {
        if (instance == null)
            instance = new PlayerStatsManager();

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

        Tag currentTag = PlayerStatsUtils.getCurrentTag(player.getUniqueId());
        HashSet<Tag> ownedTags = PlayerStatsUtils.getCurrentTags(player.getUniqueId());

        return playerStatsMap.put(player,
                PlayerStats.Builder.create()
                    .setPlayer(player)
                    .setCurrentTag(currentTag)
                    .setOwnedTags(ownedTags)
                    .build()
                ) == null;
    }
}
