package com.talesdev.copsandcrims.weapon;

import com.talesdev.copsandcrims.guns.DesertEagle;

/**
 * Enum represent a weapon type
 *
 * @author MoKunz
 */
public enum WeaponType {
    MELEE("\u928c", ""),
    PISTOL("\u928f", "\u928e"),
    SHOTGUN("\u928a\u928b", ""),
    SUB_MACHINE_GUN("\u928d", ""),
    ASSAULT_RIFLE("\u9286\u9287", "\u9288\u9289"),
    SNIPER_RIFLE("\u9284\u9285", "");
    private String symbol = "";
    private String alternativeSymbol = "";

    WeaponType(String symbol, String alternativeSymbol) {
        this.symbol = symbol;
        this.alternativeSymbol = alternativeSymbol;
    }

    public static String getSymbol(Weapon weapon) {
        if (weapon.getWeaponType().equals(WeaponType.PISTOL)) {
            if (weapon.getClass().getName().equals(DesertEagle.class)) {
                return WeaponType.PISTOL.getAlternativeSymbol();
            } else {
                return WeaponType.PISTOL.getSymbol();
            }
        } else {
            return weapon.getWeaponType().getSymbol();
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAlternativeSymbol() {
        return alternativeSymbol;
    }
}
