package com.renatusnetwork.glyphs.utils.database.tables;

import com.renatusnetwork.glyphs.managers.DatabaseManager;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.database.DatabaseUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class PlayerStatsUtils {

    public static Tag getCurrentTag(UUID uuid) {
        return TagsManager.getInstance().getTag(
                DatabaseUtils.getResult(DatabaseManager.PLAYERS_TABLE, "*", "WHERE uuid=?", uuid).get("current_tag")
        );
    }

    public static HashSet<Tag> getCurrentTags(UUID uuid) {
        HashSet<Tag> tags = new HashSet<>();

        List<HashMap<String, String>> results = DatabaseUtils.getResults(DatabaseManager.OWNED_TAGS_TABLE, "*", "WHERE player_uuid=?", uuid);
        results.forEach(result -> tags.add(TagsManager.getInstance().getTag(result.get("owned_tag"))));

        return tags;
    }
}
