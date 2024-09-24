package com.renatusnetwork.glyphs.objects.menus.items.actions;

import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.menus.MenuPage;
import com.renatusnetwork.glyphs.objects.menus.items.ActionItem;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.inventory.ItemStack;

public class ResetItem extends ActionItem {

    public ResetItem(MenuPage menuPage, ItemStack item) {
        super(menuPage, item);
    }

    @Override
    public void click(PlayerStats playerStats) {
        playerStats.getPlayer().closeInventory();
        playerStats.getPlayer().sendMessage(
                playerStats.hasCurrentTag() ?
                ChatUtils.color(LangUtils.tag_reset_self_succeed) : ChatUtils.color(LangUtils.tag_reset_self_no_tag)
        );
        PlayerStatsManager.getInstance().resetTag(playerStats);
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
            return new ResetItem(menuPage, item);
        }
    }
}
