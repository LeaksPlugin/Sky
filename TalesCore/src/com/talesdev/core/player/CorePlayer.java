package com.talesdev.core.player;

import com.talesdev.core.TalesCore;
import com.talesdev.core.config.ConfigFile;
import com.talesdev.core.config.Savable;
import com.talesdev.core.network.SendablePlayerMessage;
import com.talesdev.core.player.data.EquipmentCache;
import com.talesdev.core.player.data.ItemDataSet;
import com.talesdev.core.player.data.PlayerDamage;
import com.talesdev.core.player.data.PlayerLocation;
import com.talesdev.core.scoreboard.HealthBar;
import com.talesdev.core.scoreboard.WrappedScoreboard;
import com.talesdev.core.system.Destroyable;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Core Player
 *
 * @author MoKunz
 */
public class CorePlayer {
    private ConfigFile playerConfig;
    private Set<Savable> autoSave;
    private Set<Destroyable> autoDestroy;
    private Set<ItemDataSet> itemDataSet;
    private TalesCore core;
    private PlayerTask playerTask;
    private EquipmentCache equipmentCache;
    private PlayerLocation playerLocation;
    private Scoreboard playerScoreboard;
    private Entity compassTarget;
    private WrappedScoreboard wrappedScoreboard;
    private HealthBar healthBar;
    private PlayerDamage playerDamage;
    private Player player;

    public CorePlayer(Player player, TalesCore core) {
        this.player = player;
        this.core = core;
        this.playerConfig = new ConfigFile(playerFileLocation());
        this.playerConfig.load();
        this.autoSave = new HashSet<>();
        this.autoDestroy = new HashSet<>();
        this.itemDataSet = new HashSet<>();
        this.playerTask = new PlayerTask(this);
        this.equipmentCache = new EquipmentCache(this);
        this.playerLocation = new PlayerLocation(this);
        this.playerDamage = new PlayerDamage(this);
        this.compassTarget = null;
        this.playerScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.healthBar = new HealthBar(playerScoreboard);
        this.wrappedScoreboard = new WrappedScoreboard(playerScoreboard);
        player.setScoreboard(playerScoreboard);
        // load
        this.load();
        initCompassTracker();
    }

    public void initCompassTracker() {
        getPlayerTask().store("compass", getScheduler().runTaskTimer(getCore(), () -> {
            if (getCompassTarget() != null) {
                player.setCompassTarget(getCompassTarget().getLocation());
            } else {
                player.setCompassTarget(player.getBedSpawnLocation());
            }
        }, 0, 15));
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

    public void autoDestroy(Destroyable destroyable) {
        autoDestroy.add(destroyable);
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
        autoDestroy.forEach(Destroyable::destroy);
        playerConfig.save();
    }

    public String sectionOf(Savable savable, String node) {
        return (savable.getName() != null ? (savable.getName().length() > 0 ? savable.getName() + "." : "") : "") + node;
    }

    public void updateScoreboard() {
        wrappedScoreboard.update();
        getPlayer().setScoreboard(getPlayerScoreboard());
    }

    public void clearScoreboard() {
        wrappedScoreboard.reset();
        getPlayer().setScoreboard(getPlayerScoreboard());
    }

    public Set<ItemDataSet> getItemDataSet() {
        return new HashSet<>(itemDataSet);
    }

    public void add(ItemDataSet itemDataSet) {
        this.itemDataSet.add(itemDataSet);
    }

    public ItemDataSet get(String itemName) {
        for (ItemDataSet itemDataSet : this.itemDataSet) {
            if (itemDataSet.getItemName().equalsIgnoreCase(itemName)) {
                return itemDataSet;
            }
        }
        return null;
    }

    public boolean contains(String itemName) {
        for (ItemDataSet itemDataSet : this.itemDataSet) {
            if (itemDataSet.getItemName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void remove(String itemName) {
        for (Iterator<ItemDataSet> dataListIterator = itemDataSet.iterator(); dataListIterator.hasNext(); ) {
            ItemDataSet itemDataSet = dataListIterator.next();
            if (itemDataSet.getItemName().equalsIgnoreCase(itemName)) {
                dataListIterator.remove();
            }
        }
    }

    public TalesCore getCore() {
        return core;
    }

    public Server getServer() {
        return core.getServer();
    }

    public Logger getLogger() {
        return Logger.getLogger("[CorePlayer][" + player.getName() + "]");
    }

    public BukkitScheduler getScheduler() {
        return core.getServer().getScheduler();
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

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public Scoreboard getPlayerScoreboard() {
        return playerScoreboard;
    }

    public WrappedScoreboard getWrappedScoreboard() {
        return wrappedScoreboard;
    }

    public PlayerDamage getPlayerDamage() {
        return playerDamage;
    }

    public Entity getCompassTarget() {
        return compassTarget;
    }

    public void setCompassTarget(Entity compassTarget) {
        this.compassTarget = compassTarget;
    }
}
