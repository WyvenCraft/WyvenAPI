package com.wyvencraft.api.managers;

import com.wyvencraft.api.utils.MessageUtil;
import com.wyvencraft.api.utils.Replacer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface ILangManager {

    String getLanguage();

    String getMessage(String msg);

    String getLocalizedMessage(final Player player, final String key);

    void sendLocalizedMessage(final Player player, final String key, final Replacer... replacerArray);

    default String getMessageColored(final String key) {
        final String message = this.getMessage(key);
        if (message == null || message.isEmpty()) {
            return "";
        }
        return MessageUtil.color(message);
    }

    default String getMessageColoredWithPrefix(final String key) {
        final String message = this.getMessageColored(key);
        if (message == null || message.isEmpty()) {
            return "";
        }
        final String prefix = this.getMessageColored("prefixes.plugin");
        if (prefix == null || prefix.isEmpty()) {
            return message;
        }
        return prefix + " " + message;
    }

    default void sendMessage(final CommandSender sender, final String... messageArray) {
        for (final String message : messageArray) {
            if (message != null) {
                if (!message.isEmpty()) {
                    sender.sendMessage(message);
                }
            }
        }
    }
}
