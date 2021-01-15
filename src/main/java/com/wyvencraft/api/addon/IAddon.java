package com.wyvencraft.api.addon;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.InputStream;

public interface IAddon {
    IAddonLanguage getLanguage();

    File getDataFolder();

    IAddon setDataFolder(File dataFolder);

    File getFile();

    IAddon setFile(File file);

    FileConfiguration getConfig(final String fileName);

    void reloadConfig(final String fileName);

    void saveConfig(final String fileName);

    void saveDefaultConfig(final String fileName);

    void setDescription(final IAddonDescription description);

    IAddonDescription getDescription();

    InputStream getResource(final String fileName);

    void printHookInfo(final String pluginName);
}
