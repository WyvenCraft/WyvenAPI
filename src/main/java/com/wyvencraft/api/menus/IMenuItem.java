package com.wyvencraft.api.menus;

import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.smartinventory.util.SlotPos;

import java.util.List;

public interface IMenuItem {
    enum Type {
        BACK, NEXT_PAGE, PREV_PAGE, CLOSE, PLACEHOLDER;
    }

    public boolean isFill();

    public ItemStackBuilder getStack();

    public List<String> getActions();

    public SlotPos getSlot();

    public List<SlotPos> getSlots();

    public Type getType();
}
