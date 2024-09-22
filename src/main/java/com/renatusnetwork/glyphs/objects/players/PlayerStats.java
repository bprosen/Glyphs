package com.renatusnetwork.glyphs.objects.players;

import com.renatusnetwork.glyphs.objects.menus.Menu;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.config.ConfigUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.UUID;

public class PlayerStats {

    private Player player;
    private Tag currentTag;

    public PlayerStats(Player player, Tag currentTag) {
        this.player = player;
        this.currentTag = currentTag;
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

    public static class Builder {
        private Player player;
        private Tag currentTag;

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

        public PlayerStats build() {
            return new PlayerStats(player, currentTag);
        }
    }
}
