package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.objects.menus.Menu;
import com.renatusnetwork.glyphs.objects.menus.MenuHolder;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.views.CurrentTagItem;
import com.renatusnetwork.glyphs.objects.menus.items.actions.TagItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.utils.config.MenusUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class MenuManager {

    private static MenuManager instance;

    public static MenuManager getInstance() {
        return instance == null ? instance = new MenuManager() : instance;
    }

    private HashMap<String, Menu> menus;

    private MenuManager() {
        load();
    }

    public void load() {
        this.menus = MenusUtils.getMenus();
    }

    public Menu get(String menuName) {
        return menus.get(menuName);
    }

    public void open(PlayerStats playerStats, Menu menu, int pageNumber) {
        pageNumber = Math.max(0, pageNumber);
        MenuPage menuPage = menu.getPage(pageNumber);
        Inventory inventory = Bukkit.createInventory(new MenuHolder(menuPage), menuPage.getSize(), menuPage.getTitleColored());

        if (inventory != null) {
            menuPage.getItems().forEach((key, value) -> {
                ItemStack itemStack = value.getItem();

                if (value instanceof TagItem) {
                    TagItem tagItem = (TagItem) value;
                    itemStack = MenusUtils.parseTagItem(tagItem, playerStats);
                } else if (value instanceof CurrentTagItem) {
                    CurrentTagItem currentTagItem = (CurrentTagItem) value;
                    itemStack = MenusUtils.parseCurrentTagItem(currentTagItem, playerStats);
                }

                inventory.setItem(key, itemStack);
            });

            playerStats.openInventory(inventory);
        }
    }

    public void open(PlayerStats playerStats, Menu menu) {
        open(playerStats, menu, 0);
    }
}
