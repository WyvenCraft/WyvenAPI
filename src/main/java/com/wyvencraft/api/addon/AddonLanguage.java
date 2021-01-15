package com.wyvencraft.api.addon;

import com.wyvencraft.api.integration.WyvenAPI;
import com.wyvencraft.api.managers.ILangManager;
import com.wyvencraft.api.utils.Replacer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class AddonLanguage implements ILangManager {
    Addon addon;
    WyvenAPI plugin;

    public AddonLanguage(Addon addon) {
        this.addon = Objects.requireNonNull(addon, "addon must not be null!");
        this.plugin = addon.getPlugin();
    }

    @Override
    public String getLanguage() {
        final FileConfiguration config = plugin.getFileManager().getConfig("config.yml");
        final String languageName = config.getString("General.language");
        if (languageName == null) {
            return "en_us";
        }

        final File languageFolder = new File(addon.getDataFolder(), "language");
        final File languageFile = new File(languageFolder, languageName + ".yml");
        return languageFile.exists() ? languageName : "en_us";
    }

    @Override
    public String getMessage(String msg) {
        final String languageName = this.getLanguage();
        final FileConfiguration config = addon.getConfig("language/" + languageName + ".yml");

        if (config.isList(msg)) {
            final List<String> messageList = config.getStringList(msg);
            return String.join("\n", messageList);
        }

        final String message = config.getString(msg);
        return (message == null) ? "" : message;
    }

    @Override
    public String getLocalizedMessage(Player player, String key) {
        return null;
    }

    @Override
    public void sendLocalizedMessage(Player player, String key, Replacer... replacerArray) {

    }
}
