package com.talesdev.copsandcrims.dedicated;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.phase.LobbyPhase;
import com.talesdev.core.config.ConfigFile;

/**
 * TDM Game Arena
 *
 * @author MoKunz
 */
public class TDMGameArena extends GameArena {
    public TDMGameArena() {
        super(CopsAndCrims.getPlugin(), new ConfigFile("plugins/CopsAndCrims/gamearena.yml"), null, null);
        setArenaWorld(new TDMArenaWorld(this));
        setGameArenaListener(new TDMArenaListener(this));
    }

    @Override
    protected void init() {
        getTeam().newTeam(getTeam().createTeam("Terrorist"));
        dispatchPhase(new LobbyPhase());
    }

    @Override
    public void startGame() {

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
