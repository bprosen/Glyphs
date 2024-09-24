package com.renatusnetwork.glyphs.objects.menus.items.actions;

import com.renatusnetwork.glyphs.managers.MenuManager;
import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import org.bukkit.inventory.meta.ItemMeta;

public class TagItem extends ActionItem {

    private Tag tag;

    public TagItem(MenuPage menuPage, ItemStack item, Tag tag) {
        super(menuPage, item);

        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public void click(PlayerStats playerStats) {
        if (playerStats.hasTag(tag)) {
            playerStats.closeInventory();
            PlayerStatsManager.getInstance().setTag(playerStats, tag);
            playerStats.sendMessage(LangUtils.parse(LangUtils.tag_set_self, tag.getTitle()));
        }
    }

    public void favorite(PlayerStats playerStats, int slot) {
        if (playerStats.hasTag(tag)) {
            boolean favorited = playerStats.isFavorite(tag);
            PlayerStatsManager playerStatsManager = PlayerStatsManager.getInstance();

            if (favorited) {
                playerStatsManager.removeFavorite(playerStats, tag);
            } else {
                playerStatsManager.addFavorite(playerStats, tag);
            }

            MenuManager.getInstance().reloadOpenedMenuItem(playerStats, slot);
        }
    }
    public static class Builder {
        private MenuPage menuPage;
        private ItemStack item;
        private Tag tag;

        public static Builder create() {
            return new Builder();
        }

        public Builder menuPage(MenuPage menuPage) {
            this.menuPage = menuPage;
            return this;
        }

        public Builder item(ItemStack item) {
            this.item = item;
            return this;
        }

        public Builder tag(Tag tag) {
            this.tag = tag;
            return this;
        }

        public ActionItem build() {
            return new TagItem(menuPage, item, tag);
        }
    }
}
