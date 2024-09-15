package com.renatusnetwork.glyphs.utils.database.tables;

import com.renatusnetwork.glyphs.managers.DatabaseManager;
import com.renatusnetwork.glyphs.utils.database.DatabaseUtils;

import java.util.HashMap;
import java.util.List;

public class TagsUtils {

    public static List<HashMap<String, String>> getTags() {
        return DatabaseUtils.getResults(DatabaseManager.TAGS_TABLE, "*", "");
    }

    public static HashMap<String, String> getTag(String name) {
        return DatabaseUtils.getResult(DatabaseManager.TAGS_TABLE, "*", "WHERE name=?", name);
    }

    public static void createTag(String name, String creator, int creationDate) {
        DatabaseUtils.runAync(
                "INSERT INTO " + DatabaseManager.TAGS_TABLE + " " +
                       "(name, creator_name, creation_date) " +
                       "VALUES (?, ?, ?)",
                        name, creator, creationDate
        );
    }

    public static void deleteTag(String name) {
        DatabaseUtils.runAync("DELETE FROM " + DatabaseManager.TAGS_TABLE + " WHERE name=?", name);
    }

    public static void setTitle(String name, String title) {
        DatabaseUtils.runAync("UPDATE " + DatabaseManager.TAGS_TABLE + " SET title=? WHERE name=?", title, name);
    }
}
