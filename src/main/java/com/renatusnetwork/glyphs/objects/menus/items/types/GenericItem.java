package com.renatusnetwork.glyphs.objects.menus.items.types;

import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.MenuItem;
import org.bukkit.inventory.ItemStack;

public class GenericItem extends MenuItem {

    public GenericItem(MenuPage menuPage, ItemStack item) {
        super(menuPage, item);
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

        public MenuItem build() {
            return new GenericItem(menuPage, item);
        }
    }
}
