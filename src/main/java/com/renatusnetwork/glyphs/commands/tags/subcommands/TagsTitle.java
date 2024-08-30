package com.renatusnetwork.glyphs.commands.tags.subcommands;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TagsTitle implements CommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] a) {

        if (a.length >= 3) {
            TagsManager tagsManager = TagsManager.getInstance();
            String tagName = a[1].toLowerCase();
            String title = Arrays.stream(a, 2, a.length).collect(Collectors.joining(" "));
            Tag tag = tagsManager.get(tagName);

            if (tag == null) {
                sender.sendMessage(LangUtils.parse(LangUtils.tag_not_exists, tagName));
                return true;
            }

            tagsManager.setTitle(tag, title);
            sender.sendMessage(LangUtils.parse(LangUtils.tag_set_title, tagName, title));
            return true;
        }
        return false;
    }
}
