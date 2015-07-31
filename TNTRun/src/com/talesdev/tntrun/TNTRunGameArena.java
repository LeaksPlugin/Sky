package com.talesdev.tntrun;

import com.talesdev.core.arena.ArenaMapConfig;
import com.talesdev.core.arena.DedicatedArenaListener;
import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.scoreboard.LobbyScoreboard;
import com.talesdev.core.config.ConfigFile;
import org.bukkit.ChatColor;

/**
 * @author MoKunz
 */
public class TNTRunGameArena extends GameArena {
    private TNTRun tntRun;
    private LobbyScoreboard lobbyScoreboard;
    private ArenaMapConfig arenaMapConfig;

    public TNTRunGameArena(TNTRun plugin) {
        super(plugin, new ConfigFile("plugins/TNTRun/config.yml"), null, null);
        tntRun = plugin;
        // required for dedicated game
        listen(new DedicatedArenaListener(this));
    }

    @Override
    public void init() {
        getLogger().info("Setting up TNTRun game arena");
        // load a map info
        arenaMapConfig = new ArenaMapConfig(this);
        arenaMapConfig.onLoad();
        // max players
        setMaxPlayers(16);
        // head msg
        setHeadMessage(ChatColor.RED + "[TNTRun]");
        // main listener
        setGameArenaListener(new TNTRunGameArenaListener(this));
        getLogger().info("Completed!");
    }

    @Override
    public void startGame() {

    }

    @Override
    public void stopGame() {
        super.stopGame();
    }

    @Override
    public void destroy() {
        // unload a map info
        arenaMapConfig.onDestroy();
        super.destroy();
    }

    public TNTRun getPlugin() {
        return tntRun;
    }

    public LobbyScoreboard getLobbyScoreboard() {
        return lobbyScoreboard;
    }
}
