package com.talesdev.copsandcrims.player;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.weapon.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * CvC Player
 * @author MoKunz
 */
public class CvCPlayer {
    private boolean isWalking = false;
    private long lastWalkingTime;
    private double armorPoint;
    private Player player;
    private PlayerRecoil playerRecoil;
    private PlayerBullet playerBullet;
    private CvCPlayerData playerData;

    public CvCPlayer(Player player) {
        this.player = player;
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

    public Player getPlayer(){
        return player;
    }

    public String getPlayerName(){
        return player.getName();
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
}