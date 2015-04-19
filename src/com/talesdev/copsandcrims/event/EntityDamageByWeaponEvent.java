package com.talesdev.copsandcrims.event;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Called when entity was damaged by weapon
 *
 * @author MoKunz
 */
public class EntityDamageByWeaponEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Weapon weapon;
    private Entity entity;
    private double damage;
    private boolean headShot;
    private boolean isCancel = false;

    /**
     * The default constructor is defined for cleaner code. This constructor
     * assumes the event is synchronous.
     */
    public EntityDamageByWeaponEvent(Player player, Weapon weapon, Entity entity, double damage, boolean headShot) {
        this.player = player;
        this.weapon = weapon;
        this.entity = entity;
        this.damage = damage;
        this.headShot = headShot;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public CvCPlayer getCvCPlayer() {
        return CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(getPlayer());
    }

    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return isCancel;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.isCancel = cancel;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isHeadShot() {
        return headShot;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
