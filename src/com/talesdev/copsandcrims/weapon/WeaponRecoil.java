package com.talesdev.copsandcrims.weapon;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.core.entity.MetaData;
import org.bukkit.entity.Player;

/**
 * Weapon recoil tag
 *
 * @author MoKunz
 */
public class WeaponRecoil {
    private final String KEY;
    private double regen = 1.0D;
    private double maxRecoil = 50.0D;
    private Player player;
    private MetaData metaData;

    public WeaponRecoil(Player player, String weaponName) {
        this(player, weaponName, 30.0, 1.0);
    }

    public WeaponRecoil(Player player, String weaponName, double maxRecoil) {
        this(player, weaponName, maxRecoil, 1.0);
    }

    public WeaponRecoil(Player player, String weaponName, double maxRecoil, double regen) {
        this.KEY = "WeaponRecoil." + weaponName;
        this.player = player;
        this.metaData = new MetaData(player, CopsAndCrims.getPlugin());
        this.maxRecoil = maxRecoil;
        this.regen = regen;
    }

    public double getRegen() {
        return regen;
    }

    public void setRegen(double regen) {
        this.regen = regen;
    }

    private MetaData getMetaData() {
        return metaData;
    }

    public double getRecoil() {
        if (getMetaData().getMetadata(KEY) != null) {
            return (double) getMetaData().getMetadata(KEY);
        }
        return 0.0;
    }

    public void setRecoil(double recoilValue) {
        if (recoilValue > 0) {
            if (getRecoil() <= maxRecoil) {
                getMetaData().setMetadata(KEY, recoilValue);
            } else {
                getMetaData().setMetadata(KEY, maxRecoil);
            }
        } else {
            getMetaData().setMetadata(KEY, 0.0);
        }
    }

    public void addRecoil(double recoilAmount) {
        setRecoil(getRecoil() + recoilAmount);
    }

    public void removeRecoil(double recoilAmount) {
        setRecoil(getRecoil() - recoilAmount);
    }
}
