package com.renatusnetwork.glyphs.objects.menus.items;

import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import org.bukkit.inventory.ItemStack;

public class MenuItem {

    private MenuPage menuPage;
    private ItemStack item;

    public MenuItem(MenuPage menuPage, ItemStack item) {
        this.menuPage = menuPage;
        this.item = item;
    }

    public MenuPage getMenuPage() {
        return menuPage;
    }

    public ItemStack getItem() {
        return item;
    }

    public void click(PlayerStats playerStats) {
        // Empty body
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
            return new MenuItem(menuPage, item);
        }
    }
}
