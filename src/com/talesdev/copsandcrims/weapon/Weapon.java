package com.talesdev.copsandcrims.weapon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represent a weapon
 *
 * @author MoKunz
 */
public abstract class Weapon {
    protected String name;
    protected String displayName;
    protected List<String> lore;

    public Weapon(String name, String displayName, List<String> lore) {
        this.name = name;
        this.displayName = displayName;
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    protected List<String> blankAliases() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Weapon) {
            if (((Weapon) obj).getName().equals(this.getName())) {
                return true;
            }
        }
        return false;
    }
}
