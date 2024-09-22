package com.renatusnetwork.glyphs.objects.menus.items.actions;

import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import org.bukkit.inventory.ItemStack;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import org.bukkit.inventory.meta.ItemMeta;

public class TagItem extends ActionItem {

    private Tag tag;

    public TagItem(MenuPage menuPage, ItemStack item, Tag tag) {
        super(menuPage, item);

        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = super.getItem().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(ChatUtils.color(tag.getTitle() + "&7 Tag"));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public void click(PlayerStats playerStats) {
        if (playerStats.hasTag(tag)) {
            playerStats.getPlayer().closeInventory();
            PlayerStatsManager.getInstance().setTag(playerStats, tag);
            playerStats.getPlayer().sendMessage(ChatUtils.color("&7You set your tag to &a" + tag.getTitle()));
        }
    }

    public static class Builder {
        private MenuPage menuPage;
        private ItemStack item;
        private Tag tag;

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

        public Builder tag(Tag tag) {
            this.tag = tag;
            return this;
        }

        public ActionItem build() {
            return new TagItem(menuPage, item, tag);
        }
    }
}
