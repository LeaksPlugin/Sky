package com.talesdev.core.player.uuid;

import com.talesdev.core.TalesCore;
import com.talesdev.core.config.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * UUID Database
 *
 * @author MoKunz
 */
public class UUIDMap {
    private ConfigFile configFile;
    private Map<String, UUID> uuidMap;
    private TalesCore core;

    public UUIDMap(TalesCore core) {
        this.core = core;
        this.uuidMap = new HashMap<>();
        this.configFile = new ConfigFile(uuidFileLocation());
        load();
    }

    private File uuidFileLocation() {
        return TalesCore.dataLocation("uuid.yml");
    }

    public void load() {
        configFile.load();
        Set<String> playerList = configFile.getConfig().getKeys(false);
        playerList.stream().filter(player -> player != null).forEach(player -> {
            uuidMap.put(player, UUID.fromString(configFile.getConfig().getString(player)));
        });
    }

    public void set(String playerName, UUID uuid) {
        uuidMap.put(playerName, uuid);
    }

    public UUID get(String playerName) {
        return uuidMap.get(playerName);
    }

    public boolean contains(String playerName) {
        return uuidMap.containsKey(playerName);
    }

    public void save() {
        for (Map.Entry<String, UUID> uuidEntry : uuidMap.entrySet()) {
            configFile.getConfig().set(uuidEntry.getKey(), uuidEntry.getValue().toString());
        }
        configFile.save();
    }

    public void destroy() {
        save();
    }
}
