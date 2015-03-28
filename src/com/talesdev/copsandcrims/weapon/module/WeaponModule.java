package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.weapon.Weapon;
import org.bukkit.event.Listener;

/**
 * Weapon controller
 *
 * @author MoKunz
 */
public abstract class WeaponModule implements Listener {
    protected Weapon weapon;
    private String moduleName;

    public WeaponModule(String name) {
        this.moduleName = name;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void linkWeapon(Weapon weapon) {
        if (this.weapon != null) return;
        this.weapon = weapon;
    }

    protected Weapon getWeapon() {
        return this.weapon;
    }
}
