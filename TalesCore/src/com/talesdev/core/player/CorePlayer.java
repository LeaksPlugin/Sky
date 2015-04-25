package com.talesdev.core.player;

import com.talesdev.core.TalesCore;
import com.talesdev.core.config.ConfigFile;
import com.talesdev.core.config.Savable;
import com.talesdev.core.network.SendablePlayerMessage;
import com.talesdev.core.player.data.EquipmentCache;
import com.talesdev.core.player.data.PlayerLocation;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Core Player
 *
 * @author MoKunz
 */
public class CorePlayer {
    private ConfigFile playerConfig;
    private Set<Savable> autoSave;
    private TalesCore core;
    private PlayerTask playerTask;
    private EquipmentCache equipmentCache;
    private PlayerLocation playerLocation;
    private Player player;

    public CorePlayer(Player player, TalesCore core) {
        this.player = player;
        this.core = core;
        this.playerConfig = new ConfigFile(playerFileLocation());
        this.playerConfig.load();
        this.autoSave = new HashSet<>();
        this.playerTask = new PlayerTask(this);
        this.equipmentCache = new EquipmentCache(this);
        this.playerLocation = new PlayerLocation(this);
        // load
        this.load();
    }

    public Player getPlayer() {
        return player;
    }

    public File playerFileLocation() {
        return TalesCore.dataLocation("players/" + getPlayer().getName() + ".yml");
    }

    @Override
    public String toString() {
        return getPlayer().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OfflinePlayer) {
            return getPlayer().equals(obj);
        }
        return false;
    }

    public void sendCustomMessage(SendablePlayerMessage message) {
        message.send(getPlayer());
    }

    public void autoSave(Savable savable) {
        autoSave.add(savable);
    }

    public void listen(Listener listener) {
        core.getServer().getPluginManager().registerEvents(listener, core);
    }

    public void load() {
        autoSave.forEach(savable -> savable.loadFrom(getConfig()));
    }

    public void save() {
        autoSave.forEach(savable -> savable.saveTo(getConfig()));
    }

    public void destroy() {
        save();
        playerTask.destroy();
        playerConfig.save();
    }

    public TalesCore getCore() {
        return core;
    }

    public EquipmentCache getEquipmentCache() {
        return equipmentCache;
    }

    public FileConfiguration getConfig() {
        return playerConfig.getConfig();
    }

    public PlayerLocation getPlayerLocation() {
        return playerLocation;
    }

    public PlayerTask getPlayerTask() {
        return playerTask;
    }
}
