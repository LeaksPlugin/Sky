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
    protected List<String> lores;

    public Weapon(String name, String displayName, List<String> lores) {
        this.name = name;
        this.displayName = displayName;
        this.lores = lores;
    }

    public String getName() {
        return name;
    }

    public List<String> getLores() {
        return lores;
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
        if (!(obj instanceof Weapon)) {
            return false;
        }
        return ((Weapon) obj).getName().equals(this.getName());
    }
}
