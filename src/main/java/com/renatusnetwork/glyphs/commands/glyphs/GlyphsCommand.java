package com.renatusnetwork.glyphs.commands.glyphs;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.commands.glyphs.subcommands.Reload;
import com.renatusnetwork.glyphs.commands.tags.subcommands.*;
import com.renatusnetwork.glyphs.managers.MenuManager;
import com.renatusnetwork.glyphs.managers.PlayerStatsManager;
import com.renatusnetwork.glyphs.objects.players.PlayerStats;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.config.ConfigUtils;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GlyphsCommand implements CommandExecutor {

    public static final String RELOAD_COMMAND_TEXT = "reload";

    private HashMap<String, CommandHandler> subCommands;

    public GlyphsCommand() {
        subCommands = new HashMap<String, CommandHandler>() {{
            put(RELOAD_COMMAND_TEXT, new Reload());
        }};
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] a) {
        if (sender.hasPermission(ConfigUtils.admin_node_permission) && a.length == 0 ||
            !(subCommands.containsKey(a[0]) && subCommands.get(a[0]).handle(sender, a))) {
            sendHelp(sender);
        }
        return false;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatUtils.color(LangUtils.glyphs_command_help));
    }
}
