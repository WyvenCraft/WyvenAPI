package com.wyvencraft.api.menus;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public interface IMenus {

    /**
     * Load all menus inside menus.yml
     */
    void loadMenus();

    /**
     * Get a menu
     *
     * @param menuName name of menu
     */
    IMenu getMenu(String menuName);

    /**
     * Get a list of all menus
     */
    List<IMenu> getMenus();

    /**
     * Get a menuitem from a file.
     *
     * @param file The config file
     * @param section section to item.
     */
    IMenuItem getMenuItem(YamlConfiguration file, ConfigurationSection section);
}
