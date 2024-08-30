package com.renatusnetwork.glyphs.commands.tags.subcommands;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagsReset implements CommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] a) {
        if (a.length == 2) {
            Player player = Bukkit.getPlayer(a[1]);

            if (player == null) {
                sender.sendMessage(LangUtils.parse(LangUtils.player_offline, a[2]));
                return true;
            }

            PlayerStats playerStats = PlayerStatsManager.getInstance().get(player);
            PlayerStatsManager.getInstance().resetTag(playerStats);

            sender.sendMessage(LangUtils.parse(LangUtils.tag_reset, player.getDisplayName()));
            return true;
        }

        return false;
    }
}
