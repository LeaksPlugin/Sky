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
    private Player player;
    private MetaData metaData;
    private final String KEY;

    public WeaponRecoil(Player player, String weaponName) {
        this.KEY = "WeaponRecoil." + weaponName;
        this.player = player;
        this.metaData = new MetaData(player, CopsAndCrims.getPlugin());
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
        getMetaData().setMetadata(KEY, recoilValue);
    }

    public void addRecoil(double recoilAmount) {
        setRecoil(getRecoil() + recoilAmount);
    }

    public void removeRecoil(double recoilAmount) {
        setRecoil(getRecoil() - recoilAmount);
    }
}
