package com.wyvencraft.api.managers;

import com.wyvencraft.api.utils.Replacer;
import org.bukkit.command.CommandSender;

public interface ILangManager {

    public String getLanguage();

    public void sendMessage(final CommandSender player, final String key, final Replacer... replacerArray);

    public void sendMessage(final CommandSender sender, final String... messageArray);

    public String getMessage(final String key, final Replacer... replacers);

    public String getMessage(final String key);
}
