package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.objects.menus.types.FilterType;
import com.renatusnetwork.glyphs.objects.menus.types.SortType;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.database.tables.TagsUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TagsManager {

    private static TagsManager instance;

    public static TagsManager getInstance() {
        return instance == null ? instance = new TagsManager() : instance;
    }

    private final HashMap<String, Tag> tags;

    public TagsManager() {
        this.tags = new HashMap<>();

        init();
    }

    private void init() {
        List<HashMap<String, String>> results = TagsUtils.getTags();

        results.forEach(result -> tags.put(result.get("name"),
                Tag.Builder.create()
                    .name(result.get("name"))
                    .title(result.get("title"))
                    .creator(result.get("creator_name"))
                    .creationDate(Integer.parseInt(result.get("creation_date")))
                    .custom(Integer.parseInt(result.get("custom")) == 1)
                    .build()
        ));
    }

    public boolean exists(String name) {
        return tags.containsKey(name);
    }

    public void create(String name, String creator) {
        int creationDate = (int) (System.currentTimeMillis() / 1000);

        tags.put(name, Tag.Builder.create().name(name).creator(creator).creationDate(creationDate).build());
        TagsUtils.createTag(name, creator, creationDate);
    }

    public void delete(String name) {
        tags.remove(name);
        TagsUtils.deleteTag(name);
    }

    public Tag get(String name) {
        return tags.get(name);
    }

    public Tag getFromTitle(String title) {
        String strippedTitle = ChatUtils.removeColorCodes(title);

        return tags.values().stream().filter(tag ->
                    tag.hasTitle() &&
                    tag.getStrippedTitle().equalsIgnoreCase(strippedTitle)
                ).findFirst().orElse(null);
    }

    public void setTitle(Tag tag, String title) {
        tag.setTitle(title);
        TagsUtils.setTitle(tag.getName(), title);
    }

    public void toggleCustom(Tag tag) {
        tag.toggleCustom();
        TagsUtils.toggleCustom(tag.getName());
    }

    public List<Tag> search(String query) {
        String queryLowerCase = query.toLowerCase();

        return tags.values().stream().filter(
                tag -> tag.hasTitle() && tag.getStrippedTitle().toLowerCase().contains(queryLowerCase)
            ).collect(Collectors.toList());
    }

    public List<Tag> filterTags(PlayerStats playerStats, HashMap<FilterType, Boolean> filters) {
        boolean showOwned = filters.get(FilterType.OWNED);
        boolean showUnowned = filters.get(FilterType.UNOWNED);
        boolean showCustom = filters.get(FilterType.CUSTOM);

        if (!showOwned || !showUnowned || !showCustom) {
            return tags.values().stream().filter(
                    tag -> {
                        boolean hasTag = playerStats.hasTag(tag);

                        return (showOwned && hasTag) ||
                               (showUnowned && !hasTag) ||
                               (showCustom && tag.isCustom());
                    }
                ).collect(Collectors.toList());
        } else {
            return new ArrayList<>(tags.values());
        }
    }
}
