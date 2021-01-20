package com.wyvencraft.api.hooks;

import com.wyvencraft.api.integration.WyvenAPI;
import org.bukkit.Bukkit;

import java.util.HashMap;

public abstract class Hook {
    private WyvenAPI plugin;
    public static final HashMap<String, Hook> hooks = new HashMap<>();

    private final String name;
    private boolean enabled = false;

    public Hook(String name) {
        this.name = name;
        hooks.put(name, this);
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
            System.out.println("Successfully hooked into " + this.name);
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
