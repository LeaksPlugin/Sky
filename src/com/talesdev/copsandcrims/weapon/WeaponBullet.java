package com.talesdev.copsandcrims.weapon;

import com.talesdev.core.player.data.HashItemData;
import com.talesdev.core.player.data.ItemData;

/**
 * Weapon bullet
 *
 * @author MoKunz
 */
public class WeaponBullet extends HashItemData {
    private String weaponName = "";
    private int bulletCount;
    private int maxBullet;
    private boolean reloading = false;
    private boolean cancel = false;

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
        if (bulletCount > 0) {
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

    public void cancel() {
        this.cancel = true;
    }

    public boolean isCancel() {
        return this.cancel;
    }

    public void respondCancel() {
        this.cancel = false;
    }

    @Override
    public String forItem() {
        return weaponName;
    }

    @Override
    public String dataType() {
        return "WeaponBullet";
    }

    @Override
    public Class<? extends ItemData> getClassType() {
        return getClass();
    }
}
