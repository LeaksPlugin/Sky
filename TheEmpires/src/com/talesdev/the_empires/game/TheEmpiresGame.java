package com.talesdev.the_empires.game;

import com.talesdev.core.arena.ArenaTimer;
import com.talesdev.core.arena.DedicatedArenaListener;
import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.phase.EndPhase;
import com.talesdev.core.arena.scoreboard.LobbyScoreboard;
import com.talesdev.the_empires.CoreConfig;
import com.talesdev.the_empires.TheEmpires;
import com.talesdev.the_empires.game.system.GameListener;
import com.talesdev.the_empires.game.system.GameResponse;
import com.talesdev.the_empires.game.world.CentralArea;
import com.talesdev.the_empires.game.world.DefaultEmpire;
import com.talesdev.the_empires.game.world.Empire;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MoKunz
 */
public final class TheEmpiresGame extends GameArena {
    private Map<String, Empire> empireMap;
    private CentralArea centralArea;
    private CoreConfig coreConfig;
    private ArenaTimer timer;
    private LobbyScoreboard lobbyScoreboard;

    public TheEmpiresGame(TheEmpires plugin) {
        super(plugin, plugin.getCoreConfig(), null, null);
        // listener
        listen(new DedicatedArenaListener(this));
        listen(new GameListener(this));
    }

    @Override
    public void init() {
        // config
        this.coreConfig = getPlugin().getCoreConfig();
        this.empireMap = new HashMap<>();
        // msg
        setHeadMessage(ChatColor.RED + "[TheEmpires]");
        // timer
        this.timer = new ArenaTimer(this, 60 * 5, false);
        this.timer.onStop(() -> dispatchPhase(new EndPhase()));
        this.timer.setUpdate(this::update);
        // max player
        this.setMaxPlayers(coreConfig.getMaxPlayers());
        // scoreboard
        this.lobbyScoreboard = new LobbyScoreboard();
        this.lobbyScoreboard.setTitle("TheEmpiresGame");
        this.lobbyScoreboard.setMapName(coreConfig.getMapName());
        this.lobbyScoreboard.setMaxPlayers(getMaxPlayers());
        allInitDisplay(lobbyScoreboard);
        // create team
        getTeam().newTeam(getTeam().createTeam("Red"));
        empireMap.put("Red",
                new DefaultEmpire(this, coreConfig.getMinBond("Red"), coreConfig.getMaxBond("Red"), "Red", coreConfig.getTeamSpawn("Red")));
        getTeam().newTeam(getTeam().createTeam("Blue"));
        empireMap.put("Blue",
                new DefaultEmpire(this, coreConfig.getMinBond("Blue"), coreConfig.getMaxBond("Blue"), "Blue", coreConfig.getTeamSpawn("Blue")));
    }

    @Override
    public void startGame() {
        // teleport to team spawn
        timer.start();
    }

    public void update() {

    }

    @Override
    public void stopGame() {
        timer.stop();
        super.stopGame();
    }

    @Override
    public TheEmpires getPlugin() {
        return ((TheEmpires) super.getPlugin());
    }

    public GameResponse getGameResponse() {
        return new GameResponse() {
        };
    }

    private CoreConfig getCoreConfig() {
        return coreConfig;
    }

    public Empire getEmpire(String teamName) {
        return empireMap.get(teamName);
    }

    public Empire getEmpire(Team team) {
        return empireMap.get(team.getName());
    }
}
