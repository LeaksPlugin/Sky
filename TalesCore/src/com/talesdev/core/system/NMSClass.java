package com.talesdev.core.system;

import org.bukkit.Bukkit;

/**
 * Util class for getting nms class
 * @author MoKunz
 */
public class NMSClass {
    public static String getNMSPackageName() {
        return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static String getOBCPackageName() {
        return "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
    public static Class getNMSClass(String name) {
        try {
            return Class.forName(getNMSPackageName() + "." + name);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find class : " + name);
            e.printStackTrace();
            return null;
        }
    }

    public static Class getCBClass(String name) {
        try {
            return Class.forName(getOBCPackageName() + "." + name);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find class : " + name);
            e.printStackTrace();
            return null;
        }
    }
}
