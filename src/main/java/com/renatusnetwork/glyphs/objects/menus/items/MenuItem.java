package com.renatusnetwork.glyphs.objects.menus.items;

import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import org.bukkit.inventory.ItemStack;

public abstract class MenuItem {

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
}
