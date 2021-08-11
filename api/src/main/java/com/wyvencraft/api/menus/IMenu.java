package com.wyvencraft.api.menus;

import java.util.List;

public interface IMenu {

    /*
     * Get menu title
     */
    String getTitle();

    /*
     * Get rows amount
     */
    int getRows();

    /*
     * Get menu id
     */
    String getId();

    /*
     * Get parent menu name
     */
    String getParent();

    /*
     * Get menu contents
     */
    List<? extends IMenuItem> getContents();
}
