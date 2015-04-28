package com.talesdev.core.arena;

import com.talesdev.core.TalesCore;
import com.talesdev.core.arena.event.PlayerJoinArenaEvent;
import com.talesdev.core.arena.event.PlayerLeaveArenaEvent;
import com.talesdev.core.arena.scoreboard.DisplayScoreboard;
import com.talesdev.core.arena.team.GlobalScoreboard;
import com.talesdev.core.arena.world.ArenaWorld;
import com.talesdev.core.config.ConfigFile;
import com.talesdev.core.config.Savable;
import com.talesdev.core.player.CorePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent an arena
 *
 * @author MoKunz
 */
public class GameArena implements Joinable {
    private Plugin plugin;
    private ConfigFile configFile;
    private ArenaWorld arenaWorld;
    private GameState gameState = GameState.WAITING;
    private GlobalScoreboard globalScoreboard;
    private Set<Savable> autoSave;
    private Set<Player> playerSet;
    private GameArenaListener listener;

    public GameArena(Plugin plugin, ConfigFile configFile, ArenaWorld arenaWorld, GameArenaListener listener) {
        this.plugin = plugin;
        this.configFile = configFile;
        this.configFile.load();
        load();
        this.playerSet = new HashSet<>();
        this.autoSave = new HashSet<>();
        this.globalScoreboard = new GlobalScoreboard(this);
        this.arenaWorld = arenaWorld;
        this.gameState = GameState.WAITING;
        this.listener = listener;
        listen(this.listener);
    }

    protected void setArenaWorld(ArenaWorld arenaWorld) {
        this.arenaWorld = arenaWorld;
    }

    protected void setGameArenaListener(GameArenaListener listener) {
        this.listener = listener;
    }

    public void listen(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void dispatchPhase(GamePhase gamePhase) {
        gamePhase.dispatch(this);
    }

    public void applyScoreboard(DisplayScoreboard displayScoreboard) {
        globalScoreboard.setDisplayScoreboard(displayScoreboard);
    }

    public void autoSave(Savable savable) {
        autoSave.add(savable);
    }

    public void load() {
        autoSave.forEach(savable -> savable.loadFrom(getConfig()));
    }

    public void save() {
        autoSave.forEach(savable -> savable.saveTo(getConfig()));
    }

    public FileConfiguration getConfig() {
        return configFile.getConfig();
    }

    public void addPlayer(Player player) {
        playerSet.add(player);
    }

    public boolean containsPlayer(Player player) {
        return playerSet.contains(player);
    }

    public Set<Player> getPlayerSet() {
        return new HashSet<>(playerSet);
    }

    public int playing() {
        return playerSet.size();
    }

    public void removePlayer(Player player) {
        playerSet.remove(player);
    }

    public String getArenaName() {
        return arenaWorld.getName();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void destroy() {
        save();
        configFile.save();
    }

    @Override
    public boolean join(Player player) {
        PlayerJoinArenaEvent event = new PlayerJoinArenaEvent(this, player);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            addPlayer(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean leave(Player player) {
        PlayerLeaveArenaEvent event = new PlayerLeaveArenaEvent(this, player);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            removePlayer(player);
            return true;
        }
        return false;
    }

    public GameArenaListener getListener() {
        return listener;
    }

    public CorePlayer getCorePlayer(Player player) {
        return TalesCore.getPlugin().getCorePlayer(player);
    }

    public GlobalScoreboard getGlobalScoreboard() {
        return globalScoreboard;
    }
}
