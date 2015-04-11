package com.talesdev.copsandcrims;

import com.talesdev.core.world.SoundEffectInterface;

/**
 * CVC Sound
 *
 * @author MoKunz
 */
public enum CvCSound implements SoundEffectInterface {
    // Weapons
    MCGO_WEAPONS_AK47SHOT,
    MCGO_WEAPONS_CARBINESHOT,
    MCGO_WEAPONS_KNIFESWING,
    MCGO_WEAPONS_MAGNUMSHOT,
    MCGO_WEAPONS_PISTOLSHOT,
    MCGO_WEAPONS_SHOTGUNSHOT,
    MCGO_WEAPONS_SMGSHOT,
    MCGO_WEAPONS_SNIPERSHOT,

    // Headshot
    MCGO_RANDOM_HEADSHOT_SHOOTER,
    MCGO_RANDOM_HEADSHOT_VICTIM,

    // Reloading
    MCGO_RANDOM_RELOAD__START,
    MCGO_RANDOM_RELOAD__FIRE,
    MCGO_RANDOM_RELOAD__END,;

    @Override
    public String asString() {
        return toString().replaceAll("__", "UNDERSCORE").replaceAll("_", "\\.").replaceAll("UNDERSCORE", "_").toLowerCase();
    }
}
