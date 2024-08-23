package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.database.tables.TagsUtils;

import java.util.HashMap;
import java.util.List;

public class TagsManager {

    private static TagsManager instance;

    public static TagsManager getInstance() {
        return instance == null ? instance = new TagsManager() : instance;
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
                    .name(result.get("name"))
                    .display(result.get("display"))
                    .creatorName(result.get("creator_name"))
                    .build()
        ));
    }

    public boolean exists(String name) {
        return tags.containsKey(name);
    }

    public void create(String name, String creator) {
        tags.put(name, Tag.Builder.create().name(name).creatorName(creator).build());
        TagsUtils.createTag(name, creator);
    }

    public boolean add(String name) {
        HashMap<String, String> results = TagsUtils.getTag(name);

        return tags.put(name,
                Tag.Builder.create()
                    .name(name)
                    .display(results.get("display"))
                    .creatorName(results.get("creator_name"))
                    .build()
        ) == null;
    }

    public Tag getTag(String name) {
        return tags.get(name);
    }
}
