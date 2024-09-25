package com.renatusnetwork.glyphs.utils.database.tables;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.managers.DatabaseManager;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.menus.types.FilterType;
import com.renatusnetwork.glyphs.objects.menus.types.SortType;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.database.DatabaseUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class PlayerStatsUtils {

    public static void insert(UUID uuid, String name) {
        DatabaseUtils.run("INSERT IGNORE INTO " + DatabaseManager.PLAYERS_TABLE + " (uuid, name) VALUES(?,?)", uuid.toString(), name);
    }

    public static HashMap<String, String> getPlayerStats(UUID uuid) {
        return DatabaseUtils.getResult(DatabaseManager.PLAYERS_TABLE, "*", "WHERE uuid=?", uuid.toString());
    }

    public static void toggleFilter(UUID uuid, FilterType filter) {
        DatabaseUtils.runAync(
                "UPDATE " + DatabaseManager.PLAYERS_TABLE + " SET " + DatabaseUtils.filterKey(filter) + "=NOT " + DatabaseUtils.filterKey(filter) +
                      " WHERE uuid=?",
                      uuid.toString()
        );
    }

    public static void setSortBy(UUID uuid, SortType sortBy) {
        DatabaseUtils.runAync("UPDATE " + DatabaseManager.PLAYERS_TABLE + " SET sort_by=? WHERE uuid=?", sortBy.name(), uuid.toString());
    }

    public static void setCurrentTag(UUID uuid, String tagName) {
        DatabaseUtils.runAync("UPDATE " + DatabaseManager.PLAYERS_TABLE + " SET current_tag=? WHERE uuid=?", tagName, uuid.toString());
    }

    public static void resetCurrentTag(UUID uuid) {
        DatabaseUtils.runAync("UPDATE " + DatabaseManager.PLAYERS_TABLE + " SET current_tag=NULL WHERE uuid=?", uuid.toString());
    }
}
