package com.renatusnetwork.glyphs.utils.database.tables;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.managers.DatabaseManager;
import com.renatusnetwork.glyphs.managers.TagsManager;
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

    public static Tag getCurrentTag(UUID uuid) {
        return TagsManager.getInstance().get(
                DatabaseUtils.getResult(DatabaseManager.PLAYERS_TABLE, "current_tag", "WHERE uuid=?", uuid.toString()).get("current_tag")
        );
    }

    public static void setCurrentTag(UUID uuid, String tagName) {
        DatabaseUtils.runAync("UPDATE " + DatabaseManager.PLAYERS_TABLE + " SET current_tag=? WHERE uuid=?", tagName, uuid.toString());
    }

    public static void resetCurrentTag(UUID uuid) {
        DatabaseUtils.runAync("UPDATE " + DatabaseManager.PLAYERS_TABLE + " SET current_tag=NULL WHERE uuid=?", uuid.toString());
    }
}
