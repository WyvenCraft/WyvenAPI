package com.wyvencraft.api.addon;

import com.wyvencraft.api.integration.IWyvenCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.logging.Logger;

class AddonClassLoader extends URLClassLoader {
    private Addon addon;
    private final AddonManager manager;
    private final Map<String, Class<?>> classes;

    AddonClassLoader(AddonManager manager, YamlConfiguration description, File path, ClassLoader parent)
            throws MalformedURLException {
        super(new URL[]{path.toURI().toURL()}, parent);
        this.manager = manager;
        this.classes = new HashMap<>();

        registerAddon(description, path);
    }

    @Override
    protected Class<?> findClass(String name) {
        return findClass(name, true);
    }

    public Class<?> findClass(String name, boolean checkGlobal) {
        Class<?> result = classes.get(name);
        if (result == null) {
            if (checkGlobal) {
                result = this.manager.getClassByName(name);
            }

            if (result == null) {
                try {
                    result = super.findClass(name);
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    // Do nothing.
                } catch (UnsupportedClassVersionError e) {
                    IWyvenCore plugin = this.manager.getPlugin();
                    Logger logger = plugin.getLogger();
                    logger.warning("Could not load class with name=" + name + ", global=" + checkGlobal
                            + " because an error occurred:");
                    logger.warning(e.getMessage());
                }

                if (result != null) {
                    this.manager.setClass(name, result);
                }
            }
            classes.put(name, result);
        }
        return result;
    }

    public Addon getAddon() {
        return this.addon;
    }

    public Set<String> getClasses() {
        return this.classes.keySet();
    }

    private void registerAddon(YamlConfiguration description, File path) {
        Class<?> mainClass;
        try {
            String mainClassName = description.getString("main");
            if (mainClassName == null) throw new IllegalStateException("Could not find `main` in addon.yml");

            mainClass = Class.forName(mainClassName, true, this);
        } catch (ReflectiveOperationException ex) {
            String newError = ("Could not load '" + path.getName() + "' from folder '" + path.getParent() + "'");
            throw new IllegalStateException(newError, ex);
        }

        Class<? extends Addon> addonClass;
        try {
            addonClass = mainClass.asSubclass(Addon.class);
        } catch (ClassCastException ex) {
            String newError = ("Main class is not an instance of 'Addon'");
            throw new IllegalStateException(newError, ex);
        }

        try {
            Constructor<? extends Addon> declaredConstructor = addonClass.getDeclaredConstructor(IWyvenCore.class);
            this.addon = declaredConstructor.newInstance(this.manager.getPlugin());

            AddonDescription addonDescription = createDescription(description);
            this.addon.setDescription(addonDescription);
        } catch (ReflectiveOperationException ex) {
            String newError = ("Could not load '" + path.getName() + "' from folder '" + path.getParent() + "'");
            throw new IllegalStateException(newError, ex);
        }
    }

    private AddonDescription createDescription(YamlConfiguration configuration) throws IllegalStateException {
        String mainClassName = configuration.getString("main");
        if (mainClassName == null) throw new IllegalStateException("'main' is required in addon.yml");

        String unlocalizedName = configuration.getString("name");
        if (unlocalizedName == null) throw new IllegalStateException("'name' is required in addon.yml");

        String version = configuration.getString("version");
        if (version == null) throw new IllegalStateException("'version' is required in addon.yml");

        AddonDescriptionBuilder builder = new AddonDescriptionBuilder(mainClassName, unlocalizedName, version);
        String displayName = configuration.getString("display-name", null);
        if (displayName == null) displayName = configuration.getString("prefix", null);
        if (displayName == null) displayName = unlocalizedName;
        builder.withDisplayName(displayName);

        String description = configuration.getString("description", null);
        if (description != null) builder.withDescription(description);

        String website = configuration.getString("website", null);
        if (website != null) builder.withWebsite(website);

        List<String> authorList = configuration.getStringList("authors");
        if (authorList.isEmpty()) authorList = new ArrayList<>();

        String author = configuration.getString("author", null);
        if (author != null) authorList.add(author);
        builder.withAuthors(authorList);

        List<String> pluginDependList = configuration.getStringList("plugin-depend");
        builder.withPluginDependencies(pluginDependList);

        List<String> pluginSoftDependList = configuration.getStringList("plugin-soft-depend");
        builder.withPluginSoftDependencies(pluginSoftDependList);

        List<String> addonDependList = configuration.getStringList("addon-depend");
        builder.withAddonDependencies(addonDependList);

        List<String> addonSoftDependList = configuration.getStringList("addon-soft-depend");
        builder.withAddonSoftDependencies(addonSoftDependList);

        boolean lateLoad = configuration.getBoolean("late-load", false);
        builder.withLateLoad(lateLoad);

        return builder.build();
    }
}
