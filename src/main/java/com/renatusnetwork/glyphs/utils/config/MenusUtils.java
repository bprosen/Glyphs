package com.renatusnetwork.glyphs.utils.config;

import com.renatusnetwork.glyphs.managers.ConfigManager;
import com.renatusnetwork.glyphs.objects.menus.Menu;
import com.renatusnetwork.glyphs.objects.menus.MenuItem;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MenusUtils {

    public static FileConfiguration getMenusConfig() { return ConfigManager.getInstance().get("menus"); }

    public static HashMap<String, Menu> getMenus() {
        FileConfiguration config = getMenusConfig();
        HashMap<String, Menu> menus = new HashMap<>();

        config.getKeys(false).forEach(key -> menus.put(key, getMenu(key)));

        return menus;
    }

    public static Menu getMenu(String menuName) {
        FileConfiguration config = getMenusConfig();

        int pageCount = config.getInt(menuName + ".page_count", 1);

        return Menu.Builder.create().name(menuName).pageCount(pageCount).build();
    }

    public static HashMap<Integer, MenuPage> getPages(Menu menu) {
        HashMap<Integer, MenuPage> pages = new HashMap<>();

        for (int i = 0; i < menu.getPageCount(); i++) {
            pages.put(i, getMenuPage(menu, i));
        }

        return pages;
    }

    public static MenuPage getMenuPage(Menu menu, int page) {
        String menuName = menu.getName();
        FileConfiguration config = getMenusConfig();

        if (config.isConfigurationSection(menuName + "." + page)) {
            String title = config.getString(menuName + "." + page + ".title");
            int size = config.getInt(menuName + "." + page + ".size");

            return MenuPage.Builder.create().menu(menu).title(title).size(size).build();
        }
        return null;
    }

    public static HashMap<Integer, MenuItem> getItems(MenuPage page) {
        HashMap<Integer, MenuItem> items = new HashMap<>();

        for (int i = 0; i < page.getSize(); i++) {
            items.put(i, getMenuItem(page, i));
        }

        return items;
    }

    public static MenuItem getMenuItem(MenuPage page, int slot) {
        FileConfiguration config = getMenusConfig();
        String menuName = page.getMenu().getName();

        if (config.isConfigurationSection(menuName + "." + page + "." + slot)) {
            Material material = Material.matchMaterial(config.getString(menuName + "." + page + "." + slot + ".material"));
            String title = config.getString(menuName + "." + page + "." + slot + ".title");
            List<String> lore = config.getStringList(menuName + "." + page + "." + slot + ".lore");

            ItemStack itemStack = new ItemStack(material);
            ItemMeta meta = itemStack.getItemMeta();

            meta.setDisplayName(ChatUtils.color(title));
            meta.setLore(lore.stream().map(ChatUtils::color).collect(Collectors.toList()));

            itemStack.setItemMeta(meta);

            return MenuItem.Builder.create().menuPage(page).item(itemStack).build();
        }
        return null;
    }
}
