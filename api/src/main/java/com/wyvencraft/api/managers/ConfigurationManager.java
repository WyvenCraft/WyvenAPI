package com.wyvencraft.api.managers;

import com.wyvencraft.api.addon.Addon;
import com.wyvencraft.api.integration.IWyvenCore;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConfigurationManager {

    private final Plugin resource;
    private final File baseFolder;
    private final Map<String, YamlConfiguration> configFiles;

    public ConfigurationManager(Addon addon) {
        this(addon.getPlugin());
    }

    public ConfigurationManager(IWyvenCore plugin) {
        this.baseFolder = plugin.getPlugin().getDataFolder();
        this.configFiles = new HashMap<>();
        this.resource = plugin.getPlugin();
    }

    /**
     * @param fileName The name of the internal file.
     * @return A configuration stored inside of the resource holder
     * ({@code null} if the file does not exist or an error occurred.)
     */
    public YamlConfiguration getInternal(String fileName) {
        InputStream inputStream = this.resource.getResource(fileName);
        if (inputStream == null) return null;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            YamlConfiguration configuration = new YamlConfiguration();
            configuration.load(inputStreamReader);
            return configuration;
        } catch (IOException | InvalidConfigurationException ex) {
            return null;
        }
    }

    /**
     * @param fileName The relative name of the configuration to get
     * @return A configuration from memory. If the configuration is not in memory it will be loaded from storage first.
     * If a file can't be loaded, an empty configuration will be returned.
     */
    public YamlConfiguration get(String fileName) {
        YamlConfiguration configFile = configFiles.getOrDefault(fileName, null);
        if (configFile != null) return configFile;

        reload(fileName);
        return configFiles.getOrDefault(fileName, new YamlConfiguration());
    }

    /**
     * Save a configuration from memory to storage.
     *
     * @param fileName The relative name of the configuration.
     */
    public void save(String fileName) {
        try {
            YamlConfiguration configuration = this.configFiles.getOrDefault(fileName, null);
            if (configuration == null) return;

            File file = getFile(fileName);
            configuration.save(file);
        } catch (IOException ex) {
            Logger logger = this.resource.getLogger();
            logger.log(Level.WARNING, "An I/O exception occurred while saving a configuration file:", ex);
        }
    }

    /**
     * Load a configuration from storage into memory.
     *
     * @param fileName The relative name of the configuration.
     */
    public void reload(String fileName) {
        File file = getFile(fileName);
        if (!file.exists() || !file.isFile()) {
            Logger logger = this.resource.getLogger();
            logger.warning("'" + fileName + "' could not be reloaded because it is not a file or does not exist!");
            return;
        }

        try {
            YamlConfiguration configuration = new YamlConfiguration();

            YamlConfiguration jarConfiguration = getInternal(fileName);
            if (jarConfiguration != null) {
                configuration.setDefaults(jarConfiguration);
            }

            this.configFiles.put(fileName, configuration);
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException ex) {
            Logger logger = this.resource.getLogger();
            logger.log(Level.WARNING, "An I/O exception occurred while loading a configuration file:", ex);
            logger.log(Level.WARNING, "Using default configuration from jar file instead.");
        }
    }

    private File getFile(String fileName) {
        Validate.notEmpty(fileName, "fileName cannot be null or empty!");
        return new File(baseFolder, fileName);
    }

    public void saveDefault(String fileName) {
        Validate.notEmpty(fileName, "jarName cannot be null or empty!");

        File file = getFile(fileName);
        if (file.exists()) return;

        InputStream jarStream = this.resource.getResource(fileName);
        if (jarStream == null) {
            Logger logger = this.resource.getLogger();
            logger.warning("Failed to save default config '" + fileName + "' because it does not exist in the jar.");
            return;
        }

        try {
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                Logger logger = this.resource.getLogger();
                logger.warning("Failed to save default config '" + fileName + "' because the parent folder could not be created.");
                return;
            }

            if (!file.createNewFile()) {
                Logger logger = this.resource.getLogger();
                logger.warning("Failed to save default config '" + fileName + "' because the file could not be created.");
                return;
            }

            Path absolutePath = file.toPath();
            Files.copy(jarStream, absolutePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger logger = this.resource.getLogger();
            logger.log(Level.WARNING, "An I/O exception occurred while saving a default file:", ex);
        }
    }
}
