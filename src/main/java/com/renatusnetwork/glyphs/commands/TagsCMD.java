package com.renatusnetwork.glyphs.commands;

import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TagsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] a) {
        if (sender.isOp()) {
            TagsManager tagsManager = TagsManager.getInstance();

            if (a.length == 2 && a[0].equalsIgnoreCase("create")) {
                String tagName = a[1].toLowerCase();

                if (tagsManager.exists(tagName)) {
                    sender.sendMessage(LangUtils.parse(LangUtils.tag_exists, tagName));
                    return false;
                }

                tagsManager.create(tagName, sender.getName());
                sender.sendMessage(LangUtils.parse(LangUtils.tag_created, tagName));
                return true;
            }
        }
        return false;
    }
}
