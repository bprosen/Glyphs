package com.renatusnetwork.glyphs.objects.menus;

import com.renatusnetwork.glyphs.utils.ChatUtils;

import java.util.HashMap;

public class MenuPage {

    private Menu menu;
    private String title;
    private int size;
    private HashMap<Integer, MenuItem> items;

    public MenuPage(Menu menu, String title, int size) {
        this.menu = menu;
        this.title = title;
        this.size = size;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getSize() {
        return size;
    }

    public String getTitleColored() {
        return ChatUtils.color(title);
    }

    public static class Builder {
        private Menu menu;
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

        public MenuPage build() {
            return new MenuPage(menu, title, size);
        }
    }
}
