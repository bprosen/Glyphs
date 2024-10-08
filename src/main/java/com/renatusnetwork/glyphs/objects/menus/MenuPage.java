package com.renatusnetwork.glyphs.objects.menus;

import com.renatusnetwork.glyphs.objects.menus.items.MenuItem;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.config.MenusUtils;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class MenuPage {

    private Menu menu;
    private int number;
    private String title;
    private int size;
    private HashMap<Integer, MenuItem> items;

    public MenuPage(Menu menu, int number, String title, int size) {
        this.menu = menu;
        this.number = number;
        this.title = title;
        this.size = size;
        this.items = MenusUtils.getItems(this);
    }

    public Menu getMenu() {
        return menu;
    }

    public MenuItem getItem(int slot) {
        return items.get(slot);
    }

    public int getSize() {
        return size;
    }

    public int getNumber() { return number; }

    public String getTitleColored() {
        return ChatUtils.color(title);
    }

    public String getTitle() { return title; }

    public HashMap<Integer, MenuItem> getItems() { return items; }

    public static class Builder {
        private Menu menu;
        private int number;
        private String title;
        private int size;

        public static Builder create() {
            return new Builder();
        }

        public Builder menu(Menu menu) {
            this.menu = menu;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public MenuPage build() {
            return new MenuPage(menu, number, title, size);
        }
    }
}
