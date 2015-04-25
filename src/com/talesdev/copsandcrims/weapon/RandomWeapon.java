package com.talesdev.copsandcrims.weapon;

import java.util.*;

/**
 * Random Weapon
 *
 * @author MoKunz
 */
public class RandomWeapon {
    private WeaponFactory weaponFactory;
    private Set<Weapon> filter;

    public RandomWeapon(WeaponFactory weaponFactory) {
        this.weaponFactory = weaponFactory;
        this.filter = new HashSet<>();
    }

    @SafeVarargs
    public final void filter(Class<? extends Weapon>... weaponClass) {
        for (Class<? extends Weapon> clazz : weaponClass) {
            filter.add(weaponFactory.getWeapon(clazz));
        }
    }

    public void filter(Weapon... weapons) {
        Collections.addAll(filter, weapons);
    }

    public Weapon randomWeapon() {
        List<Weapon> weaponList = new ArrayList<>(weaponFactory.getAllWeapon());
        filter.forEach(weaponList::remove);
        return weaponList.get(new Random().nextInt(weaponList.size()));
    }

    public Class<? extends Weapon> randomWeaponClass() {
        return randomWeapon().getClass();
    }

    public String randomWeaponName() {
        return randomWeapon().getName();
    }
}
