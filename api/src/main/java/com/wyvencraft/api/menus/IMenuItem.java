package com.wyvencraft.api.menus;

import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.smartinventory.util.SlotPos;

import java.util.List;

public interface IMenuItem {
    enum Type {
        BACK, NEXT_PAGE, PREV_PAGE, CLOSE, PLACEHOLDER;
    }

    /**
     *
     * @return true if this item should fill inventory
     */
    boolean isFill();

    /**
     *
     * @return menu item as itembuilder
     */
    ItemStackBuilder getStack();

    /**
     *
     * @return list of actions to be done when clicked
     */
    List<String> getActions();

    /**
     *
     * @return the slot position inside menu.
     */
    SlotPos getSlot();

    /**
     *
     * @return list of slot positions
     */
    List<SlotPos> getSlots();

    /**
     * BACK - opens parent page if available
     * NEXT_PAGE - opens next page if is pagination and have multiple
     * PREVIOUS_PAGE - opens previous page if is pagination and have multiple
     * CLOSE - closes the menu
     * PLACEHOLDER - default type
     *
     * @return type of menu item
     */
    Type getType();
}
