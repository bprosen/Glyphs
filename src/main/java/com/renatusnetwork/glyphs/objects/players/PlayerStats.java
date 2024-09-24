package com.renatusnetwork.glyphs.objects.players;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.config.ConfigUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.HashSet;
import java.util.UUID;

public class PlayerStats {

    private Player player;
    private Tag currentTag;
    private HashSet<Tag> favorites;

    public PlayerStats(Player player, Tag currentTag, HashSet<Tag> favorites) {
        this.player = player;
        this.currentTag = currentTag;
        this.favorites = favorites;
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

    public void openInventory(Inventory inventory) {
        player.openInventory(inventory);
    }

    public boolean hasTag(Tag tag) {
        return player.hasPermission(ConfigUtils.permission_node_prefix + tag.getName());
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

        public PlayerStats build() {
            return new PlayerStats(player, currentTag, favorites);
        }
    }
}
