package com.wyvencraft.api.hooks;

import com.wyvencraft.api.integration.IWyvenCore;
import org.bukkit.Bukkit;

import java.util.HashMap;

public abstract class Hook {
    public static final HashMap<String, Hook> hooks = new HashMap<>();

    private final IWyvenCore plugin;
    private final String name;
    private boolean enabled = false;

    public Hook(IWyvenCore plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        hooks.put(name, this);
    }

    public IWyvenCore getPlugin() {
        return this.plugin;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getName() {
        return this.name;
    }

    public void hook() {
        if (Bukkit.getPluginManager().isPluginEnabled(this.name)) {
            this.enabled = true;
            runHookAction();
            getPlugin().sendMessage(Bukkit.getConsoleSender(), "Successfully hooked into " + this.name);
        }
    }

    protected abstract void runHookAction();

    public static void attemptHooks() {
        for (Hook hook : hooks.values()) {
            hook.hook();
        }
    }

    public static boolean isEnabled(String pluginName) {
        return hooks.get(pluginName).isEnabled();
    }

    public static Hook getHookByName(String pluginName) {
        return hooks.get(pluginName);
    }
}
