package com.renatusnetwork.glyphs.commands.tags.subcommands;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.utils.TimeUtils;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.CommandSender;
import com.renatusnetwork.glyphs.objects.tags.Tag;

public class TagsInfo implements CommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] a) {

        if (a.length == 2) {
            String tagName = a[1].toLowerCase();
            Tag tag = TagsManager.getInstance().get(tagName);

            if (tag == null) {
                sender.sendMessage(LangUtils.parse(LangUtils.tag_not_exists, tagName));
                return true;
            }

            sender.sendMessage(
                    LangUtils.parse(
                            LangUtils.tag_info,
                            tagName,
                            tagName,
                            tag.getTitle(),
                            tag.getCreator(),
                            TimeUtils.parseTime(tag.getCreationDate())
                    ));
            return true;
        }
        return false;
    }
}
