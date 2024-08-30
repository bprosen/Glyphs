package com.renatusnetwork.glyphs.commands.tags.subcommands;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagsSet implements CommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] a) {

        if (a.length == 3) {
            TagsManager tagsManager = TagsManager.getInstance();
            String tagName = a[2].toLowerCase();
            Player player = Bukkit.getPlayer(a[1]);

            if (player == null) {
                sender.sendMessage(LangUtils.parse(LangUtils.player_offline, a[2]));
                return true;
            }

            Tag tag = tagsManager.get(tagName);

            if (tag == null) {
                sender.sendMessage(LangUtils.parse(LangUtils.tag_not_exists, tagName));
                return true;
            }

            PlayerStats playerStats = PlayerStatsManager.getInstance().get(player);

            PlayerStatsManager.getInstance().setTag(playerStats, tag);
            sender.sendMessage(LangUtils.parse(LangUtils.tag_set, player.getDisplayName(), tagName));
            return true;
        }
        return false;
    }
}
