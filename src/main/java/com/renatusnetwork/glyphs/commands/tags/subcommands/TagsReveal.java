package com.renatusnetwork.glyphs.commands.tags.subcommands;

import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.managers.TagsManager;
import com.renatusnetwork.glyphs.objects.tags.Tag;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TagsReveal implements CommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] a) {

        if (a.length >= 2) {
            String title = Arrays.stream(a, 1, a.length).collect(Collectors.joining(" "));
            Tag tag = TagsManager.getInstance().getFromTitle(title);

            if (tag == null) {
                sender.sendMessage(LangUtils.parse(LangUtils.tag_title_not_exists, title));
                return true;
            }

            sender.sendMessage(LangUtils.parse(LangUtils.tag_reveal_title, title, tag.getName()));
            return true;
        }
        return false;
    }
}
