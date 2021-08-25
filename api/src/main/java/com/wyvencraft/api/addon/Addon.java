package com.wyvencraft.api.addon;

import com.wyvencraft.api.configuration.IResource;
import com.wyvencraft.api.integration.IWyvenCore;
import com.wyvencraft.api.configuration.ConfigurationManager;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Addon implements IResource {
    public enum State {
        LOADED, UNLOADED, ENABLED, DISABLED
    }

    private State state;
    private File dataFolder, file;
    private AddonLogger logger;
    private AddonDescription description;
    private ConfigurationManager configurationManager;

    private final IWyvenCore plugin;
    private final List<Listener> listenerList;

    public Addon(IWyvenCore plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin must not be null!");
        this.listenerList = new ArrayList<>();

        this.state = State.UNLOADED;
        this.description = null;
        this.dataFolder = null;
        this.file = null;
    }

    final void setDataFolder(File dataFolder) {
        Validate.notNull(dataFolder, "dataFolder must not be null!");
        if (!dataFolder.isDirectory()) throw new IllegalArgumentException("dataFolder must be a directory!");
        this.dataFolder = dataFolder;
    }

    final void setFile(File file) {
        Validate.notNull(file, "file must not be null!");
        if (file.isDirectory()) throw new IllegalArgumentException("file must not be a directory!");
        this.file = file;
    }

    final void setDescription(AddonDescription description) {
        this.description = Objects.requireNonNull(description, "description must not be null!");
    }

    final List<Listener> getListeners() {
        return this.listenerList;
    }

    final void setState(State state) {
        this.state = Objects.requireNonNull(state, "state must not be null!");
    }

    public final State getState() {
        return this.state;
    }

    public final IWyvenCore getPlugin() {
        return this.plugin;
    }

    public final Logger getLogger() {
        if (this.logger == null) {
            this.logger = new AddonLogger(this);
        }

        return this.logger;
    }

    public final ConfigurationManager getConfigurationManager() {
        if (this.configurationManager == null) {
            this.configurationManager = new ConfigurationManager(this);
        }

        return this.configurationManager;
    }

    public final File getDataFolder() {
        return this.dataFolder;
    }

    public final File getFile() {
        return this.file;
    }

    public final AddonDescription getDescription() {
        return this.description;
    }

    public final String getName() {
        AddonDescription description = getDescription();
        return description.getUnlocalizedName();
    }

    public final String getPrefix() {
        AddonDescription description = getDescription();
        return description.getDisplayName();
    }

    public final InputStream getResource(String name) {
        Validate.notEmpty(name, "name cannot be null or empty!");
        try {
            Class<? extends Addon> thisClass = getClass();
            URLClassLoader classLoader = (URLClassLoader) thisClass.getClassLoader();

            URL url = classLoader.findResource(name);
            if (url == null) return null;

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            Logger logger = getLogger();
            logger.log(Level.WARNING, "Failed to get resource '" + name + "':", ex);
            return null;
        }
    }

    protected final boolean checkDependency(String pluginName, boolean checkEnabled) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Logger logger = getLogger();

        Plugin plugin = pluginManager.getPlugin(pluginName);
        if (plugin == null) {
            logger.warning("A dependency is not installed on the server: " + pluginName);
            return false;
        }

        if (checkEnabled && !plugin.isEnabled()) {
            logger.warning("A dependency was found but it was not enabled: " + pluginName);
            return false;
        }

        PluginDescriptionFile description = plugin.getDescription();
        String fullName = description.getFullName();
        logger.info("Successfully found a dependency: " + fullName);
        return true;
    }

    protected final boolean checkDependency(String pluginName, boolean checkEnabled, String versionStartsWith) {
        if (!checkDependency(pluginName, checkEnabled)) return false;
        PluginManager pluginManager = Bukkit.getPluginManager();
        Logger logger = getLogger();

        Plugin plugin = pluginManager.getPlugin(pluginName);
        if (plugin == null) return false;

        PluginDescriptionFile description = plugin.getDescription();
        String version = description.getVersion();
        if (!version.startsWith(versionStartsWith)) {
            logger.info("Dependency '" + pluginName + "' is not the correct version!");
            return false;
        }

        return true;
    }

    protected final void registerListener(Listener listener) {
        IWyvenCore plugin = getPlugin();
        JavaPlugin javaPlugin = plugin.getPlugin();
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(listener, javaPlugin);
        this.listenerList.add(listener);
    }

    public abstract void onLoad();

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void reloadConfig();
}
