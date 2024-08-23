package com.renatusnetwork.glyphs.objects.menus;

import com.renatusnetwork.glyphs.utils.config.MenusUtils;

import java.util.HashMap;

public class Menu {

    private String name;
    private int pageCount;
    private HashMap<Integer, MenuPage> pages;

    public Menu(String name, int pageCount) {
        this.name = name;
        this.pageCount = pageCount;
        this.pages = MenusUtils.getPages(this);
    }

    public String getName() {
        return name;
    }

    public MenuPage getPage(int page) {
        return pages.get(page);
    }

    public int getPageCount() {
        return pageCount;
    }

    public static class Builder {
        private String name;
        private int pageCount;

        public static Builder create() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Menu build() {
            return new Menu(name, pageCount);
        }
    }
}
