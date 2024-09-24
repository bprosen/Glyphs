package com.renatusnetwork.glyphs.commands.tags;

import com.renatusnetwork.glyphs.commands.CommandHandler;
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

public class TagsCommand implements CommandExecutor {

    public static final String CREATE_COMMAND_TEXT = "create";
    public static final String DELETE_COMMAND_TEXT = "delete";
    public static final String SET_COMMAND_TEXT = "set";
    public static final String TITLE_COMMAND_TEXT = "title";
    public static final String RESET_COMMAND_TEXT = "reset";
    public static final String REVEAL_COMMAND_TEXT = "reveal";
    public static final String INFO_COMMAND_TEXT = "info";

    private HashMap<String, CommandHandler> subCommands;

    public TagsCommand() {
        subCommands = new HashMap<String, CommandHandler>() {{
            put(CREATE_COMMAND_TEXT, new TagsCreate());
            put(DELETE_COMMAND_TEXT, new TagsDelete());
            put(SET_COMMAND_TEXT, new TagsSet());
            put(TITLE_COMMAND_TEXT, new TagsTitle());
            put(RESET_COMMAND_TEXT, new TagsReset());
            put(REVEAL_COMMAND_TEXT, new TagsReveal());
            put(INFO_COMMAND_TEXT, new TagsInfo());
        }};
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] a) {
        if (sender.hasPermission(ConfigUtils.admin_node_permission) && a.length > 0 &&
            !(subCommands.containsKey(a[0]) && subCommands.get(a[0]).handle(sender, a))) {
            sendHelp(sender);
        } else if (sender instanceof Player && a.length == 0) {
            // Run main command
            PlayerStats playerStats = PlayerStatsManager.getInstance().get((Player) sender);

            if (playerStats != null) {
                MenuManager.getInstance().open(playerStats, MenuManager.getInstance().get(ConfigUtils.main_menu_name));
            } else {
                sendHelp(sender);
            }
        }
        return false;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatUtils.color(LangUtils.tags_command_help));
    }
}
