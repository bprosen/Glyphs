package com.renatusnetwork.glyphs.utils.config;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.managers.ConfigManager;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.menus.items.GenericItem;
import com.renatusnetwork.glyphs.objects.menus.items.views.CurrentTagItem;
import com.renatusnetwork.glyphs.objects.menus.types.ActionType;
import com.renatusnetwork.glyphs.objects.menus.Menu;
import com.renatusnetwork.glyphs.objects.menus.types.ViewType;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.menus.items.ViewItem;
import com.renatusnetwork.glyphs.objects.menus.items.actions.*;
import com.renatusnetwork.glyphs.objects.menus.items.MenuItem;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
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
        FileConfiguration config = getMenusConfig();

        config.getConfigurationSection(page.getMenu().getName() + "." + page.getNumber()).getKeys(false).forEach(key -> {
            if (ParseUtils.isInteger(key)) {
                int slot = Integer.parseInt(key);
                items.put(slot, getMenuItem(page, slot));
            } else {
                // support for being able to mass set an item (ex: 0-15,24,19,4-2)
                Arrays.asList(key.split(",")).forEach(commaSplit -> {
                    if (commaSplit.contains("-")) {
                        String[] subRange = commaSplit.split("-");

                        // make sure each side of the - is an int
                        if (ParseUtils.isInteger(subRange[0]) && ParseUtils.isInteger(subRange[1])) {
                            int from = Integer.parseInt(subRange[0]);
                            int to = Integer.parseInt(subRange[1]);

                            for (int i = from; i <= to; i++) {
                                items.put(i, getMenuItem(page, i));
                            }
                        }
                    } else if (ParseUtils.isInteger(commaSplit)) {
                        int slot = Integer.parseInt(commaSplit);
                        items.put(slot, getMenuItem(page, slot));
                    }
                });
            }
        });

        return items;
    }

    public static MenuItem getMenuItem(MenuPage page, int slot) {
        FileConfiguration config = getMenusConfig();
        String menuName = page.getMenu().getName();
        int pageNumber = page.getNumber();
        String slotPath = buildSlotPath(menuName, pageNumber, slot);

        if (config.isConfigurationSection(slotPath)) {
            String materialString = config.getString(slotPath + ".material");

            Material material = materialString != null ? Material.matchMaterial(materialString) : ConfigUtils.menu_default_material;
            ItemStack itemStack = new ItemStack(material);
            ItemMeta meta = itemStack.getItemMeta();

            String title = config.getString(slotPath + ".title");
            List<String> lore = config.getStringList(slotPath + ".lore");

            meta.setDisplayName(title != null ? ChatUtils.color(title) : "");
            meta.setLore(lore.stream().map(ChatUtils::color).collect(Collectors.toList()));

            itemStack.setItemMeta(meta);

            if (config.isConfigurationSection(slotPath + ".view")) {
                return parseViewItem(page, slot, itemStack);
            } else if (config.isConfigurationSection(slotPath + ".action")) {
                return parseActionItem(page, slot, itemStack);
            } else {
                // Normal type
                return GenericItem.Builder.create().menuPage(page).item(itemStack).build();
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

    public static ItemStack parseCurrentTagItem(CurrentTagItem currentTagItem, PlayerStats playerStats) {
        ItemStack item = currentTagItem.getItem().clone();
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(playerStats.hasCurrentTag() ?
                ChatUtils.color("&7Current tag: " + playerStats.getCurrentTag().getTitle()) :
                ChatUtils.color("&cNo current tag"));
        item.setItemMeta(meta);

        return item;
    }

    private static ActionItem parseActionItem(MenuPage page, int slotNumber, ItemStack itemStack) {
        FileConfiguration config = getMenusConfig();
        String slotActionPath = buildSlotPath(page.getMenu().getName(), page.getNumber(), slotNumber) + ".action";
        String type = config.getString(slotActionPath + ".type");

        try {
            ActionType actionType = ActionType.valueOf(type.toUpperCase());

            switch (actionType) {
                case OPEN:
                    return OpenItem.Builder.create()
                            .menuPage(page)
                            .item(itemStack)
                            .menu(config.getString(slotActionPath + ".menu"))
                            .pageNumber(config.getInt(slotActionPath + ".page"))
                            .build();
                case SEARCH:
                    return SearchItem.Builder.create()
                            .menuPage(page)
                            .item(itemStack)
                            .build();
                case TAG:
                    return TagItem.Builder.create()
                            .menuPage(page)
                            .item(itemStack)
                            .tag(TagsManager.getInstance().get(config.getString(slotActionPath + ".tag")))
                            .build();
                case RESET:
                    return ResetItem.Builder.create()
                            .menuPage(page)
                            .item(itemStack)
                            .build();
            }

        } catch (IllegalArgumentException exception) {
            Glyphs.getLog().info("Could not parse action type: " + type);
            exception.printStackTrace();
        }
        return null;
    }

    private static ViewItem parseViewItem(MenuPage page, int slotNumber, ItemStack itemStack) {
        FileConfiguration config = getMenusConfig();
        String slotViewPath = buildSlotPath(page.getMenu().getName(), page.getNumber(), slotNumber) + ".view";
        String type = config.getString(slotViewPath + ".type");

        try {
            ViewType viewType = ViewType.valueOf(type.toUpperCase());

            switch (viewType) {
                case CURRENT_TAG:
                    return CurrentTagItem.Builder.create()
                            .item(itemStack)
                            .build();
            }

        } catch (IllegalArgumentException exception) {
            Glyphs.getLog().info("Could not parse action type: " + type);
            exception.printStackTrace();
        }
        return null;
    }

    private static String buildSlotPath(String menuName, int pageNumber, int slot) {
        return menuName + "." + pageNumber + "." + slot;
    }
}
