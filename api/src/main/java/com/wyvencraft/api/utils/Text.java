package com.wyvencraft.api.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class Text {
    public static String color(final String message) {
        return message == null ? "" : ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] colorArray(final String... messageArray) {
        final String[] colorArray = new String[messageArray.length];
        for (int i = 0; i < messageArray.length; ++i) {
            final String message = messageArray[i];
            colorArray[i] = color(message);
        }
        return colorArray;
    }

    public static List<String> colorList(final Iterable<String> messageList) {
        final List<String> colorList = Utils.newList(new String[0]);
        for (final String message : messageList) {
            final String color = color(message);
            colorList.add(color);
        }
        return colorList;
    }

    public static List<String> colorList(final String... messageArray) {
        final List<String> messageList = Utils.newList(messageArray);
        return colorList(messageList);
    }
}
