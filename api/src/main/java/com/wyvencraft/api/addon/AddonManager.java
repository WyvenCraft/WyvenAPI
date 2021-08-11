package com.wyvencraft.api.addon;

import com.wyvencraft.api.integration.IWyvenCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class AddonManager {
    private final IWyvenCore plugin;
    private final Map<String, Addon> addonMap;
    private final Map<Addon, AddonClassLoader> addonClassLoaderMap;
    private final Map<String, Class<?>> classNameMap;

    public AddonManager(IWyvenCore plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin must not be null!");
        this.addonMap = new HashMap<>();
        this.addonClassLoaderMap = new HashMap<>();
        this.classNameMap = new HashMap<>();
    }

    public IWyvenCore getPlugin() {
        return this.plugin;
    }

    public void loadAddons() {
        IWyvenCore plugin = getPlugin();
        Logger logger = plugin.getLogger();
        logger.info("Loading addons...");

        File dataFolder = plugin.getPlugin().getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            logger.warning("The CombatLogX folder does not exist and could not be created!");
            return;
        }

        File addonsFolder = new File(dataFolder, "addons");
        if (!addonsFolder.exists() && !addonsFolder.mkdirs()) {
            logger.warning("The addons folder does not exist and could not be created!");
            return;
        }

        FilenameFilter filter = (folder, fileName) -> fileName.endsWith(".jar");
        File[] fileArray = addonsFolder.listFiles(filter);
        if (fileArray == null || fileArray.length == 0) {
            logger.info("There were no addons to load.");
            return;
        }

        for (File file : fileArray) {
            if (file.isDirectory()) continue;
            loadAddon(file);
            logger.info(" ");
        }

        List<Addon> addonList = sortAddons(getLoadedAddons());
        int addonListSize = addonList.size();

        String message = ("Successfully loaded " + addonListSize + " addon" +
                (addonListSize == 1 ? "" : "s") + ".");
        logger.info(message);
    }

    public void enableAddons() {
        IWyvenCore plugin = getPlugin();
        Logger logger = plugin.getLogger();
        logger.info("Enabling addons...");

        List<Addon> loadedAddonList = getLoadedAddons();
        if (loadedAddonList.isEmpty()) {
            logger.info("There were no addons to enable.");
            return;
        }

        sortAddons(loadedAddonList);
        List<Addon> lateLoadAddonList = new ArrayList<>();

        for (Addon addon : loadedAddonList) {
            AddonDescription description = addon.getDescription();
            if (description.isLateLoad()) {
                lateLoadAddonList.add(addon);
                continue;
            }

            enableAddon(addon);
            logger.info(" ");
        }

        List<Addon> enabledAddonList = getEnabledAddons();
        int addonListSize = enabledAddonList.size();
        String message = ("Successfully enabled " + addonListSize + " addon"
                + (addonListSize == 1 ? "" : "s") + ".");
        logger.info(message);

        Runnable task = () -> {
            for (Addon addon : lateLoadAddonList) {
                enableAddon(addon);
                logger.info(" ");
            }

            List<Addon> newEnabledAddonList = getEnabledAddons();
            int newAddonListSize = newEnabledAddonList.size();
            int newAddonCount = (newAddonListSize - addonListSize);

            String newMessage = ("Successfully enabled " + newAddonCount + " late-load addon"
                    + (addonListSize == 1 ? "" : "s") + ".");
            logger.info(newMessage);
        };

        JavaPlugin javaPlugin = getPlugin().getPlugin();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(javaPlugin, task, 1L);
    }

    public void disableAddons() {
        IWyvenCore plugin = getPlugin();
        Logger logger = plugin.getLogger();
        logger.info("Disabling addons...");

        List<Addon> enabledAddonList = getEnabledAddons();
        if (enabledAddonList.isEmpty()) {
            logger.info("There were no addons to disable.");
        } else {
            for (Addon addon : enabledAddonList) {
                disableAddon(addon);
                logger.info(" ");
            }
        }

        this.addonMap.clear();
        this.classNameMap.clear();
        this.addonClassLoaderMap.clear();
        logger.info("Successfully disabled all addons.");
    }

    public void reloadConfigs() {
        List<Addon> addonList = getEnabledAddons();
        addonList.forEach(Addon::reloadConfig);
    }

    public Optional<Addon> getAddon(String name) {
        if (name == null) return Optional.empty();
        Addon addon = this.addonMap.getOrDefault(name, null);
        return Optional.ofNullable(addon);
    }

    public List<Addon> getAllAddons() {
        Collection<Addon> addonCollection = this.addonMap.values();
        return new ArrayList<>(addonCollection);
    }

    public List<Addon> getLoadedAddons() {
        List<Addon> addonList = getAllAddons();
        return addonList.stream()
                .filter(addon -> addon.getState() == Addon.State.LOADED)
                .collect(Collectors.toList());
    }

    public List<Addon> getEnabledAddons() {
        List<Addon> addonList = getAllAddons();
        return addonList.stream()
                .filter(addon -> addon.getState() == Addon.State.ENABLED)
                .sorted(Comparator.comparing(Addon::getName))
                .collect(Collectors.toList());
    }

    public AddonClassLoader getClassLoader(Addon addon) {
        return this.addonClassLoaderMap.getOrDefault(addon, null);
    }

    public Class<?> getClassByName(String name) {
        try {
            Class<?> defaultValue = this.addonClassLoaderMap.values()
                    .stream().map(loader -> loader.findClass(name, false))
                    .filter(Objects::nonNull).findFirst().orElse(null);
            return this.classNameMap.getOrDefault(name, defaultValue);
        } catch (Exception ex) {
            return null;
        }
    }

    public void setClass(String name, Class<?> clazz) {
        this.classNameMap.putIfAbsent(name, clazz);
    }

    private void loadAddon(File addonFile) {
        IWyvenCore plugin = getPlugin();
        Logger logger = plugin.getLogger();
        plugin.printDebug("Attempting to load addon from file '" + addonFile + "'...");

        Addon addon;
        AddonClassLoader addonClassLoader;
        try (JarFile jarFile = new JarFile(addonFile)) {
            YamlConfiguration description = getAddonDescription(jarFile);
            Class<? extends AddonManager> managerClass = getClass();
            ClassLoader managerClassLoader = managerClass.getClassLoader();

            PluginManager pluginManager = Bukkit.getPluginManager();
            if (description.isList("plugin-depend")) {
                List<String> pluginDependList = description.getStringList("plugin-depend");
                for (String pluginName : pluginDependList) {
                    Plugin dependencyPlugin = pluginManager.getPlugin(pluginName);
                    if (dependencyPlugin != null) continue;

                    logger.warning("Failed to load addon '" + addonFile + "' because a plugin " +
                            "dependency was not loaded: " + pluginName);
                    return;
                }
            }

            if (description.isList("addon-depend")) {
                List<String> addonDependList = description.getStringList("addon-depend");
                for (String addonName : addonDependList) {
                    if (this.addonMap.containsKey(addonName)) continue;
                    logger.warning("Failed to load addon '" + addonFile + "' because an addon" +
                            " dependency was missing: " + addonName);
                    return;
                }
            }

            addonClassLoader = new AddonClassLoader(this, description, addonFile,
                    managerClassLoader);
            addon = addonClassLoader.getAddon();
        } catch (Exception ex) {
            logger.warning("An addon failed to load because an error occurred.");
            logger.warning("If debug-mode is enabled, the full error will be displayed below.");
            this.plugin.printDebug(ex);
            return;
        }

        File pluginFolder = plugin.getPlugin().getDataFolder();
        File addonsFolder = new File(pluginFolder, "addons");
        String addonName = addon.getName();
        File dataFolder = new File(addonsFolder, addonName);
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            logger.warning("Failed to create folder for addon '" + addonName + "'.");
            return;
        }

        addon.setFile(addonFile);
        addon.setDataFolder(dataFolder);

        this.addonMap.put(addonName, addon);
        this.addonClassLoaderMap.put(addon, addonClassLoader);

        try {
            AddonDescription description = addon.getDescription();
            String fullName = description.getFullName();
            logger.info("Loading addon '" + fullName + "'...");

            addon.onLoad();
            addon.setState(Addon.State.LOADED);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "An error occurred while loading an addon:", ex);
            logger.warning("Failed to load addon from file '" + addonFile + "'.");
        }
    }

    public void enableAddon(Addon addon) {
        Addon.State state = addon.getState();
        if (state == Addon.State.ENABLED) return;

        IWyvenCore plugin = getPlugin();
        Logger logger = plugin.getLogger();

        try {
            AddonDescription description = addon.getDescription();
            String fullName = description.getFullName();
            logger.info("Enabling addon '" + fullName + "'...");

            addon.setState(Addon.State.ENABLED);
            addon.onEnable();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "An error occurred while enabling an addon:", ex);
        }
    }

    public void disableAddon(Addon addon) {
        Addon.State state = addon.getState();
        if (state != Addon.State.ENABLED) return;

        IWyvenCore plugin = getPlugin();
        Logger logger = plugin.getLogger();

        try {
            AddonDescription description = addon.getDescription();
            String fullName = description.getFullName();
            logger.info("Disabling addon '" + fullName + "'...");

            List<Listener> listenerList = addon.getListeners();
            listenerList.forEach(HandlerList::unregisterAll);
            listenerList.clear();

            addon.setState(Addon.State.DISABLED);
            addon.onDisable();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "An error occurred while enabling an addon:", ex);
        }
    }

    private List<Addon> sortAddons(List<Addon> original) {
        original.sort(new AddonComparator());
        return original;
    }

    private YamlConfiguration getAddonDescription(JarFile jarFile) throws IllegalStateException, IOException,
            InvalidConfigurationException {
        JarEntry entry = jarFile.getJarEntry("addon.yml");
        if (entry == null) {
            String errorMessage = ("Addon file '" + jarFile.getName() + "' does not contain an " +
                    "addon.yml file.");
            throw new IllegalStateException(errorMessage);
        }

        InputStream inputStream = jarFile.getInputStream(entry);
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader buffer = new BufferedReader(reader);

        YamlConfiguration description = new YamlConfiguration();
        description.load(buffer);
        return description;
    }
}
