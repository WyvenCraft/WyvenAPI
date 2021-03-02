package com.wyvencraft.api.utils;

public class Debug {
    public static void log(Class<?> c, String msg) {
        System.out.println("[DEBUG-" + c.getName() + "] " + msg);
    }

    public static void log(String msg) {
        System.out.println("[DEBUG] " + msg);
    }
}
