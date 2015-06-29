package com.talesdev.core.arena;

import com.talesdev.core.TalesCore;
import com.talesdev.core.arena.event.PlayerJoinArenaEvent;
import com.talesdev.core.arena.event.PlayerLeaveArenaEvent;
import com.talesdev.core.arena.phase.CountdownPhase;
import com.talesdev.core.arena.phase.GamePhase;
import com.talesdev.core.arena.scoreboard.DisplayScoreboard;
import com.talesdev.core.arena.team.GlobalScoreboard;
import com.talesdev.core.arena.team.GlobalTeam;
import com.talesdev.core.arena.world.ArenaWorld;
import com.talesdev.core.config.ConfigFile;
import com.talesdev.core.config.Savable;
import com.talesdev.core.player.CorePlayer;
import com.talesdev.core.server.ManagedTask;
import com.talesdev.core.system.Destroyable;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represent an arena
 *
 * @author MoKunz
 */
public class GameArena implements Joinable {
    protected boolean locked;
    private int maxPlayers;
    private Plugin plugin;
    private ConfigFile configFile;
    private ArenaWorld arenaWorld;
    private ManagedTask task;
    private GameState gameState = GameState.WAITING;
    private GlobalScoreboard globalScoreboard;
    private Set<Savable> autoSave;
    private Set<Destroyable> destroyableSet;
    private Set<Player> playerSet;
    private ArenaSpawn arenaSpawn;
    private GameArenaListener listener;
    private String headMessage;

    public GameArena(Plugin plugin, ConfigFile configFile, ArenaWorld arenaWorld, GameArenaListener listener) {
        this.plugin = plugin;
        getLogger().info("Preparing " + getClass().getSimpleName() + "...");
        this.playerSet = new HashSet<>();
        this.autoSave = new HashSet<>();
        this.destroyableSet = new HashSet<>();
        this.configFile = configFile;
        load();
        headMessage = ChatColor.RED + "[CVC]";
        this.globalScoreboard = new GlobalScoreboard(this);
        this.task = new ArenaTask(this);
        this.arenaWorld = arenaWorld;
        this.arenaSpawn = new TeamGameSpawn(this);
        this.gameState = GameState.WAITING;
        this.listener = listener;
        locked = getConfig().getBoolean("arena-locked", true);
        if (locked) {
            getLogger().info("Arena is locked!");
            getLogger().info("Set arena-locked to false if you want to open arena for joining!");
        }
        init();
        getLogger().info("Preparing completed!");
    }

    public void init() {

    }

    public void startGame() {

    }

    public void stopGame() {
        // global scoreboard
        getTeam().clearTeam();
        getGlobalScoreboard().reset();
    }

    public void updateDisplay(DisplayScoreboard displayScoreboard) {
        getPlayerSet().forEach(displayScoreboard::update);
    }

    public void initDisplay(Player player, DisplayScoreboard displayScoreboard) {
        displayScoreboard.start(player);
    }

    public void allInitDisplay(DisplayScoreboard displayScoreboard) {
        getPlayerSet().forEach(displayScoreboard::start);
    }

    protected void setArenaWorld(ArenaWorld arenaWorld) {
        this.arenaWorld = arenaWorld;
    }

    protected void setGameArenaListener(GameArenaListener listener) {
        this.listener = listener;
        listen(this.listener);
    }

    public ArenaSpawn getArenaSpawn() {
        return arenaSpawn;
    }

    protected void setArenaSpawn(ArenaSpawn arenaSpawn) {
        this.arenaSpawn = arenaSpawn;
    }

    public void listen(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void dispatchPhase(GamePhase gamePhase) {
        gamePhase.dispatch(this);
    }

    public void autoSave(Savable savable) {
        autoSave.add(savable);
    }

    public void autoDestroy(Destroyable destroyable) {
        destroyableSet.add(destroyable);
    }

    public void load() {
        autoSave.forEach(savable -> savable.loadFrom(getConfig()));
    }

    public void save() {
        autoSave.forEach(savable -> savable.saveTo(getConfig()));
        configFile.save();
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

    public Plugin getPlugin() {
        return plugin;
    }

    public Server getServer() {
        return plugin.getServer();
    }

    public void callEvent(Event event) {
        getServer().getPluginManager().callEvent(event);
    }

    public BukkitScheduler getScheduler() {
        return getServer().getScheduler();
    }

    public Logger getLogger() {
        return getPlugin().getLogger();
    }

    public void destroy() {
        save();
        destroyableSet.forEach(Destroyable::destroy);
        configFile.save();
    }

    public void systemMessage(String message) {
        playerSet.forEach(player -> player.sendMessage(headMessage + " " + ChatColor.YELLOW + message));
    }

    public void sendMessage(String message) {
        playerSet.forEach(player -> player.sendMessage(message));
    }

    @Override
    public boolean join(Player player) {
        if (!getGameState().canJoin()) {
            return false;
        }
        PlayerJoinArenaEvent event = new PlayerJoinArenaEvent(this, player, playerSet.size());
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            addPlayer(player);
            getGlobalScoreboard().join(player);
            systemMessage(ChatColor.BLUE + player.getName() + ChatColor.YELLOW + " joined! (" + playing() + "/" + getMaxPlayers() + ")");
            if (event.getAfterRun() != null) event.getAfterRun().run();
            if (playing() >= getMaxPlayers() / 2.0 && getGameState().equals(GameState.WAITING)) {
                dispatchPhase(new CountdownPhase());
            }
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
            TalesCore.getPlugin().getCorePlayer(player).getWrappedScoreboard().reset();
            getGlobalScoreboard().leave(player);
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

    public GlobalTeam getTeam() {
        return getGlobalScoreboard().getTeam();
    }

    public Set<Destroyable> getDestroyable() {
        return new HashSet<>(destroyableSet);
    }

    public ManagedTask getTask() {
        return task;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public boolean isLocked() {
        return locked;
    }
}
