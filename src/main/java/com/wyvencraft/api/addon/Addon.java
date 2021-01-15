package com.wyvencraft.api.addon;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.InputStream;

public interface Addon {
    AddonLanguage getLanguage();

    File getDataFolder();

    Addon setDataFolder(File dataFolder);

    File getFile();

    Addon setFile(File file);

    FileConfiguration getConfig(final String fileName);

    void reloadConfig(final String fileName);

    void saveConfig(final String fileName);

    void saveDefaultConfig(final String fileName);

    void setDescription(final AddonDescription description);

    AddonDescription getDescription();

    InputStream getResource(final String fileName);

    void printHookInfo(final String pluginName);

    void onLoad();

    void onEnable();

    void onDisable();

    void reloadConfig();
}
