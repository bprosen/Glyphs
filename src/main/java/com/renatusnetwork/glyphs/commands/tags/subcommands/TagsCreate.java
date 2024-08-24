package com.renatusnetwork.glyphs.commands.tags.subcommands;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.CommandSender;

public class TagsCreate implements CommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] a) {
        if (a.length == 2 && a[0].equalsIgnoreCase("create")) {
            TagsManager tagsManager = TagsManager.getInstance();
            String tagName = a[1].toLowerCase();

            if (tagsManager.exists(tagName)) {
                sender.sendMessage(LangUtils.parse(LangUtils.tag_exists, tagName));
                return false;
            }

            tagsManager.create(tagName, sender.getName());
            sender.sendMessage(LangUtils.parse(LangUtils.tag_created, tagName));
            return true;
        }

        return false;
    }
}
