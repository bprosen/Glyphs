package com.renatusnetwork.glyphs.objects.players;

import com.renatusnetwork.glyphs.objects.tags.Tag;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class PlayerStats {

    private Player player;
    private Tag currentTag;
    private HashSet<Tag> ownedTags;

    public PlayerStats(Player player, Tag currentTag, HashSet<Tag> ownedTags) {
        this.player = player;
        this.currentTag = currentTag;
        this.ownedTags = ownedTags;
    }

    public Tag getCurrentTag() {
        return currentTag;
    }

    public static class Builder {
        private Player player;
        private Tag currentTag;
        private HashSet<Tag> ownedTags;

        public static Builder create() {
            return new Builder();
        }

        public Builder setPlayer(Player player) {
            this.player = player;
            return this;
        }

        public Builder setCurrentTag(Tag currentTag) {
            this.currentTag = currentTag;
            return this;
        }

        public Builder setOwnedTags(HashSet<Tag> ownedTags) {
            this.ownedTags = ownedTags;
            return this;
        }

        public PlayerStats build() {
            return new PlayerStats(player, currentTag, ownedTags);
        }
    }
}
