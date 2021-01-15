package com.wyvencraft.api.addon;

import com.wyvencraft.api.integration.WyvenAPI;
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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public abstract class Addon {

    private AddonDescription description;
    private final AddonLanguage language;
    private State state;
    private final WyvenAPI plugin;
    private File dataFolder;
    private File file;
    private final Map<String, FileConfiguration> fileNameToConfigMap;

    public Addon(WyvenAPI plugin) {
        this.plugin = plugin;
        this.state = State.UNLOADED;
        this.language = new AddonLanguage(this);
        fileNameToConfigMap = new HashMap<>();
    }

    public AddonLanguage getLanguage() {
        return language;
    }

    public final State getState() {
        return state;
    }

    public final Addon setState(State state) {
        this.state = state;
        return this;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public Addon setDataFolder(File dataFolder) {
        this.dataFolder = dataFolder;
        return this;
    }

    public File getFile() {
        return file;
    }

    public Addon setFile(File file) {
        this.file = file;
        return this;
    }

    public final WyvenAPI getPlugin() {
        return plugin;
    }

    public final FileConfiguration getConfig(final String fileName) {
        try {
            final File dataFolder = this.getDataFolder();
            final File file = new File(dataFolder, fileName);
            final File realFile = file.getCanonicalFile();
            final String realName = realFile.getName();
            final FileConfiguration config = this.fileNameToConfigMap.getOrDefault(realName, null);
            if (config != null) {
                return config;
            }
            this.reloadConfig(fileName);
            return this.getConfig(fileName);
        } catch (IOException ex) {
            plugin.getPlugin().getLogger().log(Level.SEVERE, "An error occurred while getting a config named '" + fileName + "'. An empty config will be returned.", ex);
            return new YamlConfiguration();
        }
    }

    public final void reloadConfig(final String fileName) {
        try {
            final File file = new File(this.getDataFolder(), fileName);
            final File realFile = file.getCanonicalFile();
            final String realName = realFile.getName();
            final YamlConfiguration config = new YamlConfiguration();
            config.load(realFile);
            final InputStream jarStream = this.getResource(fileName);
            if (jarStream != null) {
                final InputStreamReader reader = new InputStreamReader(jarStream, StandardCharsets.UTF_8);
                final YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(reader);
                config.setDefaults(defaultConfig);
            }
            this.fileNameToConfigMap.put(realName, config);
        } catch (IOException | InvalidConfigurationException ex) {
            plugin.getPlugin().getLogger().log(Level.SEVERE, "An error ocurred while loading a config named '" + fileName + "'.", ex);
        }
    }

    public final void saveConfig(final String fileName) {
        try {
            final File dataFolder = this.getDataFolder();
            final File file = new File(dataFolder, fileName);
            final File realFile = file.getCanonicalFile();
            final File parentFile = realFile.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                final boolean createParent = parentFile.mkdirs();
                if (!createParent) {
                    plugin.getPlugin().getLogger().info("Could not create parent file for '" + fileName + "'.");
                    return;
                }
            }
            final boolean createFile = realFile.createNewFile();
            if (!createFile) {
                plugin.getPlugin().getLogger().info("Failed to create file '" + fileName + "'.");
                return;
            }
            final FileConfiguration config = this.getConfig(fileName);
            config.save(realFile);
        } catch (IOException ex) {
            plugin.getPlugin().getLogger().log(Level.SEVERE, "An error ocurred while saving a config named '" + fileName + "'.", ex);
        }
    }

    public final void saveDefaultConfig(final String fileName) {
        try {
            final File dataFolder = this.getDataFolder();
            final File file = new File(dataFolder, fileName);
            final File realFile = file.getCanonicalFile();
            if (realFile.exists()) {
                return;
            }
            final InputStream jarStream = this.getResource(fileName);
            if (jarStream == null) {
                plugin.getPlugin().getLogger().warning("Could not find file '" + fileName + "' in jar.");
                return;
            }
            final File parentFile = realFile.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                final boolean createParent = parentFile.mkdirs();
                if (!createParent) {
                    plugin.getPlugin().getLogger().info("Could not create parent file for '" + fileName + "'.");
                    return;
                }
            }
            final boolean createFile = realFile.createNewFile();
            if (!createFile) {
                plugin.getPlugin().getLogger().info("Failed to create default file '" + fileName + "'.");
                return;
            }
            final Path path = realFile.toPath();
            Files.copy(jarStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            plugin.getPlugin().getLogger().log(Level.SEVERE, "An error ocurred while saving the default config for file '" + fileName + "'.", ex);
        }
    }

    final void setDescription(final AddonDescription description) {
        this.description = description;
    }

    public final AddonDescription getDescription() {
        return description;
    }

    public final InputStream getResource(final String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName must not be null!");
        }
        try {
            final Class<? extends Addon> addonClass = this.getClass();
            final ClassLoader classLoader = addonClass.getClassLoader();
            final URL url = classLoader.getResource(fileName);
            if (url == null) {
                return null;
            }
            final URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ignored) {
            return null;
        }
    }

    public final void printHookInfo(final String pluginName) {
        final PluginManager manager = Bukkit.getPluginManager();
        if (!manager.isPluginEnabled(pluginName)) return;

        final Plugin plugin = manager.getPlugin(pluginName);
        if (plugin == null) return;

        final PluginDescriptionFile description = plugin.getDescription();
        final String fullName = description.getFullName();
        plugin.getLogger().info("Successfully hooked into plugin '" + fullName + "'.");
    }

    public abstract void onLoad();

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void reloadConfig();

    public enum State {
        ENABLED,
        DISABLED,
        LOADED,
        UNLOADED;
    }
}
