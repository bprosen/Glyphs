package com.renatusnetwork.glyphs.objects.menus.items;

import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import org.bukkit.inventory.ItemStack;

public abstract class ViewItem extends MenuItem {

    public ViewItem(MenuPage menuPage, ItemStack item) {
        super(menuPage, item);
    }
}
