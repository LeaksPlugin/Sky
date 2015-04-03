package com.talesdev.core.system;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NMS Implementation
 *
 * @author MoKunz
 */
public class NMSImplementation {
    private static Map<Class<?>, List<NMSVersion>> implementations = new HashMap<>();

    public static <T> void addImplementation(Class<T> nmsInterface, NMSVersion nmsImplementation) {
        if (nmsInterface != null) {
            if (implementations.get(nmsInterface) == null) {
                implementations.put(nmsInterface, new ArrayList<>());
            }
            if (implementations.get(nmsInterface).size() > 0) {
                for (NMSVersion version : implementations.get(nmsInterface)) {
                    if (version.getVersion().equalsIgnoreCase(nmsImplementation.getVersion())) {
                        return;
                    }
                }
            }
            implementations.get(nmsInterface).add(nmsImplementation);
        }
    }

    /**
     * @param nmsInterfaces nms implementation interface
     * @param <U>           implementation class
     * @return nms implementation
     */
    @SuppressWarnings("unchecked")
    public static <U> U getImplementation(Class<U> nmsInterfaces) {
        List<NMSVersion> implementation = implementations.get(nmsInterfaces);
        if (implementation != null) {
            for (NMSVersion imp : implementation) {
                if (imp.getVersion().equalsIgnoreCase(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3])) {
                    return (U) imp;
                }
            }
        }
        return null;
    }
}
