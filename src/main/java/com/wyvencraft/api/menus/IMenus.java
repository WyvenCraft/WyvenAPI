package com.wyvencraft.api.menus;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public interface IMenus {
    void loadMenus();

    IMenu getMenu(String menuName);

    List<IMenu> getMenus();

    IMenuItem getMenuItem(YamlConfiguration file, ConfigurationSection section);

}
