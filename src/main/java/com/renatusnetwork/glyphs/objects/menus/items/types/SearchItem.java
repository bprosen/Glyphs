package com.renatusnetwork.glyphs.objects.menus.items.types;

import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.menus.items.MenuItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import org.bukkit.inventory.ItemStack;

public class SearchItem extends ActionItem {

    public SearchItem(MenuPage menuPage, ItemStack item) {
        super(menuPage, item);
    }

    @Override
    public void click(PlayerStats playerStats) {

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
            return new SearchItem(menuPage, item);
        }
    }
}
