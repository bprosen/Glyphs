package com.renatusnetwork.glyphs.objects.menus.items;

import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import org.bukkit.inventory.ItemStack;

public abstract class ActionItem extends MenuItem {

    public ActionItem(MenuPage menuPage, ItemStack item) {
        super(menuPage, item);
    }

    public abstract void click(PlayerStats playerStats);
}
