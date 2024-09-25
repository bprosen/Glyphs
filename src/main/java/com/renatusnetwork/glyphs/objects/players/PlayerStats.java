package com.renatusnetwork.glyphs.objects.players;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.menus.types.FilterType;
import com.renatusnetwork.glyphs.objects.menus.types.SortType;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.config.ConfigUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class PlayerStats {

    private Player player;
    private Tag currentTag;
    private HashSet<Tag> favorites;
    private HashMap<FilterType, Boolean> filters;
    private SortType sortBy;
    private Inventory builtInventory;

    public PlayerStats(Player player, Tag currentTag, HashSet<Tag> favorites, HashMap<FilterType, Boolean> filters, SortType sortBy) {
        this.player = player;
        this.currentTag = currentTag;
        this.favorites = favorites;
        this.filters = filters;
        this.sortBy = sortBy;

        buildInventory();
    }

    private void buildInventory() {
        List<Tag> filteredTags = TagsManager.getInstance().filterTags(this, filters);

        filteredTags.sort((first, second) -> {
            switch (sortBy) {
                case ALPHABETICAL:
                    return first.getStrippedTitle().compareTo(second.getStrippedTitle());
                case NEWEST:
                    return first.getCreationDate() - second.getCreationDate();
                case OLDEST:
                    return -(first.getCreationDate() - second.getCreationDate());
            }
            return 0;
        });
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }

    public void setCurrentTag(Tag currentTag) {
        this.currentTag = currentTag;
    }

    public void resetCurrentTag() {
        this.currentTag = null;
    }

    public Tag getCurrentTag() {
        return currentTag;
    }

    public boolean hasCurrentTag() { return currentTag != null; }

    public void openMainMenu() {
        player.openInventory(builtInventory);
    }

    public void openInventory(Inventory inventory) {
        player.openInventory(inventory);
    }

    public boolean hasTag(Tag tag) {
        return player.hasPermission(ConfigUtils.permission_node_prefix + tag.getName());
    }

    public void setSortBy(SortType sortBy) {
        this.sortBy = sortBy;
    }

    public SortType getSortBy() {
        return sortBy;
    }

    public void toggleFilter(FilterType filter) {
        filters.put(filter, filters.get(filter));
    }

    public boolean isFavorite(Tag tag) {
        return favorites.contains(tag);
    }

    public void addFavorite(Tag tag) {
        favorites.add(tag);
    }

    public void removeFavorite(Tag tag) {
        favorites.remove(tag);
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public void closeInventory() {
        player.closeInventory();
    }

    public static class Builder {
        private Player player;
        private Tag currentTag;
        private HashSet<Tag> favorites;
        private HashMap<FilterType, Boolean> filters;
        private SortType sortBy;

        public static Builder create() {
            return new Builder();
        }

        public Builder player(Player player) {
            this.player = player;
            return this;
        }

        public Builder currentTag(Tag currentTag) {
            this.currentTag = currentTag;
            return this;
        }

        public Builder favorites(HashSet<Tag> favorites) {
            this.favorites = favorites;
            return this;
        }

        public Builder filters(HashMap<FilterType, Boolean> filters) {
            this.filters = filters;
            return this;
        }

        public Builder sortBy(SortType sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public PlayerStats build() {
            return new PlayerStats(player, currentTag, favorites, filters, sortBy);
        }
    }
}
