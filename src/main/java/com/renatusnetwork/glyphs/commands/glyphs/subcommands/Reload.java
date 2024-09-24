package com.renatusnetwork.glyphs.commands.glyphs.subcommands;

import com.renatusnetwork.glyphs.Glyphs;
import com.renatusnetwork.glyphs.commands.CommandHandler;
import com.renatusnetwork.glyphs.utils.ChatUtils;
import com.renatusnetwork.glyphs.utils.config.LangUtils;
import org.bukkit.command.CommandSender;

public class Reload implements CommandHandler {

    @Override
    public boolean handle(CommandSender sender, String[] a) {
        Glyphs.getInstance().loadAllData();
        sender.sendMessage(ChatUtils.color(LangUtils.successful_reload));
        return true;
    }
}
