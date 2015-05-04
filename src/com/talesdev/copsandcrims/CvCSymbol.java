package com.talesdev.copsandcrims;

/**
 * CvC Symbol
 * TODO : Add more symbol
 *
 * @author MoKunz
 */
public enum CvCSymbol {
    WEAPON_PISTOL_DESERT_LEFT(""),
    WEAPON_PISTOL_LEFT(""),
    WEAPON_SMG_LEFT(""),
    WEAPON_SHOTGUN_LEFT(""),
    WEAPON_RIFLE_COP_LEFT(""),
    WEAPON_RIFLE_LEFT(""),
    WEAPON_SNIPER_LEFT(""),
    GRENADE_SMALL(""),;
    private String character;

    CvCSymbol(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}
