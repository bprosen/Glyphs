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

public class Tags implements CommandExecutor {

    public static final String CREATE_COMMAND_TEXT = "create";
    public static final String DELETE_COMMAND_TEXT = "delete";
    public static final String SET_COMMAND_TEXT = "set";
    public static final String TITLE_COMMAND_TEXT = "title";
    public static final String RESET_COMMAND_TEXT = "reset";

    private HashMap<String, CommandHandler> subCommands;

    public Tags() {
        subCommands = new HashMap<String, CommandHandler>() {{
            put(CREATE_COMMAND_TEXT, new TagsCreate());
            put(DELETE_COMMAND_TEXT, new TagsDelete());
            put(SET_COMMAND_TEXT, new TagsSet());
            put(TITLE_COMMAND_TEXT, new TagsTitle());
            put(RESET_COMMAND_TEXT, new TagsReset());
        }};
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] a) {
        if (sender.isOp() && a.length > 0 && !(subCommands.containsKey(a[0]) && subCommands.get(a[0]).handle(sender, a))) {
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
        ChatUtils.sendMessages((Player) sender, LangUtils.tags_command_help);
    }
}
