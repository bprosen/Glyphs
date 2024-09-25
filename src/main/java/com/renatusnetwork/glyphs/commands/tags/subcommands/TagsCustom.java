package com.renatusnetwork.glyphs.commands.tags.subcommands;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.CommandSender;

public class TagsCustom implements CommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] a) {
        if (a.length == 2) {
            TagsManager tagsManager = TagsManager.getInstance();
            String tagName = a[1].toLowerCase();
            Tag tag = tagsManager.get(tagName);

            if (tag == null) {
                sender.sendMessage(LangUtils.parse(LangUtils.tag_exists, tagName));
                return true;
            }

            tagsManager.toggleCustom(tag);
            sender.sendMessage(LangUtils.parse(LangUtils.tag_toggle_custom, tagName, (tag.isCustom() ? "&atrue" : "&cfalse")));
            return true;
        }

        return false;
    }
}
