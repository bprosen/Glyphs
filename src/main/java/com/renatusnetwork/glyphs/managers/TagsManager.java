package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.database.tables.TagsUtils;

import java.util.HashMap;
import java.util.List;

public class TagsManager {

    private static TagsManager instance;

    public static TagsManager getInstance() {
        if (instance == null)
            instance = new TagsManager();

        return instance;
    }

    private HashMap<String, Tag> tags;

    public TagsManager() {
        this.tags = new HashMap<>();

        init();
    }

    private void init() {
        List<HashMap<String, String>> results = TagsUtils.getTags();

        results.forEach(result -> tags.put(result.get("name"),
                Tag.Builder.create()
                    .setName(result.get("name"))
                    .setDisplay(result.get("display"))
                    .setCreatorName(result.get("creator_name"))
                    .build()
        ));
    }

    public boolean add(String name) {
        HashMap<String, String> results = TagsUtils.getTag(name);

        return tags.put(name,
                Tag.Builder.create()
                    .setName(name)
                    .setDisplay(results.get("display"))
                    .setCreatorName(results.get("creator_name"))
                    .build()
        ) == null;
    }

    public Tag getTag(String name) {
        return tags.get(name);
    }
}
