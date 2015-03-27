package com.talesdev.core.player;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * UUID Database
 * @author MoKunz
 */
public class UUIDDatabase{
    public static final String BASE_PATH = "UUID";
    private Map<String,UUID> uuidMap;
    public UUIDDatabase(String rawDataBase){
        uuidMap = new HashMap<>();
    }
    public void save(Configuration configuration,String basePath){
        for(String player : uuidMap.keySet()){
            configuration.set(basePath + "." + player + ".", uuidMap.get(player).toString());
        }
    }
    public void load(Configuration configuration,String basePath){
        for(String player : configuration.getConfigurationSection(basePath).getKeys(false)){
            String uuid = (String)configuration.getConfigurationSection(basePath).get(player);
            UUID id = UUID.fromString(uuid);
            uuidMap.put(player,id);
        }
    }
    public void save(Configuration configuration){
        save(configuration,BASE_PATH);
    }
    public void load(Configuration configuration){
        save(configuration,BASE_PATH);
    }
}
