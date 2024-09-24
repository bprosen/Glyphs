package com.renatusnetwork.glyphs.utils.database.tables;

import com.renatusnetwork.glyphs.managers.DatabaseManager;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.database.DatabaseUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class FavoriteTagsUtils {

    public static HashSet<Tag> getFavoriteTags(UUID uuid) {
        List<HashMap<String, String>> results = DatabaseUtils.getResults(DatabaseManager.FAVORITE_TAGS_TABLE, "tag_name", "WHERE player_uuid=?", uuid.toString());
        HashSet<Tag> temp = new HashSet<>();

        results.forEach(resultTag -> {
            Tag tag = TagsManager.getInstance().get(resultTag.get("tag_name"));
            if (tag != null) {
                temp.add(tag);
            }
        });
        return temp;
    }

    public static void addFavoriteTag(UUID uuid, String tagName) {
        DatabaseUtils.runAync("INSERT INTO " + DatabaseManager.FAVORITE_TAGS_TABLE + " (player_uuid, tag_name) VALUES (?, ?)", uuid.toString(), tagName);
    }

    public static void removeFavoriteTag(UUID uuid, String tagName) {
        DatabaseUtils.runAync("DELETE FROM " + DatabaseManager.FAVORITE_TAGS_TABLE + " WHERE player_uuid=? AND tag_name=?", uuid.toString(), tagName);
    }
}
