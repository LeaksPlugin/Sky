package com.talesdev.copsandcrims.dedicated;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.core.arena.ArenaTimer;
import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.TeamGameSpawn;
import com.talesdev.core.arena.phase.EndPhase;
import com.talesdev.core.arena.phase.LobbyPhase;
import com.talesdev.core.arena.scoreboard.LobbyScoreboard;
import com.talesdev.core.arena.team.DefaultTeamSelector;
import com.talesdev.core.arena.team.TeamSelector;
import com.talesdev.core.arena.util.WinMessage;
import com.talesdev.core.config.ConfigFile;
import com.talesdev.core.player.CleanedPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * TDM Game Arena
 *
 * @author MoKunz
 */
public class TDMGameArena extends GameArena {
    private TeamGameSpawn teamGameSpawn;
    private ArenaTimer timer;
    private LobbyScoreboard lobbyScoreboard;
    private TDMScoreboard tdmScoreboard;
    private Map<String, Integer> teamKills;
    private Set<KillDeath> killDeathSet;
    private String winner = ChatColor.GREEN + "Draw";
    public TDMGameArena() {
        super(CopsAndCrims.getPlugin(), new ConfigFile("plugins/CopsAndCrims/config.yml"), null, null);
        setMaxPlayers(8);
        TDMArenaWorld arenaWorld = new TDMArenaWorld(this);
        arenaWorld.setName("NealTheFarmer");
        setArenaWorld(arenaWorld);
        setGameArenaListener(new TDMArenaListener(this));
        teamGameSpawn = new TeamGameSpawn(this);
        timer = new ArenaTimer(this, 300, false);
        tdmScoreboard = new TDMScoreboard(this);
        lobbyScoreboard = new LobbyScoreboard();
    }

    @Override
    protected void init() {
        teamKills = new HashMap<>();
        killDeathSet = new HashSet<>();
        getLogger().info("Dispatching initial phase");
        getTeam().newTeam(getTeam().createTeam("Terrorist"));
        teamKills.put("Terrorist", 0);
        if (!getConfig().contains("spawn.Terrorist")) getConfig().set("spawn.Terrorist", new ArrayList<>());
        getTeam().newTeam(getTeam().createTeam("CounterTerrorist"));
        teamKills.put("CounterTerrorist", 0);
        if (!getConfig().contains("spawn.CounterTerrorist"))
            getConfig().set("spawn.CounterTerrorist", new ArrayList<>());
        dispatchPhase(new LobbyPhase());
        getLogger().info("Completed!");
    }

    @Override
    public void startGame() {
        TeamSelector selector = new DefaultTeamSelector();
        selector.select(getTeam());
        getGlobalScoreboard().updateLocalTeam();
        getPlayerSet().forEach(this::createPlayerKD);
        teamGameSpawn.readFromConfig(getConfig());
        teamGameSpawn.spawn(this);
        for (Player player : getPlayerSet()) {
            String name = getTeam().getTeam(player).getName();
            TDMKitItem item = new TDMKitItem(player, name == null ? "" : name);
            item.give();
        }
        systemMessage("Game has been started!");
        timer.setUpdate(() -> {
            getPlayerSet().forEach(lobbyScoreboard::update);
        });
        timer.onStop(() -> {
            dispatchPhase(new EndPhase(winner));
        });
        timer.start();
    }

    @Override
    public void stopGame() {
        WinMessage winMessage = new WinMessage(this, "CopsAndCrims - TDM", winner);
        winMessage.send();
        timer.stop();
        teamKills.clear();
        getPlayerSet().forEach(player -> {
            CleanedPlayer cp = new CleanedPlayer(player);
            cp.clean();
        });
        killDeathSet.clear();
    }

    @Override
    public void destroy() {
        if (!getConfig().contains("arena-locked")) getConfig().set("arena-locked", true);
        super.destroy();
    }

    public TeamGameSpawn getTeamGameSpawn() {
        return teamGameSpawn;
    }

    public LobbyScoreboard getLobbyScoreboard() {
        return lobbyScoreboard;
    }

    public void addKill(String teamName) {
        teamKills.put(teamName, teamKills.get(teamName) + 1);
    }

    public int getKills(String teamName) {
        return teamKills.get(teamName);
    }

    public void checkStats() {
        getTeam().getTeamList().stream().filter(team -> getKills(team.getName()) > 50).forEach(team -> {
            winner = (team.getName().equals("Terrorist") ? ChatColor.RED : ChatColor.BLUE) + team.getName();
            stopGame();
        });
    }

    public KillDeath getPlayerKD(Player player) {
        for (KillDeath kd : killDeathSet) {
            if (kd.getPlayer().equals(player)) {
                return kd;
            }
        }
        return null;
    }

    public void createPlayerKD(Player player) {
        killDeathSet.add(new KillDeath(player));
    }

    public ArenaTimer getTimer() {
        return timer;
    }

    public TDMScoreboard getTdmScoreboard() {
        return tdmScoreboard;
    }
}
