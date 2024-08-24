package com.renatusnetwork.glyphs.commands;

import org.bukkit.command.CommandSender;

public interface CommandHandler {

    boolean handle(CommandSender sender, String[] a);
}
