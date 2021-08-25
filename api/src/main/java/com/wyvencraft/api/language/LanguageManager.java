package com.wyvencraft.api.language;

import com.wyvencraft.api.configuration.ConfigurationManager;
import com.wyvencraft.api.utils.Replacer;
import com.wyvencraft.api.utils.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.Objects;

public final class LanguageManager {
    private final ConfigurationManager configurationManager;

    public LanguageManager(ConfigurationManager configurationManager) {
        this.configurationManager = Objects.requireNonNull(configurationManager, "plugin must not be null!");
    }

    public String getLanguage() {
        return "language/" + configurationManager.get("config.yml").getString("General.language", "en_us");
    }

    public void sendMessage(CommandSender sender, String key, Replacer... replacers) {
        final String message = this.getMessage(key, replacers);
        if (message.isEmpty()) {
            return;
        }

        sender.sendMessage(Text.color(message));
    }

    public void sendMessage(CommandSender sender, String... messageArray) {
        for (final String message : messageArray) {
            if (message != null && !message.isEmpty()) {
                sender.sendMessage(Text.color(message));
            }
        }
    }

    public String getMessage(String key, Replacer... replacers) {
        final String message = this.getMessage(key);
        if (message.isEmpty()) {
            return "";
        }

        String replace = Text.color(message);
        for (final Replacer replacer : replacers) {
            replace = replacer.replace(replace);
        }

        return replace;
    }

    public String getMessage(String key) {
        final YamlConfiguration config = configurationManager.get(getLanguage() + ".yml");
        if (config.isList(key)) {
            final List<String> messageList = config.getStringList(key);
            return Text.color(String.join("\n", messageList));
        }
        final String message = config.getString(key);
        return (message == null) ? "" : Text.color(message);

    }
}
