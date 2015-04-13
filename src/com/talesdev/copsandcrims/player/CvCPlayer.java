package com.talesdev.copsandcrims.player;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.core.scoreboard.WrappedSidebarObjective;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

/**
 * CvC Player
 *
 * @author MoKunz
 */
public class CvCPlayer {
    private Scoreboard playerScoreboard;
    private WrappedSidebarObjective sidebarObjective;
    private boolean isWalking = false;
    private boolean isSneaking = false;
    private ItemStack lastHelmet = new ItemStack(Material.AIR);
    private long lastWalkingTime;
    private double armorPoint;
    private boolean isScoping = false;
    private Player player;
    private PlayerRecoil playerRecoil;
    private PlayerBullet playerBullet;

    public CvCPlayer(Player player) {
        this.player = player;
        playerScoreboard = CopsAndCrims.getPlugin().getServer().getScoreboardManager().getNewScoreboard();
        sidebarObjective = new WrappedSidebarObjective(player.getName());
        sidebarObjective.applyTo(playerScoreboard);
        getPlayer().setScoreboard(playerScoreboard);
        this.playerBullet = new PlayerBullet(this);
        this.playerRecoil = new PlayerRecoil(this);
        this.isWalking = false;
        this.lastWalkingTime = System.currentTimeMillis();
    }

    public void updateLastWalkingTime() {
        this.lastWalkingTime = System.currentTimeMillis();
    }

    public long getLastWalkingTime() {
        return lastWalkingTime;
    }

    public void setWalking(boolean walking) {
        this.isWalking = walking;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setArmorPoint(double armorPoint) {
        if (armorPoint > 0) {
            this.armorPoint = armorPoint;
        }
    }

    public double getArmorPoint() {
        return armorPoint;
    }

    public PlayerRecoil getPlayerRecoil() {
        return playerRecoil;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPlayerName() {
        return player.getName();
    }

    public boolean isScoping() {
        return isScoping;
    }

    public void setScoping(boolean isScoping) {
        this.isScoping = isScoping;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CvCPlayer && getPlayerName().equalsIgnoreCase(((CvCPlayer) obj).getPlayerName());
    }

    @Override
    public String toString() {
        return "CvCPlayer[" + getPlayerName() + "]";
    }

    public PlayerBullet getPlayerBullet() {
        return playerBullet;
    }

    public <T extends Weapon> ItemStack getWeapon(Class<T> weaponClass) {
        T weapon = CopsAndCrims.getPlugin().getWeaponFactory().getWeapon(weaponClass);
        if (weapon == null) return new ItemStack(Material.AIR);
        for (ItemStack itemStack : getPlayer().getInventory().getContents()) {
            if (weapon.isWeapon(itemStack)) {
                return itemStack;
            }
        }
        return new ItemStack(Material.AIR);
    }

    public boolean isSneaking() {
        return isSneaking;
    }

    public void setSneaking(boolean isSneaking) {
        this.isSneaking = isSneaking;
    }

    public ItemStack getLastHelmet() {
        return lastHelmet;
    }

    public void setLastHelmet(ItemStack lastHelmet) {
        this.lastHelmet = lastHelmet;
    }

    public void joinArena(CvCArena cvCArena) {
        if (!cvCArena.hasPlayer(this)) cvCArena.addPlayer(this);
    }

    public void leaveArena(CvCArena cvCArena) {
        if (cvCArena.hasPlayer(this)) cvCArena.removePlayer(this);
    }

    public Scoreboard getPlayerScoreboard() {
        return playerScoreboard;
    }

    public WrappedSidebarObjective getSidebarObjective() {
        return sidebarObjective;
    }

    public void updateScoreboard() {
        getSidebarObjective().reset(getPlayerScoreboard());
        getSidebarObjective().applyTo(getPlayerScoreboard());
        getPlayer().setScoreboard(getPlayerScoreboard());
    }

    public void clearScoreboard() {
        getSidebarObjective().reset(getPlayerScoreboard());
        getPlayer().setScoreboard(getPlayerScoreboard());
    }
}