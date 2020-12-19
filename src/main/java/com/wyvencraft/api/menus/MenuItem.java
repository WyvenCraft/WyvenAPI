package com.wyvencraft.api.menus;

import io.github.portlek.smartinventory.util.SlotPos;

import java.util.List;

public class MenuItem {
    public enum Type {
        BACK, NEXT_PAGE, PREV_PAGE, CLOSE, PLACEHOLDER;
    }

    private final ItemStackBuilder stack;
    private final SlotPos slot;
    private final boolean fill;
    private final List<SlotPos> slots;
    private final List<String> actions;
    private final Type type;

    public MenuItem(ItemStackBuilder stack, SlotPos slot, boolean fill, List<SlotPos> slots, List<String> actions, Type type) {
        this.stack = stack;
        this.slot = slot;
        this.fill = fill;
        this.slots = slots;
        this.actions = actions;
        this.type = type;
    }

    public boolean isFill() {
        return fill;
    }

    public ItemStackBuilder getStack() {
        return stack;
    }

    public List<String> getActions() {
        return actions;
    }

    public SlotPos getSlot() {
        return slot;
    }

    public List<SlotPos> getSlots() {
        return slots;
    }

    public Type getType() {
        return type;
    }
}
