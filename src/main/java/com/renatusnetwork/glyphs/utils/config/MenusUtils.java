package com.renatusnetwork.glyphs.utils.config;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.managers.ConfigManager;
import com.renatusnetwork.glyphs.managers.MenuManager;
import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.menus.Menu;
import com.renatusnetwork.glyphs.objects.menus.items.MenuItem;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.OpenItem;
import com.renatusnetwork.glyphs.objects.menus.items.TagItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.TimeUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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

            return MenuPage.Builder.create().menu(menu).title(title).size(size).number(page).build();
        }
        return null;
    }

    public static HashMap<Integer, MenuItem> getItems(MenuPage page) {
        HashMap<Integer, MenuItem> items = new HashMap<>();

        for (int i = 0; i < page.getSize(); i++) {
            MenuItem menuItem = getMenuItem(page, i);

            if (menuItem != null) {
                items.put(i, menuItem);
            }
        }

        return items;
    }

    public static MenuItem getMenuItem(MenuPage page, int slot) {
        FileConfiguration config = getMenusConfig();
        String menuName = page.getMenu().getName();
        int pageNumber = page.getNumber();

        if (config.isConfigurationSection(menuName + "." + pageNumber + "." + slot)) {
            String materialString = config.getString(menuName + "." + pageNumber + "." + slot + ".material");

            Material material = materialString != null ? Material.matchMaterial(materialString) : ConfigUtils.menu_default_material;
            ItemStack itemStack = new ItemStack(material);
            ItemMeta meta = itemStack.getItemMeta();

            // Tag type
            if (config.isSet(menuName + "." + pageNumber + "." + slot + ".tag")) {
                return TagItem.Builder.create()
                        .menuPage(page)
                        .item(itemStack)
                        .tag(TagsManager.getInstance().get(config.getString(menuName + "." + pageNumber + "." + slot + ".tag")))
                        .build();
            // Open type
            } else if (config.isConfigurationSection(menuName + "." + pageNumber + "." + slot + ".open")) {
                return OpenItem.Builder.create()
                        .menuPage(page)
                        .item(itemStack)
                        .menu(config.getString(menuName + "." + pageNumber + "." + slot + ".open.menu"))
                        .pageNumber(config.getInt(menuName + "." + pageNumber + "." + slot + ".open.page"))
                        .build();
            // Normal type
            } else {
                String title = config.getString(menuName + "." + pageNumber + "." + slot + ".title");
                List<String> lore = config.getStringList(menuName + "." + pageNumber + "." + slot + ".lore");

                meta.setDisplayName(ChatUtils.color(title));
                meta.setLore(lore.stream().map(ChatUtils::color).collect(Collectors.toList()));

                itemStack.setItemMeta(meta);

                return MenuItem.Builder.create().menuPage(page).item(itemStack).build();
            }
        }
        return null;
    }

    public static ItemStack parseTagItem(TagItem tagItem, PlayerStats playerStats) {
        Tag tag = tagItem.getTag();
        boolean hasTag = playerStats.hasTag(tag);

        ItemStack item = new ItemStack(hasTag ? ConfigUtils.menu_acquired_tag_material : ConfigUtils.menu_missing_tag_material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatUtils.color(tag.getTitle() + "&7 Tag"));
        meta.setLore(new ArrayList<String>() {{
            add(ChatUtils.color(hasTag ? "&aYou have access to this tag" : "&cYou do not have access to this tag"));

            if (playerStats.getPlayer().isOp()) {
                add("");
                add(ChatUtils.color("&a&oName: " + tag.getName()));
                add(ChatUtils.color("&a&oCreator: " + tag.getCreator()));
                add(ChatUtils.color("&a&oCreated At: " + TimeUtils.parseTime(tag.getCreationDate())));
            }
        }});

        item.setItemMeta(meta);

        return item;
    }
}
