package com.wyvencraft.api.addon;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

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
