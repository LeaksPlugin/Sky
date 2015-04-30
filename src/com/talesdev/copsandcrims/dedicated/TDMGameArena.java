package com.talesdev.copsandcrims.dedicated;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.core.arena.ArenaTimer;
import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.TeamGameSpawn;
import com.talesdev.core.arena.phase.LobbyPhase;
import com.talesdev.core.arena.team.DefaultTeamSelector;
import com.talesdev.core.arena.team.TeamSelector;
import com.talesdev.core.config.ConfigFile;

import java.util.ArrayList;

/**
 * TDM Game Arena
 *
 * @author MoKunz
 */
public class TDMGameArena extends GameArena {
    private TeamGameSpawn teamGameSpawn;
    private ArenaTimer timer;
    public TDMGameArena() {
        super(CopsAndCrims.getPlugin(), new ConfigFile("plugins/CopsAndCrims/config.yml"), null, null);
        getLogger().info("Preparing TDMGameArena...");
        locked = getConfig().getBoolean("arena-locked", true);
        if (locked) {
            getLogger().info("Arena is locked!");
            getLogger().info("Set arena-locked to false if you want to open arena for joining!");
        }
        setMaxPlayers(8);
        TDMArenaWorld arenaWorld = new TDMArenaWorld(this);
        arenaWorld.setName("NealTheFarmer");
        setArenaWorld(arenaWorld);
        setGameArenaListener(new TDMArenaListener(this));
        teamGameSpawn = new TeamGameSpawn(this);
        timer = new ArenaTimer(this, 300, false);
        getLogger().info("Preparing Completed!");
    }

    @Override
    protected void init() {
        getLogger().info("Dispatching initial phase");
        getTeam().newTeam(getTeam().createTeam("Terrorist"));
        if (!getConfig().contains("spawn.Terrorist")) getConfig().set("spawn.Terrorist", new ArrayList<>());
        getTeam().newTeam(getTeam().createTeam("CounterTerrorist"));
        if (!getConfig().contains("spawn.CounterTerrorist"))
            getConfig().set("spawn.CounterTerrorist", new ArrayList<>());
        dispatchPhase(new LobbyPhase());
        getLogger().info("Completed!");
    }

    @Override
    public void startGame() {
        TeamSelector selector = new DefaultTeamSelector();
        selector.select(getTeam());
        teamGameSpawn.readFromConfig(getConfig());
        teamGameSpawn.spawn(this);
        timer.start();
    }

    @Override
    public void stopGame() {

    }

    @Override
    public void destroy() {
        if (getConfig().contains("arena-locked")) getConfig().set("arena-locked", true);
        super.destroy();
    }

    public TeamGameSpawn getTeamGameSpawn() {
        return teamGameSpawn;
    }
}
