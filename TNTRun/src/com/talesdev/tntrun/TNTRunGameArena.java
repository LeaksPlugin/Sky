package com.talesdev.tntrun;

import com.talesdev.core.arena.ArenaMapConfig;
import com.talesdev.core.arena.DedicatedArenaListener;
import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.phase.LobbyPhase;
import com.talesdev.core.arena.scoreboard.LobbyScoreboard;
import com.talesdev.core.arena.util.WinMessage;
import com.talesdev.core.config.ConfigFile;
import com.talesdev.core.player.CleanedPlayer;
import org.bukkit.*;

/**
 * @author MoKunz
 */
public class TNTRunGameArena extends GameArena {
    private TNTFloorSystem floorSystem;
    private LobbyScoreboard lobbyScoreboard;

    private Location spawn = Bukkit.getWorlds().get(0).getSpawnLocation();
    private ArenaMapConfig arenaMapConfig;

    public TNTRunGameArena(TNTRun plugin) {
        super(plugin, new ConfigFile("plugins/TNTRun/config.yml"), null, null);
        // required for dedicated game
        listen(new DedicatedArenaListener(this));
    }

    @Override
    public void init() {
        getLogger().info("Setting up TNTRun game arena");
        // load a map info
        arenaMapConfig = new ArenaMapConfig(this);
        arenaMapConfig.onLoad();
        // lobby scoreboard
        lobbyScoreboard = new LobbyScoreboard();
        lobbyScoreboard.setMapName(getArenaWorld().getName());
        lobbyScoreboard.setTitle(ChatColor.RED + "TNTGame - TNTRun");
        // floor
        floorSystem = new TNTFloorSystem(this);
        getBlockRegen().setPriority(Material.TNT, 10);
        getBlockRegen().setPriority(Material.SAND, -10);
        getBlockRegen().setPriority(Material.GRAVEL, -20);
        // max players
        setMaxPlayers(16);
        // head msg
        setHeadMessage(ChatColor.RED + "[TNTRun]");
        // main listener
        setGameArenaListener(new TNTRunGameArenaListener(this));
        // lobby scoreboard
        allInitDisplay(lobbyScoreboard);
        // waiting
        dispatchPhase(new LobbyPhase());
        getLogger().info("Completed!");
    }

    @Override
    public void startGame() {
        floorSystem.startGame();
        getPlayerSet().forEach((player) -> {
            getCorePlayer(player).getWrappedScoreboard().reset();
            new CleanedPlayer(player).clean(GameMode.ADVENTURE);
            player.teleport(spawn);
        });
        systemMessage("The game has been started!");
    }

    @Override
    public void stopGame() {
        String s = floorSystem.getWinner();
        if (s == null) s = ChatColor.YELLOW + "Draw";
        WinMessage winner = new WinMessage(this, ChatColor.RED + "TNTGame - TNTRun", ChatColor.GREEN + s);
        winner.send();
        floorSystem.reset();
        super.stopGame();
    }

    @Override
    public void destroy() {
        // unload a map info
        arenaMapConfig.onDestroy();
        super.destroy();
    }

    public TNTRun getPlugin() {
        return ((TNTRun) super.getPlugin());
    }

    public LobbyScoreboard getLobbyScoreboard() {
        return lobbyScoreboard;
    }

    public TNTFloorSystem getFloorSystem() {
        return floorSystem;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }
}
