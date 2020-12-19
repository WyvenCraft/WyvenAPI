package com.wyvencraft.api.managers;

import com.wyvencraft.api.utils.Replacer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface LangManager {

    String getLanguage();

    String getMessage(String msg);

    String getLocalizedMessage(final Player player, final String key);

    void sendLocalizedMessage(final Player player, final String key, final Replacer... replacerArray);
}
