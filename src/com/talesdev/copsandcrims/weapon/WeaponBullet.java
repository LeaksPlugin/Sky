package com.talesdev.copsandcrims.weapon;

/**
 * Weapon bullet
 *
 * @author MoKunz
 */
public class WeaponBullet {
    private String weaponName = "";
    private int bulletCount;
    private int maxBullet;
    private boolean reloading = false;

    public WeaponBullet(String weaponName, int maxBullet, int bulletCount) {
        this.weaponName = weaponName;
        this.maxBullet = maxBullet;
        this.bulletCount = bulletCount;
    }

    public WeaponBullet(String weaponName, int bulletCount) {
        this(weaponName, bulletCount, bulletCount);
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        if (this.bulletCount - bulletCount > 0) {
            this.bulletCount = bulletCount;
        } else {
            this.bulletCount = 0;
        }
    }

    public int getMaxBullet() {
        return maxBullet;
    }

    public boolean isReloading() {
        return reloading;
    }

    public void reload() {
        this.reloading = true;
    }

    public void finishedReloading() {
        this.reloading = false;
    }

    public void usedBullet(int amount) {
        setBulletCount(getBulletCount() - amount);
    }
}
