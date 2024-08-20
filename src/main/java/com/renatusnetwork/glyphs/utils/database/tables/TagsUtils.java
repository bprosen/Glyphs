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
}
