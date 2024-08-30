package com.renatusnetwork.glyphs.objects.players;

import com.renatusnetwork.glyphs.objects.menus.Menu;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.UUID;

public class PlayerStats {

    private Player player;
    private Tag currentTag;
    private HashSet<Tag> ownedTags;

    public PlayerStats(Player player, Tag currentTag, HashSet<Tag> ownedTags) {
        this.player = player;
        this.currentTag = currentTag;
        this.ownedTags = ownedTags;
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

    public void openInventory(Inventory inventory) {
        player.openInventory(inventory);
    }

    public static class Builder {
        private Player player;
        private Tag currentTag;
        private HashSet<Tag> ownedTags;

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

        public Builder ownedTags(HashSet<Tag> ownedTags) {
            this.ownedTags = ownedTags;
            return this;
        }

        public PlayerStats build() {
            return new PlayerStats(player, currentTag, ownedTags);
        }
    }
}
