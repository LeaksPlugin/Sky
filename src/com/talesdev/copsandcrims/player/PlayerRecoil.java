package com.talesdev.copsandcrims.player;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponRecoil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Player recoil
 *
 * @author MoKunz
 */
public class PlayerRecoil {
    private Player player;
    private Map<String, WeaponRecoil> recoilMap;

    public PlayerRecoil(Player player) {
        this.player = player;
        this.recoilMap = new HashMap<>();
        init();
    }

    private void init() {
        for (String weaponName : CopsAndCrims.getPlugin().getWeaponFactory().getAllWeaponName()) {
            getRecoilMap().put(weaponName, new WeaponRecoil(getPlayer(), weaponName));
        }
    }

    public void clearRecoil() {
        getRecoilMap().clear();
        init();
    }


    public void resetRecoil(String weaponName) {
        if (getWeaponRecoil(weaponName) != null) {
            getWeaponRecoil(weaponName).setRecoil(0.0);
        }
    }

    public void resetRecoil() {
        for (String weapon : getRecoilMap().keySet()) {
            resetRecoil(weapon);
        }
    }


    public void resetRecoil(Weapon weapon) {
        if (getWeaponRecoil(weapon) != null) {
            getWeaponRecoil(weapon).setRecoil(0.0);
        }
    }

    private Map<String, WeaponRecoil> getRecoilMap() {
        return recoilMap;
    }

    public WeaponRecoil getWeaponRecoil(Weapon weapon) {
        return getWeaponRecoil(weapon.getName());
    }

    public WeaponRecoil getWeaponRecoil(String weaponName) {
        if (!getRecoilMap().containsKey(weaponName)) {
            setRecoil(weaponName, 0.0D);
        }
        return getRecoilMap().get(weaponName);
    }

    public double getRecoil(String weaponName) {
        if (getWeaponRecoil(weaponName) != null) {
            return getWeaponRecoil(weaponName).getRecoil();
        }
        return 0.0;
    }

    public double getRecoil(Weapon weapon) {
        return getRecoil(weapon.getName());
    }

    public void setRecoil(String weaponName, double value) {
        if (getWeaponRecoil(weaponName) != null) {
            getWeaponRecoil(weaponName).setRecoil(value);
        }
    }

    public void setRecoil(Weapon weapon, double value) {
        setRecoil(weapon.getName(), value);
    }

    public void addRecoil(String weaponName, double value) {
        if (getWeaponRecoil(weaponName) != null) {
            getWeaponRecoil(weaponName).addRecoil(value);
        }
    }

    public void addRecoil(Weapon weapon, double value) {
        if (getWeaponRecoil(weapon.getName()) != null) {
            getWeaponRecoil(weapon.getName()).addRecoil(value);
        }
    }

    public void removeRecoil(Weapon weapon, double value) {
        if (getWeaponRecoil(weapon.getName()) != null) {
            getWeaponRecoil(weapon.getName()).removeRecoil(value);
        }
    }

    public void removeRecoil(String weaponName, double value) {
        if (getWeaponRecoil(weaponName) != null) {
            getWeaponRecoil(weaponName).removeRecoil(value);
        }
    }

    public Player getPlayer() {
        return player;
    }
}
