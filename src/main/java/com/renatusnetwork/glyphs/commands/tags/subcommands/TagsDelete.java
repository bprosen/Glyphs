package com.renatusnetwork.glyphs.commands.tags.subcommands;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.CommandSender;

public class TagsDelete implements CommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] a) {
        if (a.length == 2) {
            TagsManager tagsManager = TagsManager.getInstance();
            String tagName = a[1].toLowerCase();

            if (!tagsManager.exists(tagName)) {
                sender.sendMessage(LangUtils.parse(LangUtils.tag_not_exists, tagName));
                return false;
            }

            tagsManager.delete(tagName);
            sender.sendMessage(LangUtils.parse(LangUtils.tag_deleted, tagName));
            return true;
        }
        return false;
    }
}
