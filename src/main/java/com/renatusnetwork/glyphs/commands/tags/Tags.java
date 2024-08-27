package com.renatusnetwork.glyphs.commands.tags;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.commands.tags.subcommands.TagsCreate;
import com.renatusnetwork.glyphs.commands.tags.subcommands.TagsDelete;
import com.renatusnetwork.glyphs.commands.tags.subcommands.TagsSet;
import com.renatusnetwork.glyphs.commands.tags.subcommands.TagsTitle;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.config.ConfigUtils;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.HashMap;

public class Tags implements CommandExecutor {

    private HashMap<String, CommandHandler> subCommands;

    public Tags() {
        subCommands = new HashMap<String, CommandHandler>() {{
            put("create", new TagsCreate());
            put("delete", new TagsDelete());
            put("set", new TagsSet());
            put("title", new TagsTitle());
        }};
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] a) {
        if (sender.isOp() && a.length > 0 && (!subCommands.containsKey(a[1]) || subCommands.get(a[1]).handle(sender, a))) {
            sendHelp(sender);
        }
        return false;
    }

    private void sendHelp(CommandSender sender) {
        LangUtils.tags_command_help.forEach(message -> sender.sendMessage(ChatUtils.color(message)));
    }
}
