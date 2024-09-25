package com.renatusnetwork.glyphs.objects.menus.items.actions;

import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.menus.types.SortType;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import org.bukkit.inventory.ItemStack;

public class SortBy extends ActionItem {

    public SortBy(MenuPage menuPage, ItemStack item) {
        super(menuPage, item);
    }

    @Override
    public void click(PlayerStats playerStats) {
        SortType sortType = playerStats.getSortBy();

        for (int i = 0; i < SortType.values().length; i++) {
            if (SortType.values()[i] == sortType) {
                sortType = SortType.values()[(i + 1) % SortType.values().length];
                break;
            }
        }
        PlayerStatsManager.getInstance().setSortBy(playerStats, sortType);
    }

    public static class Builder {
        private MenuPage menuPage;
        private ItemStack item;

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

        public ActionItem build() {
            return new SortBy(menuPage, item);
        }
    }
}
