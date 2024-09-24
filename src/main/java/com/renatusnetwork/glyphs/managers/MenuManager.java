package com.renatusnetwork.glyphs.managers;

import com.renatusnetwork.glyphs.objects.menus.Menu;
import com.renatusnetwork.glyphs.objects.menus.MenuHolder;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.MenuItem;
import com.renatusnetwork.glyphs.objects.menus.items.views.CurrentTagItem;
import com.renatusnetwork.glyphs.objects.menus.items.actions.TagItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.utils.config.MenusUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
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
            menuPage.getItems().forEach((key, value) -> setItemToInventory(playerStats, inventory, key, value));
            playerStats.openInventory(inventory);
        }
    }

    private void setItemToInventory(PlayerStats playerStats, Inventory inventory, int slot, MenuItem item) {
        ItemStack itemStack = item.getItem();

        if (item instanceof TagItem) {
            TagItem tagItem = (TagItem) item;
            itemStack = MenusUtils.parseTagItem(tagItem, playerStats);
        } else if (item instanceof CurrentTagItem) {
            CurrentTagItem currentTagItem = (CurrentTagItem) item;
            itemStack = MenusUtils.parseCurrentTagItem(currentTagItem, playerStats);
        }

        inventory.setItem(slot, itemStack);
    }

    public void open(PlayerStats playerStats, Menu menu) {
        open(playerStats, menu, 0);
    }

    public void reloadOpenedMenuItem(PlayerStats playerStats, int slot) {
        if (playerStats != null && playerStats.getPlayer().getOpenInventory() != null) {
            Inventory topInventory = playerStats.getPlayer().getOpenInventory().getTopInventory();
            InventoryHolder holder = topInventory.getHolder();

            if (holder instanceof MenuHolder) {
                MenuHolder menuHolder = (MenuHolder) holder;
                MenuItem menuItem = menuHolder.getMenuPage().getItem(slot);

                setItemToInventory(playerStats, topInventory, slot, menuItem);
            }
        }
    }
}
