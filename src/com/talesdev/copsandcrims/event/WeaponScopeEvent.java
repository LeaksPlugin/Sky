package com.talesdev.copsandcrims.event;

import com.talesdev.copsandcrims.weapon.Weapon;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Weapon scope event
 *
 * @author MoKunz
 */
public class WeaponScopeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private boolean zoomIn;
    private Player player;
    private Weapon weapon;

    public WeaponScopeEvent(Player player, Weapon weapon, boolean zoomIn) {
        this.player = player;
        this.weapon = weapon;
        this.zoomIn = zoomIn;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isZoomIn() {
        return zoomIn;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Player getPlayer() {
        return player;
    }
}
