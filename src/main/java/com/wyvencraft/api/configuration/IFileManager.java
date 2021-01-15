package com.wyvencraft.api.configuration;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public interface IFileManager {

    /**
     * Create a new config file from existing in resources.
     *
     * @param fileName Name of the file.
     */
    void saveDefaultConfig(final String fileName);

    /**
     * Get config file
     *
     * @param fileName Name of the file.
     * @return config file
     */
    YamlConfiguration getConfig(final String fileName);

    /**
     * Reloads all configs
     */
    void reloadConfigs();

    /**
     * Reloads specific config
     *
     * @param fileName Name of the file.
     */
    void reloadConfig(final String fileName);

    /**
     * Saves specific config
     *
     * @param fileName Name of the file.
     */
    void saveConfig(final String fileName);

    /**
     * Get the actual file object of a config
     *
     * @param fileName Name of the file.
     */
    File getActualFile(final String fileName);
}
