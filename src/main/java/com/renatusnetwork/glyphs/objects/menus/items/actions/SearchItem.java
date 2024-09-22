package com.renatusnetwork.glyphs.objects.menus.items.actions;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.config.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SearchItem extends ActionItem {

    public SearchItem(MenuPage menuPage, ItemStack item) {
        super(menuPage, item);
    }

    @Override
    public void click(PlayerStats playerStats) {
        AnvilGUI.Builder builder = new AnvilGUI.Builder();

        ItemStack barrierItems = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrierItems.getItemMeta();
        barrierMeta.setDisplayName(ChatUtils.color("Search tags"));
        barrierMeta.setLore(new ArrayList<String>() {{
            add(ChatUtils.color("&cInput must be at least 3 characters"));
        }});

        barrierItems.setItemMeta(barrierMeta);

        builder.plugin(Glyphs.getInstance())
                .itemLeft(barrierItems)
                .itemRight(barrierItems)
                .itemOutput(new ItemStack(Material.BOOK_AND_QUILL))
                .onClickAsync((slot, result) -> CompletableFuture.supplyAsync(() -> {
                    String levelText = result.getText();
                    List<Tag> filteredItems = TagsManager.getInstance().search(levelText);

                    if (slot != AnvilGUI.Slot.OUTPUT || levelText.length() < ConfigUtils.minimum_search_input ||
                        levelText.equalsIgnoreCase("Search tags") || filteredItems.isEmpty())
                        return Collections.emptyList();
                    return Arrays.asList(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() ->
                            filteredItems.forEach(item -> Bukkit.broadcastMessage(item.getTitle()))
                            // TODO: make open search results menu
                    ));
                }))
                .open(playerStats.getPlayer());
    }

    public static class Builder {
        private MenuPage menuPage;
        private ItemStack item;

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

        public ActionItem build() {
            return new SearchItem(menuPage, item);
        }
    }
}
