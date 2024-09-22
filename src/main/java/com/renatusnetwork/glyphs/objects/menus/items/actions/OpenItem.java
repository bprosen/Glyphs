package com.renatusnetwork.glyphs.objects.menus.items.actions;

import com.renatusnetwork.glyphs.managers.MenuManager;
import com.renatusnetwork.glyphs.objects.menus.Menu;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OpenItem extends ActionItem {

    private String menu;
    public int pageNumber;

    public OpenItem(MenuPage menuPage, ItemStack item, String menu, int pageNumber) {
        super(menuPage, item);

        this.menu = menu;
        this.pageNumber = pageNumber;
    }

    public MenuPage getMenu() {
        Menu newMenu = MenuManager.getInstance().get(menu);
        return newMenu != null ? newMenu.getPage(pageNumber) : null;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = super.getItem().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();

        MenuPage page = getMenu();

        itemMeta.setDisplayName(ChatUtils.color(
                "Click to open &a&o" + (page != null ? page.getTitle() : "Undefined") + (page != null && page.getNumber() > 0 ? " page " + page.getNumber() : "")
        ));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public void click(PlayerStats playerStats) {
        MenuPage page = getMenu();

        if (page != null) {
            MenuManager.getInstance().open(playerStats, page.getMenu(), page.getNumber());
        }
    }

    public static class Builder {
        private MenuPage menuPage;
        private ItemStack item;
        private String menu;
        private int pageNumber;

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

        public Builder menu(String menu) {
            this.menu = menu;
            return this;
        }

        public Builder pageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public ActionItem build() {
            return new OpenItem(menuPage, item, menu, pageNumber);
        }
    }
}
