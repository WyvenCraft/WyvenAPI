package com.wyvencraft.api.utils;

import java.util.*;

public class Utils {
    public static <L> List<L> newList(final Collection<L> oldList) {
        return new ArrayList<L>((Collection<? extends L>) oldList);
    }

    @SafeVarargs
    public static <L> List<L> newList(final L... oldArray) {
        final List<L> newList = new ArrayList<L>();
        Collections.addAll(newList, oldArray);
        return newList;
    }

    public static <K, V> Map<K, V> newMap(final Map<K, V> oldMap) {
        return new HashMap<K, V>((Map<? extends K, ? extends V>) oldMap);
    }

    public static <K, V> Map<K, V> newMap() {
        return new HashMap<K, V>();
    }
}
