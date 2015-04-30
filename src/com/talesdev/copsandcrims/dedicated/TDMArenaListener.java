package com.talesdev.copsandcrims.dedicated;

import com.talesdev.core.arena.event.ArenaCountdownEvent;
import com.talesdev.core.arena.event.GeneralArenaListener;
import com.talesdev.core.arena.event.PlayerJoinArenaEvent;
import com.talesdev.core.arena.event.PlayerLeaveArenaEvent;
import org.bukkit.event.EventHandler;

/**
 * TDM Arena Listener
 *
 * @author MoKunz
 */
public class TDMArenaListener extends GeneralArenaListener<TDMGameArena> {

    public TDMArenaListener(TDMGameArena gameArena) {
        super(gameArena);
    }

    @EventHandler
    public void onArenaJoin(PlayerJoinArenaEvent event) {

    }

    @EventHandler
    public void onArenaCountdown(ArenaCountdownEvent event) {

    }

    @EventHandler
    public void onArenaLeave(PlayerLeaveArenaEvent event) {

    }
}
