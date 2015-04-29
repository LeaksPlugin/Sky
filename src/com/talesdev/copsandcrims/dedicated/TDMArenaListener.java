package com.talesdev.copsandcrims.dedicated;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GameState;
import com.talesdev.core.arena.event.ArenaCountdownEvent;
import com.talesdev.core.arena.event.GeneralArenaListener;
import com.talesdev.core.arena.event.PlayerJoinArenaEvent;
import com.talesdev.core.arena.event.PlayerLeaveArenaEvent;
import com.talesdev.core.arena.phase.CountdownPhase;
import org.bukkit.event.EventHandler;

/**
 * TDM Arena Listener
 *
 * @author MoKunz
 */
public class TDMArenaListener extends GeneralArenaListener {

    public TDMArenaListener(GameArena gameArena) {
        super(gameArena);
    }

    @EventHandler
    public void onArenaJoin(PlayerJoinArenaEvent event) {
        if (event.getNewPlayerCount() >= 4 && getGameArena().getGameState().equals(GameState.WAITING)) {
            getGameArena().dispatchPhase(new CountdownPhase());
        }
    }

    @EventHandler
    public void onArenaCountdown(ArenaCountdownEvent event) {
        if (event.isFinished()) {

        }
    }

    @EventHandler
    public void onArenaLeave(PlayerLeaveArenaEvent event) {

    }
}
