package com.renatusnetwork.glyphs.objects.menus.items.actions;

import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.menus.types.FilterType;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import org.bukkit.inventory.ItemStack;

public class FilterItem extends ActionItem {

    private FilterType type;

    public FilterItem(MenuPage menuPage, ItemStack item, FilterType type) {
        super(menuPage, item);

        this.type = type;
    }

    @Override
    public void click(PlayerStats playerStats) {
        PlayerStatsManager.getInstance().toggleFilter(playerStats, type);
    }

    public static class Builder {
        private MenuPage menuPage;
        private ItemStack item;
        private FilterType type;

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

        public Builder type(FilterType type) {
            this.type = type;
            return this;
        }

        public ActionItem build() {
            return new FilterItem(menuPage, item, type);
        }
    }
}
