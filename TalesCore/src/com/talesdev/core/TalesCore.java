package com.talesdev.core;

import com.talesdev.core.event.PlayerListener;
import com.talesdev.core.player.CorePlayer;
import com.talesdev.core.player.uuid.UUIDMap;
import com.talesdev.core.player.uuid.UUIDSystem;
import com.talesdev.core.system.UnicodeCommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * TalesCore
 *
 * @author MoKunz
 */
public class TalesCore extends JavaPlugin {
    private UUIDMap uuidMap;
    private ServerCorePlayer corePlayer;

    public static TalesCore getPlugin() {
        return getPlugin(TalesCore.class);
    }

    public static File dataLocation(String name) {
        return new File("plugins/TalesCore/" + name);
    }

    @Override
    public void onEnable() {
        checkupFileSystem();
        uuidMap = new UUIDMap(this);
        corePlayer = new ServerCorePlayer(this);
        getCommand("unicode").setExecutor(new UnicodeCommandExecutor());
        UUIDSystem uuidSystem = new UUIDSystem(this);
        getCommand("uuid").setExecutor(uuidSystem);
        getServer().getPluginManager().registerEvents(uuidSystem, this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getLogger().info("TalesCore has been enabled!");
    }

    @Override
    public void onDisable() {
        uuidMap.destroy();
        corePlayer.destroy();
        getLogger().info("TalesCore has been disabled!");
    }

    public void checkupFileSystem() {
        File dataFolder = new File("plugins/TalesCore");
        if (!dataFolder.exists()) {
            if (!dataFolder.mkdirs()) {
                getLogger().warning("Failed to created new TalesCore data folder!");
            }
        }
        File config = new File("plugins/TalesCore/config.yml");
        if (!config.exists()) {
            try {
                if (!config.createNewFile()) {
                    getLogger().warning("Failed to create new config.yml file!");
                }
            } catch (IOException e) {
                getLogger().warning(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public UUIDMap getUuidMap() {
        return uuidMap;
    }

    public ServerCorePlayer getCorePlayer() {
        return corePlayer;
    }

    public CorePlayer getCorePlayer(Player player) {
        return getCorePlayer().getCorePlayer(player);
    }
}
